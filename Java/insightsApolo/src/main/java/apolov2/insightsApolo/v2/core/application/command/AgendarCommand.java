package apolov2.insightsApolo.v2.core.application.command;

import apolov2.insightsApolo.v2.core.domain.util.FormaPagamento;

import java.time.LocalDate;
import java.time.LocalTime;

public record AgendarCommand(
        Long idUsuario,
        Long idServico,
        LocalDate data,
        LocalTime hora,
        FormaPagamento formaPagamento
) {
}
