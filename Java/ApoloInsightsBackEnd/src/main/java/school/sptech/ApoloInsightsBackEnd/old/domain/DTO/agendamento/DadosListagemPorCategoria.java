package school.sptech.ApoloInsightsBackEnd.old.domain.DTO.agendamento;

import school.sptech.ApoloInsightsBackEnd.old.domain.Status;
import java.time.LocalDate;
import java.time.LocalTime;

public record DadosListagemPorCategoria(
        Long idAgendamento,
        String cliente,
        String servico,
        LocalDate data,
        LocalTime hora,
        Status status,
        String formaDePagamento
) {
    public DadosListagemPorCategoria(Long idAgendamento, String cliente,String servico, LocalDate data, LocalTime hora, Status status, String formaDePagamento) {
        this.idAgendamento = idAgendamento;
        this.cliente = cliente;
        this.servico = servico;
        this.data = data;
        this.hora = hora;
        this.status = status;
        this.formaDePagamento = formaDePagamento;
    }
}
