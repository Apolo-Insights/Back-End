package apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.entity;

import apolov2.insightsApolo.v2.core.domain.util.FormaPagamento;
import apolov2.insightsApolo.v2.core.domain.util.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "agendamentos")
@Entity(name = "Agendamento")
@EqualsAndHashCode(of = "id")
public class AgendamentoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioEntity usuario;

    @ManyToOne
    @JoinColumn(name = "servico_id")
    private ServicoEntity servico;
    
    private LocalDate data;
    private LocalTime hora;
    
    @Enumerated(EnumType.STRING)
    private FormaPagamento formaPagamento;
    
    @Enumerated(EnumType.STRING)
    private Status status;
}
