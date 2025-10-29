package apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto;

import apolov2.insightsApolo.v2.core.domain.entity.Agendamento;
import apolov2.insightsApolo.v2.core.domain.entity.Servico;
import apolov2.insightsApolo.v2.core.domain.entity.Usuario;

import java.time.LocalDate;
import java.time.LocalTime;

public record DadosDetalhamentoAgendamento(
        Usuario usuario,
        Servico servico,
        LocalDate data,
        LocalTime hora,
        String formaPagamento
) {
    public DadosDetalhamentoAgendamento(Agendamento agendamento) {
        this(
                agendamento.getUsuario(),
                agendamento.getServico(),
                agendamento.getData(),
                agendamento.getHora(),
                agendamento.getFormaPagamento() != null ? agendamento.getFormaPagamento().toString() : null
        );
    }
}
