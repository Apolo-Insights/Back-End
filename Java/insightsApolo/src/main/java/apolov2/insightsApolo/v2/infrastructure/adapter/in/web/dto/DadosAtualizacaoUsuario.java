package apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto;

import apolov2.insightsApolo.v2.core.domain.util.Genero;
import apolov2.insightsApolo.v2.core.domain.util.Role;

public record DadosAtualizacaoUsuario(
        String nome,
        String telefone,
        String email,
        Genero genero,
        String cpf,
        Role funcao
) {
}
