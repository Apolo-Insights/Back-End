package school.sptech.ApoloInsightsBackEnd.domain.DTO.usuario;

import jakarta.validation.constraints.NotBlank;

public record DadosAtualizarSenha(
        @NotBlank
        String email,
        @NotBlank
        String novaSenha
) {
}
