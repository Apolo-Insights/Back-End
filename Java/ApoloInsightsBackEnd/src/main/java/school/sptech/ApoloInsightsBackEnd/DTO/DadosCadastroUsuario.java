package school.sptech.ApoloInsightsBackEnd.DTO;

import jakarta.validation.constraints.NotBlank;

public record DadosCadastroUsuario(
        @NotBlank
        String nome,

        @NotBlank
        String telefone,

        @NotBlank
        String email,

        @NotBlank
        String senha
){}
