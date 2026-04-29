package school.sptech.ApoloInsightsBackEnd.old.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.ApoloInsightsBackEnd.old.domain.BloqueioEspecifico;
import school.sptech.ApoloInsightsBackEnd.old.domain.Categoria;

import java.util.List;

public interface BloqueioEspecificoRepository extends JpaRepository<BloqueioEspecifico, Long> {
    List<BloqueioEspecifico> findByCategoria(Categoria categoria);

    List<BloqueioEspecifico> findByCategoriaIn(List<Categoria> categorias);
}
