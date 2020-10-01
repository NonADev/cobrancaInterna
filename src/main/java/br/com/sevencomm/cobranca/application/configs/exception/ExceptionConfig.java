package br.com.sevencomm.cobranca.application.configs.exception;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionConfig /**extends ResponseEntityExceptionHandler***/ {

//    @ExceptionHandler({
//            EmptyResultDataAccessException.class
//    })
//    public ResponseEntity<Object> errorNotFound(Exception ex) {
//        return ResponseEntity.notFound().build();
//    }
//
//    @ExceptionHandler({
//            IllegalArgumentException.class
//    })
//    public ResponseEntity<Object> errorIllegalArgument(Exception ex) {
//        return ResponseEntity.badRequest().body(ex.getMessage());
//    }

    // ! Tratado no exception.AccessDeniedHandler
//    @ExceptionHandler({
//            AccessDeniedException.class
//    })
//    public ResponseEntity errorAcessDenied(Exception ex) {
//        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Error("Access Forbidden"));
//    }

//    @Override
//    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        return new ResponseEntity<>(new Error("Http Method Not Allowed"), HttpStatus.METHOD_NOT_ALLOWED);
//    }
//
//    private class Error {
//        public String error;
//
//        public Error(String error) {
//            this.error = error;
//        }
//    }
}