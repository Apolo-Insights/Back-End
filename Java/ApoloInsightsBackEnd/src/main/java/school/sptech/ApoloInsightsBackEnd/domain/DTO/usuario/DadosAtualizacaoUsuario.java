package school.sptech.ApoloInsightsBackEnd.domain.DTO.usuario;

import school.sptech.ApoloInsightsBackEnd.domain.Genero;
import school.sptech.ApoloInsightsBackEnd.domain.Role;

public record DadosAtualizacaoUsuario(
        String nome,

        String telefone,

        String email,
        Genero genero,
        Role funcao
) {
}