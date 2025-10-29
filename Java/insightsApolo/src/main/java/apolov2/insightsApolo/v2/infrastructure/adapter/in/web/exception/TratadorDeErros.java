package apolov2.insightsApolo.v2.infrastructure.adapter.in.web.exception;

import apolov2.insightsApolo.v2.core.application.exception.RequestError;
import apolov2.insightsApolo.v2.infrastructure.adapter.in.web.dto.MsgErro;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class TratadorDeErros {
    private static final Logger logger = LoggerFactory.getLogger(TratadorDeErros.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException ex) {
        logger.error("Erro de validação: {}", ex.getMessage());
        var erros = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<MsgErro> tratarErro400(HttpMessageNotReadableException ex) {
        logger.error("Erro de leitura da mensagem: {}", ex.getMessage());
        MsgErro msgErro = new MsgErro("body", "Formato de dados inválido");
        return ResponseEntity.badRequest().body(msgErro);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<MsgErro> tratarErroBadCredentials() {
        MsgErro msgErro = new MsgErro("credenciais", "Credenciais inválidas");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(msgErro);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<MsgErro> tratarErroAuthentication() {
        MsgErro msgErro = new MsgErro("autenticacao", "Falha na autenticação");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(msgErro);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<MsgErro> handleEntityNotFound(EntityNotFoundException ex) {
        MsgErro msgErro = new MsgErro("entidade", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msgErro);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<MsgErro> tratarUsuarioNaoEncontrado(UsernameNotFoundException ex) {
        MsgErro msgErro = new MsgErro("usuario", "Usuário não encontrado");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msgErro);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MsgErro> tratarErro500(Exception ex) {
        MsgErro msgErro = new MsgErro("erro", "Erro: " + ex.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(msgErro);
    }

    @ExceptionHandler(RequestError.class)
    public ResponseEntity tratarErroRequest(RequestError ex) {
        MsgErro msgErro = new MsgErro(ex.getCampo(), ex.getMensagem());
        return ResponseEntity.status(ex.getStatus()).body(msgErro);
    }

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

    private record DadosErroValidacao(String campo, String mensagem) {
        public DadosErroValidacao(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
        }
    }
}
