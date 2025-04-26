package school.sptech.ApoloInsightsBackEnd.util.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import school.sptech.ApoloInsightsBackEnd.domain.Usuario;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);
    private static final String ISSUER = "API Apolo Insights";
    private static final String TOKEN_ERROR = "Token JWT inválido ou expirado!";
    private static final String GENERATION_ERROR = "ERRO AO GERAR O TOKEN JWT";

    @Value("${api.security.token.secret}")
    private String secret;

    @Value("${api.security.token.expiration-time}")
    private Integer expirationTime;

    public String gerarToken(Usuario usuario){
        try {
            var algoritimo = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("API Apolo Insights")
                    .withSubject(usuario.getEmail())
                    .withExpiresAt(dataExpiracao())
                    .sign(algoritimo);
        } catch (JWTCreationException exception){
            throw new RuntimeException("ERRO AO GERAR O TOKEN JWT", exception);
        }
    }

    public String getSubject(String tokenJWT){
        try {
            var algoritmo =  Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    .withIssuer("API Apolo Insights")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception){
            throw new RuntimeException("Token JWT inválido ou expirado!", exception);
        }
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(expirationTime).toInstant(ZoneOffset.of("-03:00"));
    }

    private void validarSecret() {
        if (secret == null || secret.isBlank()) {
            logger.error("O segredo do token JWT não foi configurado!");
            throw new IllegalStateException("O segredo do token JWT não foi configurado!");
        }
    }
}