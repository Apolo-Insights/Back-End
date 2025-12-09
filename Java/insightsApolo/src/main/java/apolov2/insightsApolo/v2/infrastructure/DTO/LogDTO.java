package apolov2.insightsApolo.v2.infrastructure.DTO;


import java.time.LocalDateTime;

public class LogDTO {
    private String acao;
    private String usuario;
    private String descricao;
    private LocalDateTime dataHora;

    public LogDTO() {}

    public LogDTO(String acao, String usuario, String descricao) {
        this.acao = acao;
        this.usuario = usuario;
        this.descricao = descricao;
        this.dataHora = LocalDateTime.now();
    }

    public String getAcao() { return acao; }
    public void setAcao(String acao) { this.acao = acao; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }

    @Override
    public String toString() {
        return "LogDTO{" +
                "acao='" + acao + '\'' +
                ", usuario='" + usuario + '\'' +
                ", descricao='" + descricao + '\'' +
                ", dataHora=" + dataHora +
                '}';
    }
}