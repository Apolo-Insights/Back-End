package school.sptech.ApoloInsightsBackEnd.domain.DTO.atendimento;

import jakarta.validation.constraints.NotNull;
import school.sptech.ApoloInsightsBackEnd.domain.Atendimento;

public record DadosDetalhamentoAtendimento(
        @NotNull
        Long idCategoria,
        @NotNull
        Double valor
) {
        public DadosDetalhamentoAtendimento(Atendimento atendimento) {
        this(
                atendimento.getCategoria().getId(),
                atendimento.getValor()
        );
        }
}
