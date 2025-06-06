package school.sptech.ApoloInsightsBackEnd.domain.DTO.horariodisponivel;

import school.sptech.ApoloInsightsBackEnd.domain.HorarioDisponivel;

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
