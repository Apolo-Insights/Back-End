package school.sptech.ApoloInsightsBackEnd.exception;

import org.springframework.http.HttpStatus;

public class RequestError extends RuntimeException {
  private HttpStatus status;
  private String campo;
  private String mensagem;

  public RequestError(HttpStatus status, String campo, String mensagem) {
    this.campo = campo;
    this.mensagem = mensagem;
    this.status = status;
  }

  public String getCampo() {
    return campo;
  }

  public String getMensagem() {
    return mensagem;
  }

    public HttpStatus getStatus() {
        return status;
    }
}
