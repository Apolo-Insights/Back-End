package apolov2.insightsApolo.v2.core.port.out;

import apolov2.insightsApolo.v2.core.domain.entity.Usuario;

public interface EmailGateway {
    void enviarTokenRecuperacaoSenha(Usuario usuario, String token);
}
