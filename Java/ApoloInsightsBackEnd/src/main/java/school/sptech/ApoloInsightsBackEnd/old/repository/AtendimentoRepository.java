package school.sptech.ApoloInsightsBackEnd.old.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.ApoloInsightsBackEnd.old.domain.Atendimento;

import java.time.LocalDate;
import java.util.List;

public interface AtendimentoRepository extends JpaRepository<Atendimento, Long> {
    List<Atendimento> findByCategoriaIdAndDataBetween(Long idCategoria, LocalDate inicio, LocalDate fim);

    List<Atendimento> findByDataBetween(LocalDate inicioAnterior, LocalDate fimAnterior);
}
