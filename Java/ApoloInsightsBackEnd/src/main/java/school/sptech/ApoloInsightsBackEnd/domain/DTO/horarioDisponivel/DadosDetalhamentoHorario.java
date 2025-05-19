package school.sptech.ApoloInsightsBackEnd.domain.DTO.horarioDisponivel;

import school.sptech.ApoloInsightsBackEnd.domain.HorarioDisponivel;

import java.time.LocalTime;

public record DadosDetalhamentoHorario(
        LocalTime horaInicio,
        LocalTime horaFim,
        boolean bloqueado,
        Long idCategoria
) {

    public DadosDetalhamentoHorario(HorarioDisponivel novoHorario) {
        this(
                novoHorario.getHoraInicio(),
                novoHorario.getHoraFim(),
                novoHorario.isBloqueado(),
                novoHorario.getCategoria().getId()
        );
    }
}
