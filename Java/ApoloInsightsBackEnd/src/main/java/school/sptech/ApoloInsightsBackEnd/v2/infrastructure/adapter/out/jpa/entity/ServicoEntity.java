package school.sptech.ApoloInsightsBackEnd.v2.infrastructure.adapter.out.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import school.sptech.ApoloInsightsBackEnd.old.domain.Categoria;
import school.sptech.ApoloInsightsBackEnd.old.domain.DTO.servico.DadosAtualizacaoServico;
import school.sptech.ApoloInsightsBackEnd.old.domain.DTO.servico.DadosCadastroServico;

import java.time.Duration;
import java.time.LocalTime;

import static school.sptech.ApoloInsightsBackEnd.old.util.DataHoraUtil.formatter;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "servicos")
@Entity(name = "Servico")
@EqualsAndHashCode(of = "id")
public class ServicoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    private Double preco;
    private String foto;
    private Duration duracao;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    public ServicoEntity(DadosCadastroServico dados, Categoria categoria) {
        this.nome = dados.nome();
        this.descricao = dados.descricao();
        this.preco = dados.preco();
        this.foto = dados.fotoBase64();
        this.categoria = categoria;
        // Espera uma string no formato HH:mm (ex: "01:30")
        LocalTime tempo = LocalTime.parse(dados.duracao(), formatter);
        this.duracao = Duration.between(LocalTime.MIN, tempo);
    }

    public void atualizarInformacoes(DadosAtualizacaoServico dados) {
        if (dados.nome() != null) this.nome = dados.nome();
        if (dados.descricao() != null) this.descricao = dados.descricao();
        if (dados.preco() != null) this.preco = dados.preco();
        if (dados.fotoBase64() != null) this.foto = dados.fotoBase64();
        if (dados.duracao() != null) {
            // Espera uma string no formato HH:mm (ex: "01:30")
            LocalTime tempo = LocalTime.parse(dados.duracao(), formatter);
            this.duracao = Duration.between(LocalTime.MIN, tempo);
        }
    }
}
