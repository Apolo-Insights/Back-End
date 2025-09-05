package school.sptech.ApoloInsightsBackEnd.v2.infrastructure.adapter.out.jpa.mapper;

import org.mapstruct.*;
import school.sptech.ApoloInsightsBackEnd.v2.core.application.command.CadastrarServicoCommand;
import school.sptech.ApoloInsightsBackEnd.v2.core.domain.entity.Servico;
import school.sptech.ApoloInsightsBackEnd.v2.infrastructure.adapter.in.web.dto.DadosCadastroServico;
import school.sptech.ApoloInsightsBackEnd.v2.infrastructure.adapter.in.web.dto.DadosDetalhamentoServico;
import school.sptech.ApoloInsightsBackEnd.v2.infrastructure.adapter.out.jpa.entity.ServicoEntity;

@Mapper(componentModel = "spring")
public interface ServicoMapper {
    CadastrarServicoCommand cadastroToCommand(DadosCadastroServico dados);
    DadosDetalhamentoServico servicoToDetalhamento(Servico servico);
    ServicoEntity servicoToEntity(Servico servico);
    Servico entityToServico(ServicoEntity entity);
}


