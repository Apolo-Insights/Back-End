package school.sptech.ApoloInsightsBackEnd.domain.DTO.agendamento;

import school.sptech.ApoloInsightsBackEnd.domain.Agendamento;
import school.sptech.ApoloInsightsBackEnd.domain.Servico;
import school.sptech.ApoloInsightsBackEnd.domain.Usuario;

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
