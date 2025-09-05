package school.sptech.ApoloInsightsBackEnd.v2.infrastructure.adapter.out.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.sptech.ApoloInsightsBackEnd.old.domain.Servico;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long> {
    boolean existsByNome(String nome);

    Page<Servico> findByCategoriaId(Long categoriaId, Pageable pageable);
}

