package school.sptech.ApoloInsightsBackEnd.old.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
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

    public BloqueioSemanal(Categoria categoria, LocalDate data, LocalTime horaInicio, LocalTime horaFim) {
        int diaSemana = data.getDayOfWeek().getValue() == 0 ? 7 : data.getDayOfWeek().getValue(); // Ajusta Domingo para 7
        this.categoria = categoria;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
    }
}
