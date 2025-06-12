package school.sptech.ApoloInsightsBackEnd.domain.DTO.servico;

import school.sptech.ApoloInsightsBackEnd.domain.Categoria;
import school.sptech.ApoloInsightsBackEnd.domain.Servico;
import school.sptech.ApoloInsightsBackEnd.util.DataHoraUtil;

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
