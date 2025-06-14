package school.sptech.ApoloInsightsBackEnd.domain.DTO.dashboard;

import java.util.List;
import java.util.Map;

public class DadosDashboard {

    private List<Map<String, Object>> atendimentosPorMes;
    private List<Map<String, Object>> distribuicaoHorarios;
    private List<Map<String, Object>> mediaServicosPorCliente;
    private List<Map<String, Object>> distribuicaoDiaHora;
    private List<Map<String, Object>> servicoDoMes;
    private List<Map<String, Object>> mediaAtendimentosPorSemana;
    private List<Map<String, Object>> mediaGeralPorSemana;
    private List<Map<String, Object>> top10Clientes;
    private List<Map<String, Object>> ocupacaoDias;

    // Getters e Setters

    public List<Map<String, Object>> getAtendimentosPorMes() {
        return atendimentosPorMes;
    }

    public void setAtendimentosPorMes(List<Map<String, Object>> atendimentosPorMes) {
        this.atendimentosPorMes = atendimentosPorMes;
    }

    public List<Map<String, Object>> getDistribuicaoHorarios() {
        return distribuicaoHorarios;
    }

    public void setDistribuicaoHorarios(List<Map<String, Object>> distribuicaoHorarios) {
        this.distribuicaoHorarios = distribuicaoHorarios;
    }

    public List<Map<String, Object>> getMediaServicosPorCliente() {
        return mediaServicosPorCliente;
    }

    public void setMediaServicosPorCliente(List<Map<String, Object>> mediaServicosPorCliente) {
        this.mediaServicosPorCliente = mediaServicosPorCliente;
    }

    public List<Map<String, Object>> getDistribuicaoDiaHora() {
        return distribuicaoDiaHora;
    }

    public void setDistribuicaoDiaHora(List<Map<String, Object>> distribuicaoDiaHora) {
        this.distribuicaoDiaHora = distribuicaoDiaHora;
    }

    public List<Map<String, Object>> getServicoDoMes() {
        return servicoDoMes;
    }

    public void setServicoDoMes(List<Map<String, Object>> servicoDoMes) {
        this.servicoDoMes = servicoDoMes;
    }

    public List<Map<String, Object>> getMediaAtendimentosPorSemana() {
        return mediaAtendimentosPorSemana;
    }

    public void setMediaAtendimentosPorSemana(List<Map<String, Object>> mediaAtendimentosPorSemana) {
        this.mediaAtendimentosPorSemana = mediaAtendimentosPorSemana;
    }

    public List<Map<String, Object>> getMediaGeralPorSemana() {
        return mediaGeralPorSemana;
    }

    public void setMediaGeralPorSemana(List<Map<String, Object>> mediaGeralPorSemana) {
        this.mediaGeralPorSemana = mediaGeralPorSemana;
    }

    public List<Map<String, Object>> getTop10Clientes() {
        return top10Clientes;
    }

    public void setTop10Clientes(List<Map<String, Object>> top10Clientes) {
        this.top10Clientes = top10Clientes;
    }

    public List<Map<String, Object>> getOcupacaoDias() {
        return ocupacaoDias;
    }

    public void setOcupacaoDias(List<Map<String, Object>> ocupacaoDias) {
        this.ocupacaoDias = ocupacaoDias;
    }
}
