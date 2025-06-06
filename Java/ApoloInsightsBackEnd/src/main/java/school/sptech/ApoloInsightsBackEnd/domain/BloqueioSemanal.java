package school.sptech.ApoloInsightsBackEnd.domain;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.horariodisponivel.DadosBloqueioHorario;
import java.time.LocalTime;

@Entity
@Table(name = "bloqueio_semanal")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BloqueioSemanal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @Column(name = "dia_semana", nullable = false)
    private int diaSemana; // 0 = Domingo, 6 = Sábado

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "hora_fim", nullable = false)
    private LocalTime horaFim;

    public BloqueioSemanal(Categoria categoria, @Valid DadosBloqueioHorario dados) {
        this.categoria = categoria;
        this.diaSemana = dados.data().getDayOfWeek().getValue() % 7;
        this.horaInicio = dados.horaInicio();
        this.horaFim = dados.horaFim();
    }
}
