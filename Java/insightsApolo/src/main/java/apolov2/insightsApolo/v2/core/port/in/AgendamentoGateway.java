package apolov2.insightsApolo.v2.core.port.in;

import apolov2.insightsApolo.v2.core.domain.entity.Agendamento;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AgendamentoGateway {
    Agendamento salvar(Agendamento agendamento);
    List<Agendamento> salvarTodos(List<Agendamento> agendamentos);
    Optional<Agendamento> buscarPorId(Long id);
    Agendamento atualizar(Agendamento agendamento);
    List<Agendamento> buscarPorUsuarioId(Long usuarioId);
    List<Agendamento> buscarPorCategoriaEPeriodo(Long idCategoria, LocalDate inicio, LocalDate fim);
}
