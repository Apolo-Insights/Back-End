package school.sptech.ApoloInsightsBackEnd.repository;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.ApoloInsightsBackEnd.domain.Produto;

import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {


    boolean existsByCodigoDoProdutoIgnoreCase(@NotBlank String codigoDoProduto);

    Optional<Produto> findByCodigoDoProduto(String codigo);

}
