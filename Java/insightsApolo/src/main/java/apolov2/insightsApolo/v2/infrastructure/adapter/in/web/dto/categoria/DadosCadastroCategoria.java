package apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.categoria;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroCategoria(
        @NotBlank(message = "O nome da Categoria não foi inserido")
        String nome,
        String fotoBase64
) {
}
