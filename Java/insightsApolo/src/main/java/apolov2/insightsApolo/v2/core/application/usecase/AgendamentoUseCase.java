package apolov2.insightsApolo.v2.core.application.usecase;

import apolov2.insightsApolo.v2.core.application.command.AgendarCommand;
import apolov2.insightsApolo.v2.core.application.command.AgendarMultiploCommand;
import apolov2.insightsApolo.v2.core.domain.entity.Agendamento;
import apolov2.insightsApolo.v2.core.domain.entity.Servico;
import apolov2.insightsApolo.v2.core.domain.entity.Usuario;
import apolov2.insightsApolo.v2.core.port.in.AgendamentoGateway;
import apolov2.insightsApolo.v2.core.port.in.ServicoGateway;
import apolov2.insightsApolo.v2.core.port.in.UsuarioGateway;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AgendamentoUseCase {
    private final AgendamentoGateway agendamentoGateway;
    private final UsuarioGateway usuarioGateway;
    private final ServicoGateway servicoGateway;

    public AgendamentoUseCase(AgendamentoGateway agendamentoGateway, UsuarioGateway usuarioGateway, ServicoGateway servicoGateway) {
        this.agendamentoGateway = agendamentoGateway;
        this.usuarioGateway = usuarioGateway;
        this.servicoGateway = servicoGateway;
    }

    public List<Agendamento> agendarMultiplo(AgendarMultiploCommand command) {
        Usuario usuario = usuarioGateway.buscarPorId(command.idUsuario())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        List<Servico> servicos = servicoGateway.buscarPorIds(command.idsServicos());
        if (servicos.isEmpty()) {
            throw new IllegalArgumentException("Nenhum serviço encontrado");
        }

        List<Agendamento> agendamentos = servicos.stream()
                .map(servico -> new Agendamento(usuario, servico, command.data(), command.hora(), command.formaPagamento()))
                .toList();

        return agendamentoGateway.salvarTodos(agendamentos);
    }

    public Agendamento agendar(AgendarCommand command) {
        Usuario usuario = usuarioGateway.buscarPorId(command.idUsuario())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        Servico servico = servicoGateway.buscarPorId(command.idServico());
        if (servico == null) {
            throw new IllegalArgumentException("Serviço não encontrado");
        }

        Agendamento agendamento = new Agendamento(usuario, servico, command.data(), command.hora());

        return agendamentoGateway.salvar(agendamento);
    }

    public List<Agendamento> listarHistoricoServicos(Long idUsuario) {
        List<Agendamento> agendamentos = agendamentoGateway.buscarPorUsuarioId(idUsuario);
        if (agendamentos.isEmpty()) {
            throw new IllegalArgumentException("Nenhum agendamento de serviço encontrado");
        }
        return agendamentos;
    }

    public List<Agendamento> listarAgendamentosPorCategoria(Long idCategoria, Integer mes, Integer ano) {
        LocalDate inicio = LocalDate.of(ano, mes, 1);
        LocalDate fim = inicio.withDayOfMonth(inicio.lengthOfMonth());
        return agendamentoGateway.buscarPorCategoriaEPeriodo(idCategoria, inicio, fim);
    }
}
