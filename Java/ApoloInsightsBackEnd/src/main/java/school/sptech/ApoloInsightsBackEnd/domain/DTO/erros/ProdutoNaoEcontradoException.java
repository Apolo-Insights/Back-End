package school.sptech.ApoloInsightsBackEnd.domain.DTO.erros;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProdutoNaoEcontradoException extends RuntimeException {
    public ProdutoNaoEcontradoException(String message) {
        super(message);
    }
}
