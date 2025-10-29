package apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.atendimento;

import jakarta.validation.constraints.NotNull;

public record DadosDetalhamentoAtendimento(
        @NotNull
        Long idCategoria,
        @NotNull
        Double valor
) {
}
