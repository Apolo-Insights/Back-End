package school.sptech.ApoloInsightsBackEnd.old.domain.DTO.produto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import school.sptech.ApoloInsightsBackEnd.old.domain.Produto;

public record DadosListagemProduto(

        Long id,
        @NotBlank(message = "O nome do produto não foi inserido")
        String nome,
        String descricao,
        @NotNull(message = "O preço do produto não foi inserido")
        Double preco,
        String foto,
        Integer estoque
) {
        public DadosListagemProduto(Produto produto){
                this(
                        produto.getId(),
                        produto.getNome(),
                        produto.getDescricao(),
                        produto.getPreco(),
                        produto.getFoto(),
                        produto.getEstoque());
        }
}
