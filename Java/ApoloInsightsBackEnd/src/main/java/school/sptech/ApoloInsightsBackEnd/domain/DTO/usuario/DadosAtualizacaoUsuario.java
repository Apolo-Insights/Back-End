package school.sptech.ApoloInsightsBackEnd.domain.DTO.usuario;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoUsuario(
        @NotNull
        Long id,

        String nome,

        String telefone,

        String email
) {
}