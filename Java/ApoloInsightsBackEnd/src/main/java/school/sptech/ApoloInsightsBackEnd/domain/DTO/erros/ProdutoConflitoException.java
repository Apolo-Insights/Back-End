package school.sptech.ApoloInsightsBackEnd.domain.DTO.erros;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ProdutoConflitoException extends RuntimeException {
    public ProdutoConflitoException(String message) {
        super(message);
    }
}
