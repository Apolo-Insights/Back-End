package apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.adapter;

import apolov2.insightsApolo.v2.core.domain.entity.Servico;
import apolov2.insightsApolo.v2.core.port.in.ServicoGateway;
import apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.entity.ServicoEntity;
import apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.mapper.ServicoMapper;
import apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.repository.ServicoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
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
        ServicoEntity savedEntity = repository.save(entity);
        return mapper.entityToServico(savedEntity);
    }

    @Override
    public Servico atualizar(Servico servico) {
        ServicoEntity entity = mapper.servicoToEntity(servico);
        ServicoEntity updatedEntity = repository.save(entity);
        return mapper.entityToServico(updatedEntity);
    }

    @Override
    public Servico buscarPorId(Long id) {
        ServicoEntity entity = repository.findById(id).orElse(null);
        if (entity == null) return null;
        return mapper.entityToServico(entity);
    }

    @Override
    public List<Servico> buscarPorIds(List<Long> ids) {
        List<ServicoEntity> entities = repository.findAllById(ids);
        return entities.stream()
                .map(mapper::entityToServico)
                .toList();
    }

    @Override
    public void deletar(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Page<Servico> listar(Long idCategoria, Pageable paginacao) {
        Page<ServicoEntity> entitiesPage = repository.findByCategoriaId(idCategoria, paginacao);
        return entitiesPage.map(mapper::entityToServico);
    }
}
