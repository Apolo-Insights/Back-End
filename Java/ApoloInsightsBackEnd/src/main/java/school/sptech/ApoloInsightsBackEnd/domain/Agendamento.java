package school.sptech.ApoloInsightsBackEnd.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "agendamentos")
public class Agendamento {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "servico_id")
    private Servico servico;
    private LocalDate data;
    private LocalTime hora;
    @Enumerated(EnumType.STRING)
    private FormaPagamento formaPagamento;

    public Agendamento(Usuario usuario, Servico servico, LocalDate data, LocalTime hora) {
        this.usuario = usuario;
        this.servico = servico;
        this.data = data;
        this.hora = hora;
    }
}
