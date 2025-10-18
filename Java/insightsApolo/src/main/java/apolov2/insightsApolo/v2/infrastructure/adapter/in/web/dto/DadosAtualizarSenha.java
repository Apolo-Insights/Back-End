package apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;

public record DadosAtualizarSenha(
        @NotBlank(message = "Email é obrigatório")
        String email,
        
        @NotBlank(message = "Nova senha é obrigatória")
        String novaSenha
) {
}
