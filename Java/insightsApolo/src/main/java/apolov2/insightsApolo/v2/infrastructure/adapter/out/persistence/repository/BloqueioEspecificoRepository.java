package apolov2.insightsApolo.v2.infrastructure.adapter.out.persistence.repository;

import apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.entity.CategoriaEntity;
import apolov2.insightsApolo.v2.infrastructure.adapter.out.persistence.entity.BloqueioEspecificoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BloqueioEspecificoRepository extends JpaRepository<BloqueioEspecificoEntity, Long> {
    List<BloqueioEspecificoEntity> findByCategoria(CategoriaEntity categoria);
    List<BloqueioEspecificoEntity> findByCategoriaIn(List<CategoriaEntity> categorias);
}
