package school.sptech.ApoloInsightsBackEnd.domain.DTO.servico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DadosCadastroServico(
        @NotNull(message = "A categoria não foi informada")
        Long idCategoria,
        @NotBlank(message = "O nome do Serviço não foi inserido")
        String nome,
        String descricao,

        @Positive(message = "O preço do Serviço deve ser maior que zero")
        @NotNull(message = "O preço do Serviço não foi inserido")
        Double preco,

        String foto
) {
}
