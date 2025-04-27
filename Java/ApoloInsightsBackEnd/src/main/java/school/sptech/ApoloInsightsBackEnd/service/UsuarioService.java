package school.sptech.ApoloInsightsBackEnd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.usuario.DadosAtualizacaoUsuario;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.usuario.DadosCadastroUsuario;
import school.sptech.ApoloInsightsBackEnd.domain.Usuario;
import school.sptech.ApoloInsightsBackEnd.repository.UsuarioRepository;
import school.sptech.ApoloInsightsBackEnd.util.exception.RequestError;
import school.sptech.ApoloInsightsBackEnd.util.security.SenhaUtil;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository repository;

    @Transactional
    public Usuario cadastrar(DadosCadastroUsuario dados){
        if (repository.existsByEmail(dados.email())) {
            throw new RequestError(
                    HttpStatus.CONFLICT, "email", "Esse E-mail já está cadastrado");
        }
        if (repository.existsByCpf(dados.cpf())) {
            throw new RequestError(
                    HttpStatus.CONFLICT, "cpf", "Esse CPF já está cadastrado");
        }

        Usuario usuario = new Usuario(dados);
        usuario.setSenha(SenhaUtil.hashSenha(usuario.getSenha()));
        return repository.save(usuario);
    }

    @Transactional
    public Usuario atualizar(DadosAtualizacaoUsuario dados){
        Usuario usuario = repository.findById(dados.id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        usuario.atualizarInformacoes(dados);
        return repository.save(usuario);
    }

    public void deletar(Long id) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        repository.delete(usuario);
    }
}
