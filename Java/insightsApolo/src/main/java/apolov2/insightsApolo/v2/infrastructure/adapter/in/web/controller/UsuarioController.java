package apolov2.insightsApolo.v2.infrastructure.adapter.in.web.controller;

import apolov2.insightsApolo.v2.core.application.command.AlterarSenhaCommand;
import apolov2.insightsApolo.v2.core.application.command.CadastrarFuncionarioCommand;
import apolov2.insightsApolo.v2.core.application.command.CadastrarUsuarioCommand;
import apolov2.insightsApolo.v2.core.application.usecase.UsuarioUseCase;
import apolov2.insightsApolo.v2.core.domain.entity.Usuario;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.DadosAtualizarSenha;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.DadosCadastroFuncionario;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.DadosCadastroUsuario;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.DadosDetalhamentoUsuario;
import apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.mapper.UsuarioMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
