package school.sptech.ApoloInsightsBackEnd.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.web.bind.annotation.PostMapping;

@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String codigoDoProduto;

    @NotBlank
    private String nome;
    private String descricao;
    private String imagem;

    @Positive
    private Double valor;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public @NotBlank String getCodigoDoProduto() {
        return codigoDoProduto;
    }

    public void setCodigoDoProduto(@NotBlank String codigoDoProduto) {
        this.codigoDoProduto = codigoDoProduto;
    }

    public @NotBlank String getNome() {
        return nome;
    }

    public void setNome(@NotBlank String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public @Positive Double getValor() {
        return valor;
    }

    public void setValor(@Positive Double valor) {
        this.valor = valor;
    }
}
