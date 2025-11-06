package apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.mapper;

import apolov2.insightsApolo.v2.core.domain.entity.BloqueioSemanal;
import apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.entity.BloqueioSemanalEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CategoriaMapper.class})
public interface BloqueioSemanalMapper {
    BloqueioSemanal toDomain(BloqueioSemanalEntity entity);
    BloqueioSemanalEntity toEntity(BloqueioSemanal domain);
}
