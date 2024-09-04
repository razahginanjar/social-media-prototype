package com.RazahDev.SocialMedia.Controllers.ErrorHandling;

import com.RazahDev.SocialMedia.DTO.Response.CommonResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import javax.naming.AuthenticationException;

@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler({ResponseStatusException.class})
    public ResponseEntity<CommonResponse<?>> responseResponseEntity(ResponseStatusException exception)
    {
        return ResponseEntity.status(exception.getStatusCode())
                .body(
                        CommonResponse.builder()
                                .message(exception.getMessage())
                                .statusCode(exception.getStatusCode().value())
                                .build()
                );
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<CommonResponse<?>> commonResponseResponseEntity(ConstraintViolationException exception)
    {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        CommonResponse.builder()
                                .message(exception.getMessage())
                                .statusCode(HttpStatus.BAD_REQUEST.value())
                                .build()
                );
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public final ResponseEntity<CommonResponse<?>> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, WebRequest request) {
        return ResponseEntity.status(ex.getStatusCode())
                .body(CommonResponse.builder()
                        .message(ex.getMessage())
                        .statusCode(ex.getStatusCode().value())
                        .build()
                );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public final ResponseEntity<CommonResponse<?>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(CommonResponse.builder()
                        .message(ex.getMessage())
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .build());
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public final ResponseEntity<CommonResponse<?>> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex, WebRequest request) {
        return ResponseEntity.status(ex.getStatusCode())
                .body(CommonResponse.builder()
                        .message(ex.getMessage())
                        .statusCode(ex.getStatusCode().value())
                        .build());
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<CommonResponse<?>> handleAuthException(AuthenticationException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(CommonResponse.builder()
                        .message(ex.getMessage())
                        .statusCode(HttpStatus.UNAUTHORIZED.value())
                        .build());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<CommonResponse<?>> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value())
                .body(CommonResponse.builder()
                        .message(ex.getMessage())
                        .statusCode(HttpStatus.UNAUTHORIZED.value())
                        .build());
    }
}
