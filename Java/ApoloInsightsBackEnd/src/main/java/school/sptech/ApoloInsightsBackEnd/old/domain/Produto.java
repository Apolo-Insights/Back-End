package school.sptech.ApoloInsightsBackEnd.old.domain;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import school.sptech.ApoloInsightsBackEnd.old.domain.DTO.produto.DadosAtualizacaoProduto;
import school.sptech.ApoloInsightsBackEnd.old.domain.DTO.produto.DadosCadastroProduto;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "produtos")
@Entity(name = "Produto")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    private Double preco;
    private String foto;
    private Integer estoque;

    public Produto(@Valid DadosCadastroProduto dados) {
        this.nome = dados.nome();
        this.descricao = dados.descricao();
        this.preco = dados.preco();
        this.foto = dados.fotoBase64();
        this.estoque = 0;
    }

    public void atualizarInformacoes(DadosAtualizacaoProduto dados) {
        if (dados.nome() != null) this.nome = dados.nome();
        if (dados.descricao() != null) this.descricao = dados.descricao();
        if (dados.preco() != null) this.preco = dados.preco();
        if (dados.fotoBase64() != null) this.foto = dados.fotoBase64();
    }

    public void adicionarEstoque() {
        this.estoque ++;
    }

    public void removerEstoque() {
        this.estoque --;
    }
}
