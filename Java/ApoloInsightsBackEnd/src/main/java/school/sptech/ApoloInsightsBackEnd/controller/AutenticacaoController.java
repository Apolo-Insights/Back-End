package school.sptech.ApoloInsightsBackEnd.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import school.sptech.ApoloInsightsBackEnd.DTO.DadosLogin;
import school.sptech.ApoloInsightsBackEnd.domain.Usuario;
import school.sptech.ApoloInsightsBackEnd.repository.UsuarioRepository;
import school.sptech.ApoloInsightsBackEnd.util.security.DadosTokenJWT;
import school.sptech.ApoloInsightsBackEnd.util.security.SenhaUtil;
import school.sptech.ApoloInsightsBackEnd.util.security.TokenService;

import java.util.Optional;

@CrossOrigin(origins = "${cors.allowed.origin}")
@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository repository;

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosLogin dados){
        String login = (dados.email() != null && !dados.email().isBlank()) ? dados.email() : dados.cpf();

        Optional<Usuario> usuarioOpt = repository.findByEmailOrCpf(dados.email(), dados.cpf());

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não encontrado");
        }

        Usuario usuario = usuarioOpt.get();

        if (!SenhaUtil.verificarSenha(dados.senha(), usuario.getSenha())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Senha inválida");
        }

        var autenticationtoken = new UsernamePasswordAuthenticationToken(login, dados.senha());
        var autentication = manager.authenticate(autenticationtoken);

        var tokenJWT = tokenService.gerarToken((Usuario) autentication.getPrincipal());
        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }
}
