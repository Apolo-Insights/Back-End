package school.sptech.ApoloInsightsBackEnd.domain.DTO.agendamento;

import java.time.LocalDate;
import java.time.LocalTime;

public record DadosCadastroAgendamento(
        Long usuarioId,
        Long servicoId,
        LocalDate data,
        LocalTime hora
) {

}
