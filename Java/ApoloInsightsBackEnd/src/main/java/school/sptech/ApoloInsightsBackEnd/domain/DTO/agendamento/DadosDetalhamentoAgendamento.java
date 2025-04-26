package school.sptech.ApoloInsightsBackEnd.domain.DTO.agendamento;

import school.sptech.ApoloInsightsBackEnd.domain.Agendamento;
import school.sptech.ApoloInsightsBackEnd.domain.Servico;
import school.sptech.ApoloInsightsBackEnd.domain.Usuario;

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
                agendamento.getFormaPagamento().toString());

    }
}
