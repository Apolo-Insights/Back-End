package apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroProduto(
        @NotBlank(message = "O nome do Produto não foi inserido")
        String nome,
        String descricao,
        @NotNull(message = "O preço do Produto não foi inserido")
        Double preco,
        String fotoBase64
) {
}
