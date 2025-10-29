package apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.mapper;

import apolov2.insightsApolo.v2.core.application.command.CadastrarServicoCommand;
import apolov2.insightsApolo.v2.core.domain.entity.Categoria;
import apolov2.insightsApolo.v2.core.domain.entity.Servico;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.DadosCadastroServico;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.DadosDetalhamentoServico;
import apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.entity.CategoriaEntity;
import apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.entity.ServicoEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ServicoMapper {
    CadastrarServicoCommand cadastroToCommand(DadosCadastroServico dados);
    DadosDetalhamentoServico servicoToDetalhamento(Servico servico);
    ServicoEntity servicoToEntity(Servico servico);
    Servico entityToServico(ServicoEntity entity);
    
    // Mapeamento de Categoria para CategoriaEntity
    default Categoria map(CategoriaEntity categoriaEntity) {
        if (categoriaEntity == null) {
            return null;
        }
        return new Categoria(
            categoriaEntity.getId(),
            categoriaEntity.getNome(),
            categoriaEntity.getFoto()
        );
    }
    
    // Mapeamento de CategoriaEntity para Categoria
    default CategoriaEntity map(Categoria categoria) {
        if (categoria == null) {
            return null;
        }
        CategoriaEntity entity = new CategoriaEntity();
        entity.setId(categoria.getId());
        entity.setNome(categoria.getNome());
        entity.setFoto(categoria.getFoto());
        return entity;
    }
}



