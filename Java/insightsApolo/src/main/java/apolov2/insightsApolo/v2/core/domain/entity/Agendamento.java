package apolov2.insightsApolo.v2.core.domain.entity;

import apolov2.insightsApolo.v2.core.domain.util.FormaPagamento;
import apolov2.insightsApolo.v2.core.domain.util.Status;

import java.time.LocalDate;
import java.time.LocalTime;

public class Agendamento {
    private Long id;
    private Usuario usuario;
    private Servico servico;
    private LocalDate data;
    private LocalTime hora;
    private FormaPagamento formaPagamento;
    private Status status;

    public Agendamento() {
    }

    public Agendamento(Usuario usuario, Servico servico, LocalDate data, LocalTime hora, FormaPagamento formaPagamento) {
        this.usuario = usuario;
        this.servico = servico;
        this.data = data;
        this.hora = hora;
        this.formaPagamento = formaPagamento;
        this.status = Status.AGENDADO;
    }

    public Agendamento(Usuario usuario, Servico servico, LocalDate data, LocalTime hora) {
        this.usuario = usuario;
        this.servico = servico;
        this.data = data;
        this.hora = hora;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
