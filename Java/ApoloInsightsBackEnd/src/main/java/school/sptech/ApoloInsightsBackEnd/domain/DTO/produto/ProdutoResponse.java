//Converte um ProdutoResponse (DTO) para uma entidade Produt

package school.sptech.ApoloInsightsBackEnd.domain.DTO.produto;

import lombok.Getter;
import lombok.Setter;
import school.sptech.ApoloInsightsBackEnd.domain.Produto;

@Getter
@Setter
public class ProdutoResponse {

    private Integer id;


    private String nome;

    private String descricao;

    private Double valor;

    public static Produto toResponse(ProdutoResponse dtoProdutoResp){
        Produto produtos = new Produto();
        produtos.setNome(dtoProdutoResp.nome);
        produtos.setDescricao(dtoProdutoResp.descricao);
        produtos.setValor(dtoProdutoResp.valor);
        return produtos;
    }
}
