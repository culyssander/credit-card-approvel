package br.com.santander.userservice.controller.exception;

import br.com.santander.userservice.exception.UserAlreadyExistsException;
import br.com.santander.userservice.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Autowired
    private MessageSource messageSource;


//    @ExceptionHandler(NegocioExcecao.class)
//    public ResponseEntity<Object> handlerNegocioException(NegocioExcecao ex, WebRequest request) {
//        HttpStatus status = HttpStatus.SERVICE_UNAVAILABLE;
//
//        Problema problema = getProblema(status.value(), ex.getMessage(), null);
//
//        return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
//    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> handlerNegocioException(UserAlreadyExistsException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        Problem problem = getProblem(status.value(), ex.getMessage(), null);

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handlerNegocioException(UserNotFoundException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        Problem problem = getProblem(status.value(), ex.getMessage(), null);

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

//    @ExceptionHandler(UploadImagemExcecao.class)
//    public ResponseEntity<Object> handlerObjectNotFoundException(UploadImagemExcecao ex, WebRequest request) {
//        HttpStatus status = HttpStatus.BAD_REQUEST;
//
//        Problema problema = getProblema(status.value(), ex.getMessage(), null);
//
//        return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
//    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<Fields> camposDeErros = new ArrayList<Fields>();

        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            String nome = ((FieldError) error).getField();
            String mensagem = messageSource.getMessage(error, LocaleContextHolder.getLocale());

            camposDeErros.add(new Fields(nome, mensagem));
        }

        Problem problem = getProblem(
                status.value(),
                        "One or more fields are invalid. Fill in correctly and try again",
                camposDeErros);

        return super.handleExceptionInternal(ex, problem, headers, status, request);
    }

    private Problem getProblem(Integer status, String titulo, List<Fields> fields) {
        return new Problem(status, OffsetDateTime.now(), titulo, fields);
    }
}
