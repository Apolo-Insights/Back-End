package apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.adapter;

import apolov2.insightsApolo.v2.core.domain.entity.Produto;
import apolov2.insightsApolo.v2.core.port.in.ProdutoGateway;
import apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.entity.ProdutoEntity;
import apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.mapper.ProdutoMapper;
import apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.repository.ProdutoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class ProdutoJpaAdapter implements ProdutoGateway {
    private final ProdutoRepository repository;
    private final ProdutoMapper mapper;

    public ProdutoJpaAdapter(ProdutoRepository repository, ProdutoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Produto cadastrar(Produto produto) {
        ProdutoEntity entity = mapper.produtoToEntity(produto);
        ProdutoEntity savedEntity = repository.save(entity);
        return mapper.entityToProduto(savedEntity);
    }

    @Override
    public Produto atualizar(Produto produto) {
        ProdutoEntity entity = mapper.produtoToEntity(produto);
        ProdutoEntity updatedEntity = repository.save(entity);
        return mapper.entityToProduto(updatedEntity);
    }

    @Override
    public Produto buscarPorId(Long id) {
        ProdutoEntity entity = repository.findById(id).orElse(null);
        if (entity == null) return null;
        return mapper.entityToProduto(entity);
    }

    @Override
    public void deletar(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Page<Produto> listar(Pageable paginacao) {
        Page<ProdutoEntity> entitiesPage = repository.findAll(paginacao);
        return entitiesPage.map(mapper::entityToProduto);
    }
}
