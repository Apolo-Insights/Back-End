package school.sptech.ApoloInsightsBackEnd.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.servico.DadosAtualizacaoServico;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.servico.DadosCadastroServico;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "servicos")
@Entity(name = "Servico")
@EqualsAndHashCode(of = "id")
public class Servico {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    private Double preco;
    private String foto;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    public Servico(DadosCadastroServico dados, Categoria categoria) {
        this.nome = dados.nome();
        this.descricao = dados.descricao();
        this.preco = dados.preco();
        this.foto = dados.foto();
        this.categoria = categoria;
    }

    public void atualizarInformacoes(DadosAtualizacaoServico dados) {
        if (dados.nome() != null) this.nome = dados.nome();
        if (dados.descricao() != null) this.descricao = dados.descricao();
        if (dados.preco() != null) this.preco = dados.preco();
        if (dados.foto() != null) this.foto = dados.foto();
    }


}
