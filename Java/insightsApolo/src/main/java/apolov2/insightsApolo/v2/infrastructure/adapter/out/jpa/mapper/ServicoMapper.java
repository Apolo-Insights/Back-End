package apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.mapper;

import apolov2.insightsApolo.v2.core.application.command.CadastrarServicoCommand;
import apolov2.insightsApolo.v2.core.domain.entity.Servico;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.DadosCadastroServico;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.DadosDetalhamentoServico;
import apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.entity.ServicoEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ServicoMapper {
    CadastrarServicoCommand cadastroToCommand(DadosCadastroServico dados);
    DadosDetalhamentoServico servicoToDetalhamento(Servico servico);
    ServicoEntity servicoToEntity(Servico servico);
    Servico entityToServico(ServicoEntity entity);
}


