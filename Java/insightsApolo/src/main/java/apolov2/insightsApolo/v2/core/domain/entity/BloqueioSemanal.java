package apolov2.insightsApolo.v2.core.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BloqueioSemanal {
    private Long id;
    private Categoria categoria;
    private Integer diaSemana; // 1 = Segunda, 7 = Domingo
    private LocalTime horaInicio;
    private LocalTime horaFim;

    public BloqueioSemanal(Categoria categoria, LocalDate data, LocalTime horaInicio, LocalTime horaFim) {
        this.categoria = categoria;
        this.diaSemana = data.getDayOfWeek().getValue();
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
    }
    //
}
