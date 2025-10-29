package apolov2.insightsApolo.v2.infrastructure.adapter.out.persistence.adapter;

import apolov2.insightsApolo.v2.core.domain.entity.BloqueioSemanal;
import apolov2.insightsApolo.v2.core.domain.entity.Categoria;
import apolov2.insightsApolo.v2.core.domain.port.BloqueioSemanalGateway;
import apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.entity.CategoriaEntity;
import apolov2.insightsApolo.v2.infrastructure.adapter.out.persistence.mapper.BloqueioSemanalMapper;
import apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.mapper.CategoriaMapper;
import apolov2.insightsApolo.v2.infrastructure.adapter.out.persistence.repository.BloqueioSemanalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BloqueioSemanalJpaAdapter implements BloqueioSemanalGateway {

    private final BloqueioSemanalRepository repository;
    private final BloqueioSemanalMapper mapper;
    private final CategoriaMapper categoriaMapper;

    @Override
    public BloqueioSemanal salvar(BloqueioSemanal bloqueioSemanal) {
        var entity = mapper.toEntity(bloqueioSemanal);
        var savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public List<BloqueioSemanal> buscarPorCategoria(Categoria categoria) {
        CategoriaEntity categoriaEntity = categoriaMapper.categoriaToEntity(categoria);
        return repository.findByCategoria(categoriaEntity).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<BloqueioSemanal> buscarPorCategorias(List<Categoria> categorias) {
        List<CategoriaEntity> categoriaEntities = categorias.stream()
                .map(categoriaMapper::categoriaToEntity)
                .collect(Collectors.toList());
        
        return repository.findByCategoriaIn(categoriaEntities).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}
