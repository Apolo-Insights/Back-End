package school.sptech.ApoloInsightsBackEnd.domain;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import school.sptech.ApoloInsightsBackEnd.domain.DTO.horariodisponivel.DadosBloqueioHorario;

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

    public BloqueioEspecifico(Categoria categoria, @Valid DadosBloqueioHorario dados) {
        this.categoria = categoria;
        this.data = dados.data();
        this.horaInicio = dados.horaInicio();
        this.horaFim = dados.horaFim();
    }

}
