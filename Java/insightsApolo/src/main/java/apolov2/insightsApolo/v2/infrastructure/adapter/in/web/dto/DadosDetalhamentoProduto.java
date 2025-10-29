package apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto;

import apolov2.insightsApolo.v2.core.domain.entity.Produto;

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
                produto.getFoto()
        );
    }
}
