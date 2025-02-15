package school.sptech.ApoloInsightsBackEnd.DTO;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoUsuario(
        @NotNull
        Long id,

        String nome,

        String telefone,

        String email
) {
}