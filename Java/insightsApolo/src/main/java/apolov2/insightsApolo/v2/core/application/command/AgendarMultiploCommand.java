package apolov2.insightsApolo.v2.core.application.command;

import apolov2.insightsApolo.v2.core.domain.util.FormaPagamento;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record AgendarMultiploCommand(
        Long idUsuario,
        List<Long> idsServicos,
        LocalDate data,
        LocalTime hora,
        FormaPagamento formaPagamento
) {
}
