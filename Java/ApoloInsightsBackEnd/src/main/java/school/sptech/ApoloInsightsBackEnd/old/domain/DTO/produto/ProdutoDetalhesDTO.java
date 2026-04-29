package school.sptech.ApoloInsightsBackEnd.old.domain.DTO.produto;

import school.sptech.ApoloInsightsBackEnd.old.domain.Produto;

public record ProdutoDetalhesDTO(
        Long id,
        String nome,
        String descricao,
        Double preco,
        String foto
) {
    public ProdutoDetalhesDTO(Produto produto) {
        this(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getFoto()
        );
    }
}