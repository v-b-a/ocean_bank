package com.ocean.bank.error.handling.excepiton;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
