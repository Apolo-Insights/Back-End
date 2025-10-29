package apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.repository;

import apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.entity.AgendamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<AgendamentoEntity, Long> {
    List<AgendamentoEntity> findByUsuarioId(Long usuarioId);
    List<AgendamentoEntity> findByServico_Categoria_IdAndDataBetween(Long idCategoria, LocalDate inicio, LocalDate fim);
}
