package school.sptech.ApoloInsightsBackEnd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import school.sptech.ApoloInsightsBackEnd.domain.Servico;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long> {
    boolean existsByNome(String nome);
}
