package school.sptech.ApoloInsightsBackEnd.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import school.sptech.ApoloInsightsBackEnd.domain.*;
import school.sptech.ApoloInsightsBackEnd.exception.RequestError;
import school.sptech.ApoloInsightsBackEnd.repository.AgendamentoRepository;
import school.sptech.ApoloInsightsBackEnd.repository.AtendimentoRepository;
import school.sptech.ApoloInsightsBackEnd.util.DataHoraUtil;
import school.sptech.ApoloInsightsBackEnd.util.TokenGenerator;
import java.time.LocalDate;

@Service
public class AtendimentoService {

    @Value("${spring.mail.username}")
    private String emailBerthollo;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private AtendimentoRepository atendimentoRepository;

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    public Atendimento cadastrarAtendimento(Long idAgendamento) {
        Agendamento agendamentoExistente = agendamentoRepository.findById(idAgendamento)
                .orElseThrow(() -> new RequestError(HttpStatus.NOT_FOUND, "Agendamento", "Agendamento não encontrado!"));
        Double valor = agendamentoExistente.getServico().getPreco();
        LocalDate data = agendamentoExistente.getData();
        Categoria categoria = agendamentoExistente.getServico().getCategoria();
        Usuario cliente = agendamentoExistente.getUsuario();

        if (agendamentoExistente.getStatus() != Status.AGENDADO) {
            throw new RequestError(HttpStatus.BAD_REQUEST, "Sem campo", "O atendimento já foi finalizado ou cancelado.");
        }

        if (valor == null || valor <= 0) {
            throw new RequestError(HttpStatus.BAD_REQUEST, "Valor", "O valor do atendimento deve ser maior que zero.");
        }
        agendamentoExistente.setStatus(Status.FINALIZADO);
        Atendimento atendimento = new Atendimento(categoria, valor, data, cliente);
        agendamentoRepository.save(agendamentoExistente);
        return atendimentoRepository.save(atendimento);
    }

    public void cancelarAtendimento(Long idAgendamento) {
        Agendamento agendamentoExistente = agendamentoRepository.findById(idAgendamento)
                .orElseThrow(() -> new RequestError(HttpStatus.NOT_FOUND, "Agendamento", "Agendamento não encontrado!"));

        if (agendamentoExistente.getStatus() != Status.AGENDADO) {
            throw new RequestError(HttpStatus.BAD_REQUEST, "Sem campo", "O atendimento já foi finalizado ou cancelado.");
        }
        agendamentoExistente.setStatus(Status.CANCELADO);

        enviarEmail(agendamentoExistente);
        agendamentoRepository.save(agendamentoExistente);
    }

    public void enviarEmail(Agendamento agendamento) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(agendamento.getUsuario().getEmail());
            helper.setSubject("Cancelamento de Agendamento");
            helper.setText(gerarHTML(agendamento.getUsuario().getNome(), agendamento.getServico().getNome(),
                    agendamento.getData(), "https://apoloinsights.com/reagendar/" + agendamento.getId()), true);
            helper.setFrom(emailBerthollo);

            emailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String gerarHTML(String nomeUsuario, String servicoNome, LocalDate dataAgendamento, String linkReagendar) {
        return String.format("""
        <!DOCTYPE html>
        <html lang="pt">
        <head>
            <meta charset="UTF-8">
            <title>Agendamento Cancelado</title>
        </head>
        <body style="margin:0; padding:0;">
            <table width="100%%" cellpadding="0" cellspacing="0" border="0" background="https://img.freepik.com/vetores-gratis/padrao-de-salao-de-beleza-desenhado-a-mao_23-2150110656.jpg?semt=ais_items_boosted&w=740" style="background-size: cover; background-repeat: no-repeat;">
                <tr>
                    <td align="center" style="padding: 40px 0; background-color: rgba(255,255,255,0.85);">
                        <table width="600" cellpadding="0" cellspacing="0" style="background-color: #ffffff; border: 1px solid #ddd; font-family: Arial, sans-serif;">
                            <tr>
                                <td align="center" bgcolor="#e83e8c" style="color: #ffffff; padding: 20px; font-size: 22px; font-weight: bold;">
                                    Agendamento Cancelado
                                </td>
                            </tr>
                            <tr>
                                <td style="padding: 30px; text-align: center; color: #333333;">
                                    <p style="font-size: 16px; margin-bottom: 20px;">Olá, <strong>%s</strong>,</p>
                                    <p style="font-size: 16px; margin-bottom: 20px;">Seu agendamento para o serviço <strong>%s</strong> no dia <strong>%s</strong> foi <strong>cancelado</strong>.</p>
                                    <p style="font-size: 16px;">Se desejar reagendar, clique abaixo:</p>
                                    <a href="https://red-mud-08f68d01e.6.azurestaticapps.net/" style="display: inline-block; margin-top: 20px; padding: 12px 24px; background-color: #e83e8c; color: white; text-decoration: none; border-radius: 5px; font-weight: bold;">Reagendar</a>
                                </td>
                            </tr>
                            <tr>
                                <td align="center" style="padding: 20px; background-color: #f4f4f4; color: #777777; font-size: 14px;">
                                    <p>Atenciosamente,<br><strong>Equipe de Atendimento Berthollo</strong></p>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </body>
        </html>
        """, nomeUsuario, servicoNome, DataHoraUtil.formatarData(dataAgendamento), linkReagendar);
    }
}
