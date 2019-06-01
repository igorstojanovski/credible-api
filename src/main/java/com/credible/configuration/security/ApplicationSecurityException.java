package com.credible.configuration.security;

public class ApplicationSecurityException extends RuntimeException {
    public ApplicationSecurityException(String message, Exception e) {
        super(message, e);
    }

    public ApplicationSecurityException(String message) {
        super(message);
    }
}
