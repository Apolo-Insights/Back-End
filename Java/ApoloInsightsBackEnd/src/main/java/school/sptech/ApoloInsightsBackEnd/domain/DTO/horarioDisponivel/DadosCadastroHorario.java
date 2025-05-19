package school.sptech.ApoloInsightsBackEnd.domain.DTO.horarioDisponivel;

import java.time.LocalTime;

public record DadosCadastroHorario(
        Long idCategoria,
        boolean domingo,
        boolean segunda,
        boolean terca,
        boolean quarta,
        boolean quinta,
        boolean sexta,
        boolean sabado,
        LocalTime horaInicio,
        LocalTime horaFim
) {
}
