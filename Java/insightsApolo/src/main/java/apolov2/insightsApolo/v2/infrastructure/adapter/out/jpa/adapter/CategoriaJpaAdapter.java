package apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.adapter;

import apolov2.insightsApolo.v2.core.domain.entity.Categoria;
import apolov2.insightsApolo.v2.core.port.in.CategoriaGateway;
import apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.entity.CategoriaEntity;
import apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.mapper.CategoriaMapper;
import apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.repository.CategoriaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CategoriaJpaAdapter implements CategoriaGateway {
    private final CategoriaRepository repository;
    private final CategoriaMapper mapper;

    public CategoriaJpaAdapter(CategoriaRepository repository, CategoriaMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Categoria> buscarPorId(Long id) {
        return repository.findById(id)
                .map(mapper::entityToCategoria);
    }

    @Override
    public Categoria salvar(Categoria categoria) {
        CategoriaEntity entity = mapper.categoriaToEntity(categoria);
        CategoriaEntity savedEntity = repository.save(entity);
        return mapper.entityToCategoria(savedEntity);
    }

    @Override
    public boolean existePorNome(String nome) {
        return repository.existsByNome(nome);
    }

    @Override
    public Page<Categoria> listarTodas(Pageable paginacao) {
        return repository.findAll(paginacao)
                .map(mapper::entityToCategoria);
    }

    @Override
    public Categoria atualizar(Categoria categoria) {
        CategoriaEntity entity = mapper.categoriaToEntity(categoria);
        CategoriaEntity updatedEntity = repository.save(entity);
        return mapper.entityToCategoria(updatedEntity);
    }

    @Override
    public void deletar(Categoria categoria) {
        CategoriaEntity entity = mapper.categoriaToEntity(categoria);
        repository.delete(entity);
    }
}
