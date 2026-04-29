package apolov2.insightsApolo.v2.core.port.in;

import apolov2.insightsApolo.v2.core.domain.entity.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CategoriaGateway {
    Optional<Categoria> buscarPorId(Long id);
    Categoria salvar(Categoria categoria);
    boolean existePorNome(String nome);
    Page<Categoria> listarTodas(Pageable paginacao);
    Categoria atualizar(Categoria categoria);
    void deletar(Categoria categoria);
}
