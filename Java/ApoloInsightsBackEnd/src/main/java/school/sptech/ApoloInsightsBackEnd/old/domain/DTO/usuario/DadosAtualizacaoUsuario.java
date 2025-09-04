package school.sptech.ApoloInsightsBackEnd.old.domain.DTO.usuario;

import school.sptech.ApoloInsightsBackEnd.old.domain.Genero;
import school.sptech.ApoloInsightsBackEnd.old.domain.Role;

public record DadosAtualizacaoUsuario(
        String nome,

        String telefone,
        String cpf,
        String email,
        Genero genero,
        Role funcao
) {
}