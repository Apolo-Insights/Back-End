package apolov2.insightsApolo.v2.core.port.in;

import apolov2.insightsApolo.v2.core.domain.entity.Servico;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ServicoGateway {
    Servico cadastrar(Servico domain);
    Servico atualizar(Servico domain);
    Servico buscarPorId(Long id);
    void deletar(Long id);
    List<Servico> listar(Long idCategoria, Pageable paginacao);
}


