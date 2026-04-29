package apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.adapter;

import apolov2.insightsApolo.v2.core.domain.entity.Agendamento;
import apolov2.insightsApolo.v2.core.port.in.AgendamentoGateway;
import apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.entity.AgendamentoEntity;
import apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.mapper.AgendamentoMapper;
import apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.repository.AgendamentoRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class AgendamentoJpaAdapter implements AgendamentoGateway {
    private final AgendamentoRepository repository;
    private final AgendamentoMapper mapper;

    public AgendamentoJpaAdapter(AgendamentoRepository repository, AgendamentoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Agendamento salvar(Agendamento agendamento) {
        AgendamentoEntity entity = mapper.agendamentoToEntity(agendamento);
        AgendamentoEntity savedEntity = repository.save(entity);
        return mapper.entityToAgendamento(savedEntity);
    }

    @Override
    public List<Agendamento> salvarTodos(List<Agendamento> agendamentos) {
        List<AgendamentoEntity> entities = agendamentos.stream()
                .map(mapper::agendamentoToEntity)
                .toList();
        List<AgendamentoEntity> savedEntities = repository.saveAll(entities);
        return savedEntities.stream()
                .map(mapper::entityToAgendamento)
                .toList();
    }

    @Override
    public Optional<Agendamento> buscarPorId(Long id) {
        return repository.findById(id)
                .map(mapper::entityToAgendamento);
    }

    @Override
    public Agendamento atualizar(Agendamento agendamento) {
        AgendamentoEntity entity = mapper.agendamentoToEntity(agendamento);
        AgendamentoEntity updatedEntity = repository.save(entity);
        return mapper.entityToAgendamento(updatedEntity);
    }

    @Override
    public List<Agendamento> buscarPorUsuarioId(Long usuarioId) {
        List<AgendamentoEntity> entities = repository.findByUsuarioId(usuarioId);
        return entities.stream()
                .map(mapper::entityToAgendamento)
                .toList();
    }

    @Override
    public List<Agendamento> buscarPorCategoriaEPeriodo(Long idCategoria, LocalDate inicio, LocalDate fim) {
        List<AgendamentoEntity> entities = repository.findByServico_Categoria_IdAndDataBetween(idCategoria, inicio, fim);
        return entities.stream()
                .map(mapper::entityToAgendamento)
                .toList();
    }
}
