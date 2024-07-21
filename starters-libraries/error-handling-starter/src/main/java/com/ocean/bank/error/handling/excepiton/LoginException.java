package com.ocean.bank.error.handling.excepiton;

public class LoginException extends RuntimeException {
    public LoginException(String message) {
        super(message);
    }
}
