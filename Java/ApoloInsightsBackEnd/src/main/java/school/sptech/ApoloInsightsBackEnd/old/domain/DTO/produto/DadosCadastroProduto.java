package school.sptech.ApoloInsightsBackEnd.old.domain.DTO.produto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroProduto(
        @NotBlank(message = "O nome do Serviço não foi inserido")
        String nome,
        String descricao,

        @NotNull(message = "O preço do Produto não foi inserido")
        Double preco,

        String fotoBase64
) {

}
