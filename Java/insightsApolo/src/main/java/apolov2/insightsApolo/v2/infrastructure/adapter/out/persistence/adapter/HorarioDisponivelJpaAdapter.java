package apolov2.insightsApolo.v2.infrastructure.adapter.out.persistence.adapter;

import apolov2.insightsApolo.v2.core.domain.entity.Categoria;
import apolov2.insightsApolo.v2.core.domain.entity.HorarioDisponivel;
import apolov2.insightsApolo.v2.core.domain.port.HorarioDisponivelGateway;
import apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.entity.CategoriaEntity;
import apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.mapper.CategoriaMapper;
import apolov2.insightsApolo.v2.infrastructure.adapter.out.persistence.mapper.HorarioDisponivelMapper;
import apolov2.insightsApolo.v2.infrastructure.adapter.out.persistence.repository.HorarioDisponivelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class HorarioDisponivelJpaAdapter implements HorarioDisponivelGateway {

    private final HorarioDisponivelRepository repository;
    private final HorarioDisponivelMapper mapper;
    private final CategoriaMapper categoriaMapper;

    @Override
    public HorarioDisponivel salvar(HorarioDisponivel horarioDisponivel) {
        var entity = mapper.toEntity(horarioDisponivel);
        var savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public List<HorarioDisponivel> buscarPorCategoria(Categoria categoria) {
        CategoriaEntity categoriaEntity = categoriaMapper.categoriaToEntity(categoria);
        return repository.findByCategoria(categoriaEntity).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<HorarioDisponivel> buscarPorCategorias(List<Categoria> categorias) {
        List<CategoriaEntity> categoriaEntities = categorias.stream()
                .map(categoriaMapper::categoriaToEntity)
                .collect(Collectors.toList());
        
        return repository.findByCategoriaIn(categoriaEntities).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}
