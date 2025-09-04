package school.sptech.ApoloInsightsBackEnd.old.domain.DTO.horariodisponivel;

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
