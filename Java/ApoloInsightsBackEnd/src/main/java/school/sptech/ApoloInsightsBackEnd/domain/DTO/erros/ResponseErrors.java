package school.sptech.ApoloInsightsBackEnd.domain.DTO.erros;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class ResponseErrors {

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<MsgErro> duplicidadeDeCamposSQL(SQLIntegrityConstraintViolationException e) {

        MsgErro msgErro = new MsgErro("erro", "Erro ao processar a requisição.");
        if (e.getMessage().contains("usuarios.email")) {
            msgErro = new MsgErro("email", "Email já registrado no sistema.");
            return ResponseEntity.badRequest().body(msgErro);
        }

        if (e.getMessage().contains("usuarios.cpf")) {
            msgErro = new MsgErro("cpf", "CPF já registrado no sistema.");
            return ResponseEntity.badRequest().body(msgErro);
        }

        return ResponseEntity.status(500).body(msgErro);
    }
}





