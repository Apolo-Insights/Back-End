package apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.mapper;

import apolov2.insightsApolo.v2.core.domain.entity.HorarioDisponivel;
import apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.entity.HorarioDisponivelEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CategoriaMapper.class})
public interface HorarioDisponivelMapper {
    HorarioDisponivel toDomain(HorarioDisponivelEntity entity);
    HorarioDisponivelEntity toEntity(HorarioDisponivel domain);
}
