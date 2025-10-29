package apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto;

import apolov2.insightsApolo.v2.core.domain.util.FormaPagamento;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

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
