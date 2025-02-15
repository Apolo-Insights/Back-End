package school.sptech.ApoloInsightsBackEnd.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import school.sptech.ApoloInsightsBackEnd.DTO.DadosAtualizacaoUsuario;
import school.sptech.ApoloInsightsBackEnd.DTO.DadosCadastroUsuario;
import school.sptech.ApoloInsightsBackEnd.DTO.DadosDetalhamentoUsuario;
import school.sptech.ApoloInsightsBackEnd.domain.Usuario;
import school.sptech.ApoloInsightsBackEnd.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    UsuarioService service;

    @PostMapping("/cadastrar")
    public ResponseEntity cadastrar(@Valid @RequestBody DadosCadastroUsuario dados){
        Usuario usuario = service.cadastrar(new Usuario(dados));
        return ResponseEntity.status(HttpStatus.CREATED).body(new DadosDetalhamentoUsuario(usuario));
    }

    @PutMapping("/atualizar")
    public ResponseEntity atualizar(@Valid @RequestBody DadosAtualizacaoUsuario dados){
        System.out.println(dados);
        Usuario usuario = service.atualizar(dados);
        return ResponseEntity.status(HttpStatus.OK).body(new DadosDetalhamentoUsuario(usuario));
    }
}
