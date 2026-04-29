package apolov2.insightsApolo.v2.core.port.in;

import apolov2.insightsApolo.v2.core.domain.entity.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProdutoGateway {
    Produto cadastrar(Produto produto);
    Produto atualizar(Produto produto);
    Produto buscarPorId(Long id);
    void deletar(Long id);
    Page<Produto> listar(Pageable paginacao);
}
