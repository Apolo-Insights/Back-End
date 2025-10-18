package apolov2.insightsApolo.v2.core.application.command;

import apolov2.insightsApolo.v2.core.domain.entity.Categoria;

import java.time.Duration;

public record CadastrarServicoCommand(
        String nome,
        String descricao,
        Double preco,
        String foto,
        Duration duracao,
        Categoria categoria
) {
}
