package school.sptech.ApoloInsightsBackEnd.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.sptech.ApoloInsightsBackEnd.domain.Agendamento;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    List<Agendamento> findByUsuarioId(Long usuarioId);

    List<Agendamento> findByServico_Categoria_IdAndDataBetween(Long idCategoria, LocalDate inicio, LocalDate fim);

    void deleteByUsuarioId(Long id);
}
