package dev.jmvg.codenation.errorflow.api.exceptions;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@ControllerAdvice
public class ErrorflowExceptionHandler extends ResponseEntityExceptionHandler {

    public MessageSource messageSource(){
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("ISO-8859-1");
        messageSource.setDefaultLocale(Locale.getDefault());
        return messageSource;
    }

    @Bean
    public LocalValidatorFactoryBean validatorFactoryBean(){
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {

        String userMessage = messageSource().getMessage("invalid.message", null, LocaleContextHolder.getLocale());
        String developerMessage = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
        List<Erro> errors = Arrays.asList(new Erro(userMessage, developerMessage));
        return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request );
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        List<Erro> errors = errorList(ex.getBindingResult());
        return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ EmptyResultDataAccessException.class })
    public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex,
                                                                       WebRequest request){
        String userMessage = messageSource().getMessage("resource.not.found", null, LocaleContextHolder.getLocale());
        String developerMessage = ex.toString();
        List<Erro> errors = Arrays.asList(new Erro(userMessage, developerMessage));
        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({ DataIntegrityViolationException.class })
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex,
                                                                       WebRequest request){
        String userMessage = messageSource().getMessage("operation.not.allowed", null, LocaleContextHolder.getLocale());
        String developerMessage = ex.toString();
        List<Erro> errors = Arrays.asList(new Erro(userMessage, developerMessage));
        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    private List<Erro> errorList(BindingResult bindingResult){
        List<Erro> errors = new ArrayList<>();

        for(FieldError fieldError : bindingResult.getFieldErrors()) {
            String userMessage = messageSource().getMessage(fieldError, LocaleContextHolder.getLocale());
            String developerMessage = fieldError.toString();
            errors.add(new Erro(userMessage, developerMessage));
        }
        return errors;
    }
}
