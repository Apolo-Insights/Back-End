package school.sptech.ApoloInsightsBackEnd.old.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.ApoloInsightsBackEnd.old.domain.DTO.usuario.*;
import school.sptech.ApoloInsightsBackEnd.old.domain.Usuario;
import school.sptech.ApoloInsightsBackEnd.old.service.UsuarioService;
import java.util.List;



@Tag(name = "Usuários", description = "Gerenciamento de usuários")
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioService service;

    @PostMapping
    @io.swagger.v3.oas.annotations.Operation(summary = "Cadastrar usuário", description = "Cadastra um novo usuário no sistema")
    public ResponseEntity<DadosDetalhamentoUsuario> cadastrar(@Valid @RequestBody DadosCadastroUsuario dados){
        Usuario usuario = service.cadastrar(dados);
        return ResponseEntity.status(HttpStatus.CREATED).body(new DadosDetalhamentoUsuario(usuario));
    }

    @PostMapping("/admin")
    public ResponseEntity<DadosDetalhamentoUsuario> cadastrarFuncionario(@Valid @RequestBody DadosCadastroFuncionario dados) {
        Usuario usuario = service.cadastrarFuncionario(dados);
        return ResponseEntity.status(HttpStatus.CREATED).body(new DadosDetalhamentoUsuario(usuario));
    }

    @PostMapping("/gerar-token")
    public ResponseEntity<String> gerarToken(@RequestBody String email) {
        String token = service.gerarToken(email);
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

    @PutMapping("/alterar-senha")
    public ResponseEntity<Void> alterarSenha(@RequestBody DadosAtualizarSenha dados){
        service.alterarSenha(dados);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    @io.swagger.v3.oas.annotations.Operation(summary = "Atualizar usuário", description = "Atualiza os dados de um usuário existente")
    public ResponseEntity<DadosDetalhamentoUsuario> atualizar(
            @Valid @RequestBody DadosAtualizacaoUsuario dados,
            @PathVariable Long id){
        Usuario usuario = service.atualizar(id, dados);
        return ResponseEntity.status(HttpStatus.OK).body(new DadosDetalhamentoUsuario(usuario));
    }

    @DeleteMapping("/{id}")
    @io.swagger.v3.oas.annotations.Operation(summary = "Deletar usuário", description = "Remove um usuário do sistema pelo ID")
    public ResponseEntity<String> deletar(@PathVariable Long id){
        service.deletar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Usuário deletado com sucesso!");
    }

    @GetMapping("/admin")
    public ResponseEntity<List<DadosListagemUsuario>> listarUsuarios() {
        List<DadosListagemUsuario> usuarios = service.listarUsuarios();
        return ResponseEntity.status(HttpStatus.OK).body(usuarios);
    }
}


