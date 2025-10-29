package apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto;

import apolov2.insightsApolo.v2.core.domain.entity.Agendamento;
import apolov2.insightsApolo.v2.core.domain.entity.Servico;
import apolov2.insightsApolo.v2.core.domain.entity.Usuario;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record DadosDetalhamentoAgendamentoMultiplo(
        Usuario usuario,
        List<Servico> servicos,
        LocalDate data,
        LocalTime hora,
        String formaPagamento
) {
    public DadosDetalhamentoAgendamentoMultiplo(List<Agendamento> agendamentos) {
        this(
                agendamentos.get(0).getUsuario(),
                agendamentos.stream().map(Agendamento::getServico).toList(),
                agendamentos.get(0).getData(),
                agendamentos.get(0).getHora(),
                agendamentos.get(0).getFormaPagamento().toString()
        );
    }
}
