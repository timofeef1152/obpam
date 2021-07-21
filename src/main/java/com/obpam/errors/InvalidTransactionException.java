package com.obpam.errors;

public class InvalidTransactionException extends RuntimeException {
    public InvalidTransactionException() {
    }

    public InvalidTransactionException(String message) {
        super(message);
    }
}
