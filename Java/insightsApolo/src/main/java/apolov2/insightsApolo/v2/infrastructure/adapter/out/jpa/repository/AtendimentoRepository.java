package apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.repository;

import apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.entity.AtendimentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AtendimentoRepository extends JpaRepository<AtendimentoEntity, Long> {
    List<AtendimentoEntity> findByCategoriaIdAndDataBetween(Long categoriaId, LocalDate inicio, LocalDate fim);
    List<AtendimentoEntity> findByDataBetween(LocalDate inicio, LocalDate fim);
}
