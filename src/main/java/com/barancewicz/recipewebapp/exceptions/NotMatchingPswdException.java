package com.barancewicz.recipewebapp.exceptions;

public class NotMatchingPswdException extends RuntimeException {
    public NotMatchingPswdException() {
    }

    public NotMatchingPswdException(String message) {
        super(message);
    }

    public NotMatchingPswdException(String message, Throwable cause) {
        super(message, cause);
    }
}
