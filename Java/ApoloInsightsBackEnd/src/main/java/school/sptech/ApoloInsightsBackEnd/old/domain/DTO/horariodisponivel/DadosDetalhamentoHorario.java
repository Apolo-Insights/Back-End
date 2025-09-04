package school.sptech.ApoloInsightsBackEnd.old.domain.DTO.horariodisponivel;

import school.sptech.ApoloInsightsBackEnd.old.domain.HorarioDisponivel;

import java.time.LocalTime;

public record DadosDetalhamentoHorario(
        LocalTime horaInicio,
        LocalTime horaFim,
        Long idCategoria
) {

    public DadosDetalhamentoHorario(HorarioDisponivel novoHorario) {
        this(
                novoHorario.getHoraInicio(),
                novoHorario.getHoraFim(),
                novoHorario.getCategoria().getId()
        );
    }
}
