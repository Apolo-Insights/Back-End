package apolov2.insightsApolo.v2.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import apolov2.insightsApolo.v2.core.domain.entity.Usuario;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);
    private static final String ISSUER = "API Apolo Insights";
    private static final String TOKEN_ERROR = "Token JWT inválido ou expirado!";
    private static final String GENERATION_ERROR = "Erro ao gerar o token JWT";

    @Value("${api.security.token.secret}")
    private String secret;

    @Value("${api.security.token.expiration-time}")
    private Integer expirationTime;

    public String gerarToken(Usuario usuario) {
        try {
            Algorithm algoritmo = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(usuario.getEmail())
                    .withClaim("nome", usuario.getNome())
                    .withClaim("id", usuario.getId().toString())
                    .withClaim("email", usuario.getEmail())
                    .withClaim("telefone", usuario.getTelefone())
                    .withClaim("genero", usuario.getGenero() != null ? usuario.getGenero().toString() : null)
                    .withClaim("role", usuario.getRole().name())
                    .withExpiresAt(dataExpiracao())
                    .sign(algoritmo);

        } catch (JWTCreationException exception) {
            logger.error(GENERATION_ERROR, exception);
            throw new RuntimeException(GENERATION_ERROR, exception);
        }
    }

    public String getSubject(String tokenJWT) {
        try {
            Algorithm algoritmo = Algorithm.HMAC256(secret);

            return JWT.require(algoritmo)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            logger.error(TOKEN_ERROR, exception);
            throw new RuntimeException(TOKEN_ERROR, exception);
        }
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(expirationTime).toInstant(ZoneOffset.of("-03:00"));
    }
}
