package school.sptech.ApoloInsightsBackEnd.old.domain.DTO.agendamento;

import school.sptech.ApoloInsightsBackEnd.old.domain.Agendamento;
import school.sptech.ApoloInsightsBackEnd.old.domain.Status;

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
