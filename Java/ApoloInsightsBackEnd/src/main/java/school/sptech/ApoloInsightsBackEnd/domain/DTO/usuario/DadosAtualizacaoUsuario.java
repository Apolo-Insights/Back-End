package school.sptech.ApoloInsightsBackEnd.domain.DTO.usuario;

import jakarta.validation.constraints.NotNull;
import school.sptech.ApoloInsightsBackEnd.domain.Genero;

public record DadosAtualizacaoUsuario(
        @NotNull
        Long id,

        String nome,

        String telefone,

        String email,
        Genero genero
) {
}