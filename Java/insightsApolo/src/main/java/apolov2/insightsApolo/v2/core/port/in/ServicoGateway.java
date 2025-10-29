package apolov2.insightsApolo.v2.core.port.in;

import apolov2.insightsApolo.v2.core.domain.entity.Servico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ServicoGateway {
    Servico cadastrar(Servico domain);
    Servico atualizar(Servico domain);
    Servico buscarPorId(Long id);
    List<Servico> buscarPorIds(List<Long> ids);
    void deletar(Long id);
    Page<Servico> listar(Long idCategoria, Pageable paginacao);
}


