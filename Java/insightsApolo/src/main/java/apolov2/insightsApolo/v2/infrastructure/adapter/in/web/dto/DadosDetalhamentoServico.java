package apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto;

import apolov2.insightsApolo.v2.core.domain.entity.Categoria;
import apolov2.insightsApolo.v2.core.domain.entity.Servico;
import apolov2.insightsApolo.v2.infrastructure.util.DataHoraUtil;

public record DadosDetalhamentoServico(
        String nome,
        String descricao,
        Double preco,
        String foto,
        String duracao,
        Categoria categoria
) {
    public DadosDetalhamentoServico(Servico servico) {
        this(
                servico.getNome(),
                servico.getDescricao(),
                servico.getPreco(),
                servico.getFoto(),
                DataHoraUtil.formatarDuracao(servico.getDuracao()),
                servico.getCategoria());
    }
}
