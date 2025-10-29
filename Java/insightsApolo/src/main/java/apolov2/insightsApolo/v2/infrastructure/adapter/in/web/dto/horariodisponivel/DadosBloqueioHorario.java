package apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.horariodisponivel;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record DadosBloqueioHorario(
        @NotNull(message = "O campo idCategoria não pode ser nulo")
        Long idCategoria,
        
        @NotNull(message = "O campo data não pode ser nulo")
        LocalDate data,
        
        LocalTime horaInicio,
        
        LocalTime horaFim,
        
        @NotNull(message = "O campo repetirSemanalmente não pode ser nulo")
        Boolean repetirSemanalmente,
        
        @NotNull(message = "O campo diaInteiro não pode ser nulo")
        Boolean diaInteiro
) {
}
