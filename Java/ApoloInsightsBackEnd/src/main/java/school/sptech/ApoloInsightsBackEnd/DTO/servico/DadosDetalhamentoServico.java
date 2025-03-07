package school.sptech.ApoloInsightsBackEnd.DTO.servico;

import school.sptech.ApoloInsightsBackEnd.domain.Servico;

public record DadosDetalhamentoServico(
        String nome,
        String descricao,
        Double preco,
        String foto
) {
    public DadosDetalhamentoServico(Servico servico) {
        this(servico.getNome(), servico.getDescricao(), servico.getPreco(), servico.getFoto());
    }
}
