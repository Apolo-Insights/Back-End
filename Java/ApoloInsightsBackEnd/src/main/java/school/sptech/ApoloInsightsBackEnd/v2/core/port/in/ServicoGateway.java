package school.sptech.ApoloInsightsBackEnd.v2.core.port.in;

import org.springframework.data.domain.Pageable;
import school.sptech.ApoloInsightsBackEnd.v2.core.domain.entity.Servico;
import java.util.List;

public interface ServicoGateway {
    Servico cadastrar(Servico domain);
    Servico atualizar(Servico domain);
    Servico buscarPorId(Long id);
    void deletar(Long id);
    List<Servico> listar(Long idCategoria, Pageable paginacao);
}


