package school.sptech.ApoloInsightsBackEnd.old.domain.DTO.produto;

import school.sptech.ApoloInsightsBackEnd.old.domain.Produto;

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
