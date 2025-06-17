package school.sptech.ApoloInsightsBackEnd.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.usuario.DadosLogin;
import school.sptech.ApoloInsightsBackEnd.domain.Usuario;
import school.sptech.ApoloInsightsBackEnd.repository.UsuarioRepository;
import school.sptech.ApoloInsightsBackEnd.security.DadosTokenJWT;
import school.sptech.ApoloInsightsBackEnd.security.SenhaUtil;
import school.sptech.ApoloInsightsBackEnd.security.TokenService;
import java.util.Optional;

@CrossOrigin(origins = "${cors.allowed.origin}")
@RestController
@RequestMapping("/login")
public class   AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository repository;

    private static final Logger logger = LoggerFactory.getLogger(AutenticacaoController.class);

    @PostMapping
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosLogin dados){
        logger.info("Tentativa de login para usuário: {}", dados.email() != null ? dados.email() : dados.cpf());

        String login = (dados.email() != null && !dados.email().isBlank()) ? dados.email() : dados.cpf();

        Optional<Usuario> usuarioOpt = repository.findByEmailOrCpf(dados.email(), dados.cpf());

        if (usuarioOpt.isEmpty()) {
            logger.warn("Usuário não encontrado para login: {}", login);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não encontrado");
        }

        Usuario usuario = usuarioOpt.get();
        logger.info("Usuário encontrado: id={}, email={}", usuario.getId(), usuario.getEmail());

        if (!SenhaUtil.verificarSenha(dados.senha(), usuario.getSenha())) {
            logger.error("Senha inválida para usuário: {}", login);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Senha inválida");
        }
        logger.info("Senha válida para usuário: {}", login);

        var autenticationtoken = new UsernamePasswordAuthenticationToken(login, dados.senha());
        var autentication = manager.authenticate(autenticationtoken);

        var tokenJWT = tokenService.gerarToken((Usuario) autentication.getPrincipal());
        logger.info("Token JWT gerado com sucesso para usuário: {}", login);
        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }
}
