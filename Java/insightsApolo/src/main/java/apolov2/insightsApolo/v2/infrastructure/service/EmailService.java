package apolov2.insightsApolo.v2.infrastructure.service;

import apolov2.insightsApolo.v2.core.domain.entity.Usuario;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String emailBerthollo;

    public void enviarTokenRecuperacaoSenha(Usuario usuario, String token) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(usuario.getEmail());
            helper.setSubject("Token de Redefinição de Senha");
            helper.setText(gerarHTML(usuario.getNome(), usuario.getEmail(), token), true);
            helper.setFrom(emailBerthollo);

            emailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar o e-mail de redefinição de senha, verifique se o seu email está correto ou crie uma nova conta.", e);
        }
    }

    private String gerarHTML(String nomeUsuario, String email, String token) {
        return String.format("""
        <!DOCTYPE html>
        <html lang="pt">
        <head>
            <meta charset="UTF-8">
            <title>Redefinição de Senha</title>
        </head>
        <body style="margin:0; padding:0;">
            <table width="100%%" cellpadding="0" cellspacing="0" border="0" background="https://img.freepik.com/vetores-gratis/padrao-de-salao-de-beleza-desenhado-a-mao_23-2150110656.jpg?semt=ais_items_boosted&w=740" style="background-size: cover; background-repeat: no-repeat;">
                <tr>
                    <td align="center" style="padding: 40px 0; background-color: rgba(255,255,255,0.85);">
                        <table width="600" cellpadding="0" cellspacing="0" style="background-color: #ffffff; border: 1px solid #ddd; font-family: Arial, sans-serif;">
                            <tr>
                                <td align="center" bgcolor="#e83e8c" style="color: #ffffff; padding: 20px; font-size: 22px; font-weight: bold;">
                                    Redefinição de Senha
                                </td>
                            </tr>
                            <tr>
                                <td style="padding: 30px; text-align: center; color: #333333;">
                                    <p style="font-size: 16px; margin-bottom: 20px;">Olá, <strong>%s</strong>,</p>
                                    <p style="font-size: 16px; margin-bottom: 20px;">Recebemos uma solicitação para redefinir a senha associada ao e-mail <strong>%s</strong>.</p>
                                    <p style="font-size: 16px; margin-bottom: 20px;">Use o token abaixo para redefinir sua senha:</p>
                                    <div style="font-size: 24px; font-weight: bold; margin: 20px 0; color: #e83e8c;">%s</div>
                                    <p style="font-size: 14px; color: #777;">Se você não solicitou essa alteração, por favor ignore este e-mail.</p>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" style="padding: 20px; background-color: #f4f4f4; color: #777777; font-size: 14px;">
                                    <p style="margin: 0;">&copy; 2025 Salão Apolo. Todos os direitos reservados.</p>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </body>
        </html>
        """, nomeUsuario, email, token);
    }
}
