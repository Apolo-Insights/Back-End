package school.sptech.ApoloInsightsBackEnd.old.domain.DTO.agendamento;

import school.sptech.ApoloInsightsBackEnd.old.domain.Agendamento;
import school.sptech.ApoloInsightsBackEnd.old.domain.Servico;
import school.sptech.ApoloInsightsBackEnd.old.domain.Usuario;

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
