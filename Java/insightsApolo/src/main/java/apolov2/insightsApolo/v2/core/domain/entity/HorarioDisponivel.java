package apolov2.insightsApolo.v2.core.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HorarioDisponivel {
    private Long id;
    private Categoria categoria;
    private DayOfWeek diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFim;
    private Boolean bloqueado;

    public HorarioDisponivel(Categoria categoria, DayOfWeek diaSemana, LocalTime horaInicio, LocalTime horaFim) {
        this.categoria = categoria;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.bloqueado = false; // Valor padrão
    }
}
