package apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto;

import apolov2.insightsApolo.v2.core.domain.entity.Produto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosListagemProduto(
        Long id,
        @NotBlank(message = "O nome do Produto não foi inserido")
        String nome,
        String descricao,
        @NotNull(message = "O preço do Produto não foi inserido")
        Double preco,
        String foto,
        Integer estoque
) {
    public DadosListagemProduto(Produto produto) {
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
