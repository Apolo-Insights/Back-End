package apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto;

import apolov2.insightsApolo.v2.core.domain.entity.Produto;

public record ProdutoDetalhesDTO(
        Long id,
        String nome,
        String descricao,
        Double preco,
        String foto,
        Integer estoque
) {
    public ProdutoDetalhesDTO(Produto produto) {
        this(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getFoto(),
                produto.getEstoque()
        );
    }
}
