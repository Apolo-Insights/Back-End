package apolov2.insightsApolo.v2.infrastructure.adapter.in.web.controller;

import apolov2.insightsApolo.v2.core.domain.entity.Usuario;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.DadosLogin;
import apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.entity.UsuarioEntity;
import apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.repository.UsuarioRepository;
import apolov2.insightsApolo.v2.infrastructure.security.DadosTokenJWT;
import apolov2.insightsApolo.v2.infrastructure.security.TokenService;
import apolov2.insightsApolo.v2.infrastructure.util.SenhaUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = "Autenticação", description = "Endpoints de autenticação")
@CrossOrigin(origins = "${cors.allowed.origin}")
@RestController
@RequestMapping("/v2/login")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository repository;

    private static final Logger logger = LoggerFactory.getLogger(AutenticacaoController.class);

    @PostMapping
    @Operation(summary = "Efetuar login", description = "Autentica um usuário e retorna um token JWT")
    public ResponseEntity efetuarLogin(@RequestBody @Valid DadosLogin dados) {
        logger.info("Tentativa de login para usuário: {}", dados.email() != null ? dados.email() : dados.cpf());

        String login = (dados.email() != null && !dados.email().isBlank()) ? dados.email() : dados.cpf();

        Optional<UsuarioEntity> usuarioOpt = repository.findByEmailOrCpf(dados.email(), dados.cpf());

        if (usuarioOpt.isEmpty()) {
            logger.warn("Usuário não encontrado para login: {}", login);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não encontrado");
        }

        UsuarioEntity usuarioEntity = usuarioOpt.get();
        logger.info("Usuário encontrado: id={}, email={}", usuarioEntity.getId(), usuarioEntity.getEmail());

        if (!SenhaUtil.verificarSenha(dados.senha(), usuarioEntity.getSenha())) {
            logger.error("Senha inválida para usuário: {}", login);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Senha inválida");
        }
        logger.info("Senha válida para usuário: {}", login);

        var autenticationtoken = new UsernamePasswordAuthenticationToken(login, dados.senha());
        var autentication = manager.authenticate(autenticationtoken);

        // Converter UsuarioEntity para Usuario do domínio
        Usuario usuario = converterParaDominio(usuarioEntity);
        var tokenJWT = tokenService.gerarToken(usuario);
        
        logger.info("Token JWT gerado com sucesso para usuário: {}", login);
        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }

    private Usuario converterParaDominio(UsuarioEntity entity) {
        Usuario usuario = new Usuario();
        usuario.setId(entity.getId());
        usuario.setNome(entity.getNome());
        usuario.setCpf(entity.getCpf());
        usuario.setDataNascimento(entity.getDataNascimento());
        usuario.setTelefone(entity.getTelefone());
        usuario.setGenero(entity.getGenero());
        usuario.setEmail(entity.getEmail());
        usuario.setSenha(entity.getSenha());
        usuario.setRole(entity.getRole());
        return usuario;
    }
}
