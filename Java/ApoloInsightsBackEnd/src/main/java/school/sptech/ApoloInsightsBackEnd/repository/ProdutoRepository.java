package school.sptech.ApoloInsightsBackEnd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.ApoloInsightsBackEnd.domain.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
