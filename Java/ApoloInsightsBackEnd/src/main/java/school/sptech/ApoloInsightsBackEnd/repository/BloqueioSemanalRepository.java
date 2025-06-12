package school.sptech.ApoloInsightsBackEnd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.sptech.ApoloInsightsBackEnd.domain.BloqueioSemanal;
import school.sptech.ApoloInsightsBackEnd.domain.Categoria;

import java.util.List;

@Repository
public interface BloqueioSemanalRepository extends JpaRepository<BloqueioSemanal, Long> {
    List<BloqueioSemanal> findByCategoria(Categoria categoria);

    List<BloqueioSemanal> findByCategoriaIn(List<Categoria> categorias);
}
