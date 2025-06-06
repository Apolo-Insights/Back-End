package school.sptech.ApoloInsightsBackEnd.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "horarios_disponiveis")
@AllArgsConstructor
@NoArgsConstructor
public class HorarioDisponivel {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private DayOfWeek diaSemana;
    private LocalDate data;
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    private LocalTime horaInicio;
    private LocalTime horaFim;


    public HorarioDisponivel(Categoria categoria, DayOfWeek diaSemana, LocalTime horaInicio, LocalTime horaFim) {
        this.categoria = categoria;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
    }
}
