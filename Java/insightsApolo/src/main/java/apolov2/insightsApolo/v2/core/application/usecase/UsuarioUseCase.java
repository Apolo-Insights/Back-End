package apolov2.insightsApolo.v2.core.application.usecase;

import apolov2.insightsApolo.v2.core.application.command.AlterarSenhaCommand;
import apolov2.insightsApolo.v2.core.application.command.AtualizarUsuarioCommand;
import apolov2.insightsApolo.v2.core.application.command.CadastrarFuncionarioCommand;
import apolov2.insightsApolo.v2.core.application.command.CadastrarUsuarioCommand;
import apolov2.insightsApolo.v2.core.application.exception.RegistroExistenteException;
import apolov2.insightsApolo.v2.core.domain.entity.Usuario;
import apolov2.insightsApolo.v2.core.domain.util.Role;
import apolov2.insightsApolo.v2.core.port.in.UsuarioGateway;
import apolov2.insightsApolo.v2.core.port.out.EmailGateway;
import apolov2.insightsApolo.v2.infrastructure.util.TokenGenerator;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioUseCase {
    private final UsuarioGateway gateway;
    private final EmailGateway emailGateway;

    public UsuarioUseCase(UsuarioGateway gateway, EmailGateway emailGateway) {
        this.gateway = gateway;
        this.emailGateway = emailGateway;
    }

    public Usuario cadastrarUsuario(CadastrarUsuarioCommand command) {
        // Validar se email já existe
        if (gateway.existsByEmail(command.email())) {
            throw new RegistroExistenteException("Esse E-mail já está cadastrado");
        }

        // Validar se CPF já existe
        if (gateway.existsByCpf(command.cpf())) {
            throw new RegistroExistenteException("Esse CPF já está cadastrado");
        }

        // Validar senha
        validarSenha(command.senha());

        // Criar novo usuário
        Usuario novoUsuario = new Usuario(
                command.nome(),
                command.cpf(),
                command.dataNascimento(),
                command.telefone(),
                command.genero(),
                command.email(),
                hashSenha(command.senha()),
                Role.CLIENTE
        );

        return gateway.cadastrar(novoUsuario);
    }

    public Usuario cadastrarFuncionario(CadastrarFuncionarioCommand command) {
        // Validar se email já existe
        if (gateway.existsByEmail(command.email())) {
            throw new RegistroExistenteException("Esse E-mail já está cadastrado");
        }

        // Validar se CPF já existe
        if (gateway.existsByCpf(command.cpf())) {
            throw new RegistroExistenteException("Esse CPF já está cadastrado");
        }

        // Criar novo funcionário com senha padrão (CPF)
        Usuario novoFuncionario = new Usuario(
                command.nome(),
                command.cpf(),
                null, // dataNascimento não é obrigatória para funcionários
                command.telefone(),
                null, // genero não é obrigatório para funcionários
                command.email(),
                hashSenha(command.cpf()), // senha padrão é o CPF
                command.funcao()
        );

        return gateway.cadastrar(novoFuncionario);
    }

    public String gerarToken(String email) {
        Usuario usuario = gateway.buscarPorEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Esse e-mail não pertence a nenhuma conta."));
        
        String token = TokenGenerator.gerarTokenAlfanumerico();
        emailGateway.enviarTokenRecuperacaoSenha(usuario, token);
        
        return token;
    }

    public void alterarSenha(AlterarSenhaCommand command) {
        Usuario usuario = gateway.buscarPorEmail(command.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Email não encontrado"));
        
        // Validar a nova senha
        validarSenha(command.getNovaSenha());
        
        // Atualizar senha
        usuario.setSenha(hashSenha(command.getNovaSenha()));
        
        // Salvar usuário
        gateway.atualizar(usuario);
    }

    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = gateway.listarTodos();
        
        if (usuarios.isEmpty()) {
            throw new IllegalArgumentException("Nenhum usuário encontrado");
        }
        
        return usuarios;
    }

    public void deletarUsuario(Long id) {
        Usuario usuario = gateway.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        
        gateway.deletar(id);
    }

    public Usuario atualizarUsuario(AtualizarUsuarioCommand command) {
        Usuario usuario = gateway.buscarPorId(command.getId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        
        // Atualizar apenas os campos não nulos
        if (command.getNome() != null) {
            usuario.setNome(command.getNome());
        }
        if (command.getTelefone() != null) {
            usuario.setTelefone(command.getTelefone());
        }
        if (command.getEmail() != null) {
            usuario.setEmail(command.getEmail());
        }
        if (command.getGenero() != null) {
            usuario.setGenero(command.getGenero());
        }
        if (command.getCpf() != null) {
            usuario.setCpf(command.getCpf());
        }
        if (command.getFuncao() != null) {
            usuario.setRole(command.getFuncao());
        }
        
        return gateway.atualizar(usuario);
    }

    private void validarSenha(String senha) {
        if (senha.length() < 8) {
            throw new IllegalArgumentException("A senha deve ter pelo menos 8 caracteres");
        }
        if (!senha.matches(".*[A-Z].*")) {
            throw new IllegalArgumentException("A senha deve conter pelo menos uma letra maiúscula");
        }
        if (!senha.matches(".*[a-z].*")) {
            throw new IllegalArgumentException("A senha deve conter pelo menos uma letra minúscula");
        }
        if (!senha.matches(".*\\d.*")) {
            throw new IllegalArgumentException("A senha deve conter pelo menos um número");
        }
    }

    private String hashSenha(String senha) {
        return BCrypt.hashpw(senha, BCrypt.gensalt(12));
    }
}
