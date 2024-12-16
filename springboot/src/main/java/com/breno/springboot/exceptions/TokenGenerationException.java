package com.breno.springboot.exceptions;

public class TokenGenerationException extends RuntimeException {
    
    public TokenGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}