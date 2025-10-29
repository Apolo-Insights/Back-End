package apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.horariodisponivel;

import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public record DadosCadastroHorario(
        @NotNull(message = "O campo idCategoria não pode ser nulo")
        Long idCategoria,
        
        @NotNull(message = "O campo domingo não pode ser nulo")
        Boolean domingo,
        
        @NotNull(message = "O campo segunda não pode ser nulo")
        Boolean segunda,
        
        @NotNull(message = "O campo terca não pode ser nulo")
        Boolean terca,
        
        @NotNull(message = "O campo quarta não pode ser nulo")
        Boolean quarta,
        
        @NotNull(message = "O campo quinta não pode ser nulo")
        Boolean quinta,
        
        @NotNull(message = "O campo sexta não pode ser nulo")
        Boolean sexta,
        
        @NotNull(message = "O campo sabado não pode ser nulo")
        Boolean sabado,
        
        @NotNull(message = "O campo horaInicio não pode ser nulo")
        LocalTime horaInicio,
        
        @NotNull(message = "O campo horaFim não pode ser nulo")
        LocalTime horaFim
) {
}
