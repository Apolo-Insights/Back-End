package apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.adapter;

import apolov2.insightsApolo.v2.core.domain.entity.Atendimento;
import apolov2.insightsApolo.v2.core.port.out.AtendimentoGateway;
import apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.mapper.AtendimentoMapper;
import apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.repository.AtendimentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AtendimentoJpaAdapter implements AtendimentoGateway {

    private final AtendimentoRepository repository;
    private final AtendimentoMapper mapper;

    @Override
    public List<Atendimento> buscarPorCategoriaEPeriodo(Long categoriaId, LocalDate inicio, LocalDate fim) {
        return repository.findByCategoriaIdAndDataBetween(categoriaId, inicio, fim).stream()
                .map(mapper::entityToAtendimento)
                .collect(Collectors.toList());
    }

    @Override
    public List<Atendimento> buscarPorPeriodo(LocalDate inicio, LocalDate fim) {
        return repository.findByDataBetween(inicio, fim).stream()
                .map(mapper::entityToAtendimento)
                .collect(Collectors.toList());
    }

    @Override
    public Atendimento salvar(Atendimento atendimento) {
        var entity = mapper.atendimentoToEntity(atendimento);
        var savedEntity = repository.save(entity);
        return mapper.entityToAtendimento(savedEntity);
    }
}
