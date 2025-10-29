package apolov2.insightsApolo.v2.infrastructure.adapter.out.persistence.repository;

import apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.entity.CategoriaEntity;
import apolov2.insightsApolo.v2.infrastructure.adapter.out.persistence.entity.BloqueioSemanalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BloqueioSemanalRepository extends JpaRepository<BloqueioSemanalEntity, Long> {
    List<BloqueioSemanalEntity> findByCategoria(CategoriaEntity categoria);
    List<BloqueioSemanalEntity> findByCategoriaIn(List<CategoriaEntity> categorias);
}
