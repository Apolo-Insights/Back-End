package school.sptech.ApoloInsightsBackEnd.old.security;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class SenhaUtil {
    public static String hashSenha(String senha) {
        return BCrypt.hashpw(senha, BCrypt.gensalt(12));
    }

    public static boolean verificarSenha(String senha, String hash) {
        return BCrypt.checkpw(senha, hash);
    }

}
