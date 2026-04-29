package apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.repository;

import apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.entity.ServicoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicoRepository extends JpaRepository<ServicoEntity, Long> {
    boolean existsByNome(String nome);

    Page<ServicoEntity> findByCategoriaId(Long categoriaId, Pageable pageable);
}

