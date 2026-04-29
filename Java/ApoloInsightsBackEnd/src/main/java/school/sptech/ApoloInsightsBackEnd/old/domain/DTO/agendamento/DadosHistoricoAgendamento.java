package school.sptech.ApoloInsightsBackEnd.old.domain.DTO.agendamento;

import school.sptech.ApoloInsightsBackEnd.old.domain.Agendamento;
import school.sptech.ApoloInsightsBackEnd.old.domain.FormaPagamento;
import java.time.LocalDate;
import java.time.LocalTime;

public record DadosHistoricoAgendamento(
        String servico,
        LocalDate data,
        LocalTime hora,
        FormaPagamento formaPagamento
) {
    public DadosHistoricoAgendamento(Agendamento agendamento) {
        this(
                agendamento.getServico().getNome(),
                agendamento.getData(),
                agendamento.getHora(),
                agendamento.getFormaPagamento()
        );
    }
}
