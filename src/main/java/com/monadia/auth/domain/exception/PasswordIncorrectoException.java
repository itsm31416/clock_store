package com.monadia.auth.domain.exception;

public class PasswordIncorrectoException extends RuntimeException {
    public PasswordIncorrectoException(String message) {
        super(message);
    }
}
