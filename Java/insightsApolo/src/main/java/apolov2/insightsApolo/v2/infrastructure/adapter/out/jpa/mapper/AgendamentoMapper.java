package apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.mapper;

import apolov2.insightsApolo.v2.core.application.command.AgendarCommand;
import apolov2.insightsApolo.v2.core.application.command.AgendarMultiploCommand;
import apolov2.insightsApolo.v2.core.domain.entity.Agendamento;
import apolov2.insightsApolo.v2.core.domain.entity.Servico;
import apolov2.insightsApolo.v2.core.domain.entity.Usuario;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.DadosCadastroAgendamento;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.DadosCadastroAgendamentoAdmin;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.DadosCadastroAgendamentoMultiplo;
import apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.entity.AgendamentoEntity;
import apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.entity.ServicoEntity;
import apolov2.insightsApolo.v2.infrastructure.adapter.out.jpa.entity.UsuarioEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UsuarioMapper.class, ServicoMapper.class})
public interface AgendamentoMapper {
    AgendarCommand cadastroToCommand(DadosCadastroAgendamento dados);
    AgendarMultiploCommand cadastroToCommand(DadosCadastroAgendamentoMultiplo dados);
    AgendarCommand cadastroAdminToCommand(DadosCadastroAgendamentoAdmin dados);
    Agendamento entityToAgendamento(AgendamentoEntity entity);
    AgendamentoEntity agendamentoToEntity(Agendamento agendamento);
}
