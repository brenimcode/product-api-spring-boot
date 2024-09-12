package com.breno.springboot.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionsHandler extends ResponseEntityExceptionHandler{
    
    // Tratamento para exceções genéricas
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(Exception ex, WebRequest request) {
        String errorMessage = "An unexpected error occurred";
        return handleExceptionInternal(ex, errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    // Tratar exceção de GERAR Tokens
    @ExceptionHandler(TokenGenerationException.class)
    public ResponseEntity<String> handleTokenGenerationException(TokenGenerationException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Token generation error: " + ex.getMessage());
    }

    // Tratar exceção de GERAR Tokens
    @ExceptionHandler(TokenValidationException.class)
    public ResponseEntity<String> handleTokenValidationException(TokenValidationException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token validation error: " + ex.getMessage());
    }

    //Retornar resposta correta para erros de forbidden
    // Tratar exceção de acesso proibido (403 Forbidden)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex) {
        // Personaliza a mensagem de erro para casos de acesso negado (403 Forbidden)
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied: You don't have permission to access this resource.");
    }

}
