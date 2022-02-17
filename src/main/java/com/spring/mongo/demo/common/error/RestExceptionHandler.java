package com.spring.mongo.demo.common.error;

import com.spring.mongo.demo.common.exception.DocStoreDataAccessException;
import com.spring.mongo.demo.common.exception.DocStoreException;
import com.spring.mongo.demo.util.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler({ DocStoreException.class })
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorInfo> handleDocStoreException(DocStoreException ex) {
        ErrorInfo errorInfo = this.buildErrorInfo(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex);
        log.error(errorInfo.getMessage(), ex);
        ResponseEntity<ErrorInfo> responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorInfo);
        return responseEntity;
    }

    @ExceptionHandler({ DocStoreDataAccessException.class })
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorInfo> handleDocStoreDataAccessException(DocStoreDataAccessException ex) {
        ErrorInfo errorInfo = this.buildErrorInfo(HttpStatus.BAD_REQUEST.value(), ex);
        log.error(errorInfo.getMessage(), ex);
        ResponseEntity<ErrorInfo> responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorInfo);
        return responseEntity;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
        HttpHeaders headers, HttpStatus status, WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        ResponseEntity<Object> responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errors);
        return responseEntity;
    }

    private ErrorInfo buildErrorInfo(int httpStatusCode, DocStoreException ex) {
        ErrorInfo errorInfo = new ErrorInfo(httpStatusCode, ex.getErrorCategory());
        errorInfo.setErrorCode(ex.getErrorCode());
        String message = messageSource.getMessage(ex.getMessageCode(), ex.getArguments(), Locale.ENGLISH);
        errorInfo.setMessage(message);
        return errorInfo;
    }
}
