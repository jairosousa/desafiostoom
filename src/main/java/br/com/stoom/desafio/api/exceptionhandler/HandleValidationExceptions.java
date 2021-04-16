package br.com.stoom.desafio.api.exceptionhandler;

import br.com.stoom.desafio.api.model.exception.EntidadeNaoEncontradaException;
import br.com.stoom.desafio.api.model.exception.NegocioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
public class HandleValidationExceptions extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<Object> handlerEntidadeNaoNegocio(EntidadeNaoEncontradaException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        ErrorResponse errors = getErrorResponse(ex,status);

        return super.handleExceptionInternal(ex, errors, new HttpHeaders(), status, request);

    }


    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<Object> handlerNegocio(NegocioException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ErrorResponse errors = getErrorResponse(ex, status);

        return super.handleExceptionInternal(ex, errors, new HttpHeaders(), status, request);

    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<ErrorResponse.Campo> campos = new ArrayList<ErrorResponse.Campo>();

        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            String name = ((FieldError) error).getField();
            String message = messageSource.getMessage(error, LocaleContextHolder.getLocale());

            campos.add(new ErrorResponse.Campo(name, message));
        }

        ErrorResponse errors = new ErrorResponse();
        errors.setStatus(status.value());
        errors.setTitulo("Um ou mais campos inválidos");
        errors.setDataHora(OffsetDateTime.now());
        errors.setCampos(campos);

        return super.handleExceptionInternal(ex, errors, headers, status, request);

    }

    private ErrorResponse getErrorResponse(NegocioException ex, HttpStatus status) {

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(status.value());
        errorResponse.setTitulo("Ocorreu inconsistencia na entrada de dados.");
        errorResponse.setDataHora(OffsetDateTime.now());
        errorResponse.setCampos(Arrays.asList(new ErrorResponse.Campo(null, ex.getMessage())));

        return errorResponse;
    }
}
