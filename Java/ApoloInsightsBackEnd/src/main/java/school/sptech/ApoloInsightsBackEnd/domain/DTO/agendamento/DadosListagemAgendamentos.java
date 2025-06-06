package school.sptech.ApoloInsightsBackEnd.domain.DTO.agendamento;

import school.sptech.ApoloInsightsBackEnd.domain.Agendamento;
import school.sptech.ApoloInsightsBackEnd.domain.Status;

import java.time.LocalDate;
import java.time.LocalTime;

public record DadosListagemAgendamentos(
        String servico,
        LocalDate data,
        LocalTime hora,
        Status status
) {
    public DadosListagemAgendamentos(Agendamento agendamento) {
        this(
                agendamento.getServico().getNome(),
                agendamento.getData(),
                agendamento.getHora(),
                agendamento.getStatus()
        );
    }
}
