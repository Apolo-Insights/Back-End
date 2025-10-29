package apolov2.insightsApolo.v2.core.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BloqueioEspecifico {
    private Long id;
    private Categoria categoria;
    private LocalDate data;
    private LocalTime horaInicio;
    private LocalTime horaFim;

    public BloqueioEspecifico(Categoria categoria, LocalDate data, LocalTime horaInicio, LocalTime horaFim) {
        this.categoria = categoria;
        this.data = data;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
    }
}
