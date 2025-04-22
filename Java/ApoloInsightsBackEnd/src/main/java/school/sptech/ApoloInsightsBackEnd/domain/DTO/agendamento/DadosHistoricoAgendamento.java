package school.sptech.ApoloInsightsBackEnd.domain.DTO.agendamento;

import school.sptech.ApoloInsightsBackEnd.domain.Agendamento;
import school.sptech.ApoloInsightsBackEnd.domain.FormaPagamento;
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
