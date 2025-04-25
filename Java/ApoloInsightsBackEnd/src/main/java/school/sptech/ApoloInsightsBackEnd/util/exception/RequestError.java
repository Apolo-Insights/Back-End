package school.sptech.ApoloInsightsBackEnd.util.exception;

public class RequestError extends RuntimeException {
  private String campo;
  private String mensagem;

  public RequestError(String campo, String mensagem) {
    this.campo = campo;
    this.mensagem = mensagem;
  }

  public String getCampo() {
    return campo;
  }

  public String getMensagem() {
    return mensagem;
  }
}
