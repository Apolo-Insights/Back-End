package school.sptech.ApoloInsightsBackEnd.domain.DTO.produto;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoProduto(
        @NotNull(message = "O id do produto não foi inserido")
        String nome,
        String descricao,
        Double preco,
        String foto
) {
}
