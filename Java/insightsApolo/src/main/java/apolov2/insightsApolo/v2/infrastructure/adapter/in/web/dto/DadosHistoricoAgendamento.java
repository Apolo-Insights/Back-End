package apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto;

import apolov2.insightsApolo.v2.core.domain.entity.Agendamento;
import apolov2.insightsApolo.v2.core.domain.util.FormaPagamento;

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
