package school.sptech.ApoloInsightsBackEnd.domain.DTO.categoria;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroCategoria(
        @NotBlank(message = "O nome da Categoria não foi inserido")
        String nome,
        String foto
) {
}
