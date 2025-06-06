package school.sptech.ApoloInsightsBackEnd.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Table(name = "atendimentos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Atendimento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "categoria_id", referencedColumnName = "id")
    private Categoria categoria;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id", referencedColumnName = "id")
    private Usuario cliente;

    private Double valor;

    private LocalDate data;

    public Atendimento(Categoria categoria, Double valor, LocalDate data, Usuario cliente) {
        this.categoria = categoria;
        this.valor = valor;
        this.data = data;
        this.cliente = cliente;
    }
}