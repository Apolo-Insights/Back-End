package school.sptech.ApoloInsightsBackEnd.v2.infrastructure.adapter.out.jpa.adapter;

import org.springframework.data.domain.Pageable;
import school.sptech.ApoloInsightsBackEnd.v2.core.domain.entity.Servico;
import school.sptech.ApoloInsightsBackEnd.v2.core.port.in.ServicoGateway;
import school.sptech.ApoloInsightsBackEnd.v2.infrastructure.adapter.out.jpa.entity.ServicoEntity;
import school.sptech.ApoloInsightsBackEnd.v2.infrastructure.adapter.out.jpa.mapper.ServicoMapper;
import school.sptech.ApoloInsightsBackEnd.v2.infrastructure.adapter.out.jpa.repository.ServicoRepository;
import java.util.List;

public class ServicoJpaAdapter implements ServicoGateway {
    private final ServicoRepository repository;
    private final ServicoMapper mapper;

    public ServicoJpaAdapter(ServicoRepository repository, ServicoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Servico cadastrar(Servico servico) {
        ServicoEntity entity = mapper.servicoToEntity(servico);
        return mapper.entityToServico(entity);
    }

    @Override
    public Servico atualizar(Servico domain) {
        return null;
    }

    @Override
    public Servico buscarPorId(Long id) {
        return null;
    }

    @Override
    public void deletar(Long id) {

    }

    @Override
    public List<Servico> listar(Long idCategoria, Pageable paginacao) {
        return List.of();
    }
}
