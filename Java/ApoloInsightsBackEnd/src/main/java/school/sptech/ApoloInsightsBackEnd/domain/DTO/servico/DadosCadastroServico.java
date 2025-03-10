package school.sptech.ApoloInsightsBackEnd.domain.DTO.servico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroServico(
        @NotBlank(message = "O nome do Serviço não foi inserido")
        String nome,

        @NotBlank(message = "A descrição do Serviço não foi inserida")
        String descricao,

        @NotNull(message = "O preço do Serviço não foi inserido")
        Double preco,

        String foto
) {
}
