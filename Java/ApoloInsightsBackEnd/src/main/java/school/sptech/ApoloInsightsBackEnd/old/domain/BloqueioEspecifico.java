package school.sptech.ApoloInsightsBackEnd.old.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bloqueio_especifico")
public class BloqueioEspecifico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @Column(nullable = false)
    private LocalDate data;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "hora_fim", nullable = false)
    private LocalTime horaFim;

    public BloqueioEspecifico(Categoria categoria, LocalDate data, LocalTime horaInicio, LocalTime horaFim) {
        this.categoria = categoria;
        this.data = data;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
    }

}
