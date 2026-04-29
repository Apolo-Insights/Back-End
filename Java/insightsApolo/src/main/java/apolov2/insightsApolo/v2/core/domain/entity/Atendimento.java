package apolov2.insightsApolo.v2.core.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Atendimento {
    private Long id;
    private Categoria categoria;
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
