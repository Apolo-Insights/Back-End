package school.sptech.ApoloInsightsBackEnd.old.domain.DTO.agendamento;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import school.sptech.ApoloInsightsBackEnd.old.domain.FormaPagamento;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record DadosCadastroAgendamentoMultiplo(
        @NotNull(message = "Usuario não informado")
        Long idUsuario,
        @NotEmpty(message = "Serviço não informado")
        List<Long> idsServicos,
        @NotNull(message = "Data não informada")
        LocalDate data,
        @NotNull(message = "horário não informado")
        LocalTime hora,
        @NotNull(message = "Forma de pagamento não informada")
        FormaPagamento formaPagamento
) {
}
