package school.sptech.ApoloInsightsBackEnd.v2.core.application.command;

import school.sptech.ApoloInsightsBackEnd.old.domain.Categoria;
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
