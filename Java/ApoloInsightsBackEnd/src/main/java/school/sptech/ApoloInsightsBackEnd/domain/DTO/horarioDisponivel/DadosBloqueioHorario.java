package school.sptech.ApoloInsightsBackEnd.domain.DTO.horarioDisponivel;

import java.time.LocalDate;
import java.time.LocalTime;

public record DadosBloqueioHorario(
        Long idCategoria,
        LocalDate data,
        LocalTime horaInicio,
        LocalTime horaFim,
        Boolean repetirSemanalmente
) {
}
