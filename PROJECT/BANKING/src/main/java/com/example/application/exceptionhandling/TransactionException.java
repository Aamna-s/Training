package com.example.application.exceptionhandling;

public class TransactionException extends RuntimeException {
    public TransactionException(String message) {
        super(message);
    }
}

