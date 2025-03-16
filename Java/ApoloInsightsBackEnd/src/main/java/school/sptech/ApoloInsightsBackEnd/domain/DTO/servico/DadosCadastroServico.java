package school.sptech.ApoloInsightsBackEnd.domain.DTO.servico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroServico(
        @NotBlank(message = "O nome do Serviço não foi inserido")
        String nome,
        String descricao,

        @NotNull(message = "O preço do Serviço não foi inserido")
        Double preco,

        String foto
) {
}
