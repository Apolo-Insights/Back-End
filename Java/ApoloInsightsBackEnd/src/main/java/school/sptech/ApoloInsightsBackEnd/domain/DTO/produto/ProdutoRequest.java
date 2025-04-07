//DTO de requisição para o cadastro de produtos

package school.sptech.ApoloInsightsBackEnd.domain.DTO.produto;
import lombok.Getter;
import lombok.Setter;
import school.sptech.ApoloInsightsBackEnd.domain.Produto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Getter
@Setter
public class ProdutoRequest {


    @NotBlank
    private String nome;

    @NotBlank
    private String descricao;

    @Positive(message = "O valor deve ser maior que zero")
    @NotNull
    private Double valor;


        public static Produto toEntity(ProdutoRequest dtoProduto){
            Produto produtos = new Produto();
            produtos.setNome(dtoProduto.nome);
            produtos.setDescricao(dtoProduto.descricao);
            produtos.setValor(dtoProduto.valor);
            return produtos;
        }

}
