package school.sptech.ApoloInsightsBackEnd.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.categoria.DadosAtualizacaoCategoria;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.categoria.DadosCadastroCategoria;

@Entity
@Table(name = "categorias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String foto;

    public Categoria(DadosCadastroCategoria dados) {
        this.nome = dados.nome();
        this.foto = dados.fotoBase64();
    }

    public void atualizarInformacoes(DadosAtualizacaoCategoria dados) {
        if (dados.nome() != null) this.nome = dados.nome();
        if (dados.fotoBase64() != null) this.foto = dados.fotoBase64();
    }
}
