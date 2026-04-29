package apolov2.insightsApolo.v2.infrastructure.adapter.in.web.controller;

import apolov2.insightsApolo.v2.core.application.command.AlterarSenhaCommand;
import apolov2.insightsApolo.v2.core.application.command.AtualizarUsuarioCommand;
import apolov2.insightsApolo.v2.core.application.command.CadastrarFuncionarioCommand;
import apolov2.insightsApolo.v2.core.application.command.CadastrarUsuarioCommand;
import apolov2.insightsApolo.v2.core.application.usecase.UsuarioUseCase;
import apolov2.insightsApolo.v2.core.domain.entity.Usuario;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.DadosAtualizacaoUsuario;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.DadosAtualizarSenha;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.DadosCadastroFuncionario;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.DadosCadastroUsuario;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.DadosDetalhamentoUsuario;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.DadosListagemUsuario;
import apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.mapper.UsuarioMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Usuários", description = "Gerenciamento de usuários")
@CrossOrigin(origins = "${cors.allowed.origin}")
@RestController
@RequestMapping("/v2/usuarios")
public class UsuarioController {
    private final UsuarioUseCase useCase;
    private final UsuarioMapper mapper;

    public UsuarioController(UsuarioUseCase useCase, UsuarioMapper mapper) {
        this.useCase = useCase;
        this.mapper = mapper;
    }

    @PostMapping
    @Operation(summary = "Cadastrar usuário", description = "Cadastra um novo usuário no sistema")
    public ResponseEntity<DadosDetalhamentoUsuario> cadastrar(@Valid @RequestBody DadosCadastroUsuario dados) {
        CadastrarUsuarioCommand command = mapper.cadastroToCommand(dados);
        Usuario usuario = useCase.cadastrarUsuario(command);
        return ResponseEntity.status(201).body(mapper.usuarioToDetalhamento(usuario));
    }

    @PostMapping("/admin")
    @Operation(summary = "Cadastrar funcionário", description = "Cadastra um novo funcionário no sistema")
    public ResponseEntity<DadosDetalhamentoUsuario> cadastrarFuncionario(@Valid @RequestBody DadosCadastroFuncionario dados) {
        CadastrarFuncionarioCommand command = mapper.cadastroFuncionarioToCommand(dados);
        Usuario usuario = useCase.cadastrarFuncionario(command);
        return ResponseEntity.status(201).body(mapper.usuarioToDetalhamento(usuario));
    }

    @PostMapping("/gerar-token")
    @Operation(summary = "Gerar token de recuperação", description = "Gera e envia um token para recuperação de senha por email")
    public ResponseEntity<String> gerarToken(@RequestBody String email) {
        String token = useCase.gerarToken(email);
        return ResponseEntity.ok(token);
    }

    @PutMapping("/alterar-senha")
    @Operation(summary = "Alterar senha", description = "Altera a senha de um usuário existente")
    public ResponseEntity<Void> alterarSenha(@Valid @RequestBody DadosAtualizarSenha dados) {
        AlterarSenhaCommand command = mapper.atualizarSenhaToCommand(dados);
        useCase.alterarSenha(command);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/admin")
    @Operation(summary = "Listar usuários", description = "Lista todos os usuários cadastrados no sistema")
    public ResponseEntity<List<DadosListagemUsuario>> listarUsuarios() {
        List<Usuario> usuarios = useCase.listarUsuarios();
        List<DadosListagemUsuario> listagemUsuarios = usuarios.stream()
                .map(mapper::usuarioToListagem)
                .toList();
        return ResponseEntity.ok(listagemUsuarios);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar usuário", description = "Atualiza os dados de um usuário existente")
    public ResponseEntity<DadosDetalhamentoUsuario> atualizar(
            @Valid @RequestBody DadosAtualizacaoUsuario dados,
            @PathVariable Long id) {
        AtualizarUsuarioCommand command = mapper.atualizacaoToCommand(dados);
        command.setId(id);
        Usuario usuario = useCase.atualizarUsuario(command);
        return ResponseEntity.ok(mapper.usuarioToDetalhamento(usuario));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar usuário", description = "Remove um usuário do sistema pelo ID")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        useCase.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
