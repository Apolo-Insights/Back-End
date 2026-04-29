package school.sptech.ApoloInsightsBackEnd.old.domain.DTO.servico;

import school.sptech.ApoloInsightsBackEnd.old.domain.Categoria;
import school.sptech.ApoloInsightsBackEnd.old.domain.Servico;
import school.sptech.ApoloInsightsBackEnd.old.util.DataHoraUtil;

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
