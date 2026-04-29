package apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.repository;

import apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.entity.CategoriaEntity;
import apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.entity.HorarioDisponivelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HorarioDisponivelRepository extends JpaRepository<HorarioDisponivelEntity, Long> {
    List<HorarioDisponivelEntity> findByCategoria(CategoriaEntity categoria);
    List<HorarioDisponivelEntity> findByCategoriaIn(List<CategoriaEntity> categorias);
}
