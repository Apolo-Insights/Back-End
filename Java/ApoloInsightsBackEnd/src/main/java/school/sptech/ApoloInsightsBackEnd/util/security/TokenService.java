package school.sptech.ApoloInsightsBackEnd.util.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import school.sptech.ApoloInsightsBackEnd.domain.Usuario;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarToken(Usuario usuario){
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("API Apolo Insights")
                    .withSubject(String.valueOf(usuario.getId())) // ID como subject
                    .withClaim("id", usuario.getId()) // ID também como claim
                    .withClaim("email", usuario.getEmail())
                    .withClaim("nome", usuario.getNome())
                    .withClaim("cpf", usuario.getCpf())
                    .withClaim("telefone", usuario.getTelefone())
                    .withExpiresAt(dataExpiracao())
                    .sign(algoritmo);
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

    public String getNome(String tokenJWT) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            DecodedJWT decodedJWT = JWT.require(algoritmo)
                    .withIssuer("API Apolo Insights")
                    .build()
                    .verify(tokenJWT);

            return decodedJWT.getClaim("nome").asString();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token JWT inválido ou expirado!", exception);
        }
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}

