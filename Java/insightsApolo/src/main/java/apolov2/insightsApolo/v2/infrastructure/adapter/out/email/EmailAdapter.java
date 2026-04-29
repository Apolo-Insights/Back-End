package apolov2.insightsApolo.v2.infrastructure.adapter.out.email;

import apolov2.insightsApolo.v2.core.domain.entity.Usuario;
import apolov2.insightsApolo.v2.core.port.out.EmailGateway;
import apolov2.insightsApolo.v2.infrastructure.service.EmailService;
import org.springframework.stereotype.Component;

@Component
public class EmailAdapter implements EmailGateway {
    
    private final EmailService emailService;

    public EmailAdapter(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void enviarTokenRecuperacaoSenha(Usuario usuario, String token) {
        emailService.enviarTokenRecuperacaoSenha(usuario, token);
    }
}
