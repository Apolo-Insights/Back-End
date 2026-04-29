package apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.mapper;

import apolov2.insightsApolo.v2.core.domain.entity.BloqueioEspecifico;
import apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.entity.BloqueioEspecificoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CategoriaMapper.class})
public interface BloqueioEspecificoMapper {
    BloqueioEspecifico toDomain(BloqueioEspecificoEntity entity);
    BloqueioEspecificoEntity toEntity(BloqueioEspecifico domain);
}
