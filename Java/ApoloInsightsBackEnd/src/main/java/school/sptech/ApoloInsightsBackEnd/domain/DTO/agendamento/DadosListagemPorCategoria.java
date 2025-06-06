package school.sptech.ApoloInsightsBackEnd.domain.DTO.agendamento;

import school.sptech.ApoloInsightsBackEnd.domain.Status;
import java.time.LocalDate;
import java.time.LocalTime;

public record DadosListagemPorCategoria(
        String cliente,
        String servico,
        LocalDate data,
        LocalTime hora,
        Status status,
        String formaDePagamento
) {
    public DadosListagemPorCategoria(String cliente,String servico, LocalDate data, LocalTime hora, Status status, String formaDePagamento) {
        this.cliente = cliente;
        this.servico = servico;
        this.data = data;
        this.hora = hora;
        this.status = status;
        this.formaDePagamento = formaDePagamento;
    }
}
