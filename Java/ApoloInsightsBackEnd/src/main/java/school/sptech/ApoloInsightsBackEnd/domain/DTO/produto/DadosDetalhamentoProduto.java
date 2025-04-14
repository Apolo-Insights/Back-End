package school.sptech.ApoloInsightsBackEnd.domain.DTO.produto;

import school.sptech.ApoloInsightsBackEnd.domain.Produto;
import school.sptech.ApoloInsightsBackEnd.domain.Servico;

public record DadosDetalhamentoProduto(
        String nome,
        String descricao,
        Double preco,
        String foto
) {
    public DadosDetalhamentoProduto(Produto produto) {
        this(
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getFoto());
    }
}
