package com.mvv.cards_service.application.exception;

public class DuplicateRegisterException extends RuntimeException {
    public DuplicateRegisterException(String message) {
        super(message);
    }
}
