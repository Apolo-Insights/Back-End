package apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.mapper;

import apolov2.insightsApolo.v2.core.domain.entity.Produto;
import apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.entity.ProdutoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {
    Produto entityToProduto(ProdutoEntity entity);
    ProdutoEntity produtoToEntity(Produto produto);
}
