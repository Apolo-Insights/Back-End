package school.sptech.ApoloInsightsBackEnd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.ApoloInsightsBackEnd.domain.HorarioDisponivel;
import java.util.List;


public interface HorarioDisponivelRepository extends JpaRepository<HorarioDisponivel, Long> {
    List<HorarioDisponivel> findAllByCategoriaIdAndBloqueadoFalse(Long idCategoria);
}

