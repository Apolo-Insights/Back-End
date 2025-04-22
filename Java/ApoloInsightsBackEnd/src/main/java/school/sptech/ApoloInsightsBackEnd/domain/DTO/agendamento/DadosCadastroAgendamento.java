package school.sptech.ApoloInsightsBackEnd.domain.DTO.agendamento;

import jakarta.validation.constraints.NotNull;
import school.sptech.ApoloInsightsBackEnd.domain.FormaPagamento;

import java.time.LocalDate;
import java.time.LocalTime;

public record DadosCadastroAgendamento(
        @NotNull(message = "Usuario não informado")
        Long usuarioId,
        @NotNull(message = "Serviço não informado")
        Long servicoId,
        @NotNull(message = "Data não informada")
        LocalDate data,
        @NotNull(message = "horário não informado")
        LocalTime hora,
        @NotNull(message = "Forma de pagamento não informada")
        FormaPagamento formaPagamento
) { }
