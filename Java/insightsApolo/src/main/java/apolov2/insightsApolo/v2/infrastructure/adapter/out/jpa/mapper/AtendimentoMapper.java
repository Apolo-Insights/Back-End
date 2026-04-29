package apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.mapper;

import apolov2.insightsApolo.v2.core.domain.entity.Atendimento;
import apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.entity.AtendimentoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CategoriaMapper.class, UsuarioMapper.class})
public interface AtendimentoMapper {
    
    @Mapping(source = "categoria", target = "categoria")
    @Mapping(source = "cliente", target = "cliente")
    Atendimento entityToAtendimento(AtendimentoEntity entity);
    
    @Mapping(source = "categoria", target = "categoria")
    @Mapping(source = "cliente", target = "cliente")
    AtendimentoEntity atendimentoToEntity(Atendimento atendimento);
}
