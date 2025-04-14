package school.sptech.ApoloInsightsBackEnd.domain.DTO.produto;

import school.sptech.ApoloInsightsBackEnd.domain.Produto;

public record ProdutoDetalhesDTO(
        Long id,
        String nome,
        String descricao,
        Double preco,
        String foto
) {
    public ProdutoDetalhesDTO(Produto produto) {
        this(
                produto.getId(),   // Inclua o ID do produto aqui
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getFoto()
        );
    }
}