package apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto;

import apolov2.insightsApolo.v2.core.domain.util.FormaPagamento;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record DadosCadastroAgendamento(
        @NotNull(message = "Usuario não informado")
        Long idUsuario,
        @NotNull(message = "Serviço não informado")
        Long idServico,
        @NotNull(message = "Data não informada")
        LocalDate data,
        @NotNull(message = "horário não informado")
        LocalTime hora,
        @NotNull(message = "Forma de pagamento não informada")
        FormaPagamento formaPagamento
) { }
