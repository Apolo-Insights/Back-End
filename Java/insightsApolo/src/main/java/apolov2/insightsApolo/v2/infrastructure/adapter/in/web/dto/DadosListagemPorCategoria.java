package apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto;

import apolov2.insightsApolo.v2.core.domain.util.Status;

import java.time.LocalDate;
import java.time.LocalTime;

public record DadosListagemPorCategoria(
        Long idAgendamento,
        String cliente,
        String servico,
        LocalDate data,
        LocalTime hora,
        Status status,
        String formaDePagamento
) {
}
