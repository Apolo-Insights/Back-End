package apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.adapter;

import apolov2.insightsApolo.v2.core.domain.entity.BloqueioEspecifico;
import apolov2.insightsApolo.v2.core.domain.entity.Categoria;
import apolov2.insightsApolo.v2.core.domain.port.BloqueioEspecificoGateway;
import apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.entity.CategoriaEntity;
import apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.mapper.BloqueioEspecificoMapper;
import apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.mapper.CategoriaMapper;
import apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.repository.BloqueioEspecificoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BloqueioEspecificoJpaAdapter implements BloqueioEspecificoGateway {

    private final BloqueioEspecificoRepository repository;
    private final BloqueioEspecificoMapper mapper;
    private final CategoriaMapper categoriaMapper;

    @Override
    public BloqueioEspecifico salvar(BloqueioEspecifico bloqueioEspecifico) {
        var entity = mapper.toEntity(bloqueioEspecifico);
        var savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public List<BloqueioEspecifico> buscarPorCategoria(Categoria categoria) {
        CategoriaEntity categoriaEntity = categoriaMapper.categoriaToEntity(categoria);
        return repository.findByCategoria(categoriaEntity).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<BloqueioEspecifico> buscarPorCategorias(List<Categoria> categorias) {
        List<CategoriaEntity> categoriaEntities = categorias.stream()
                .map(categoriaMapper::categoriaToEntity)
                .collect(Collectors.toList());
        
        return repository.findByCategoriaIn(categoriaEntities).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}
