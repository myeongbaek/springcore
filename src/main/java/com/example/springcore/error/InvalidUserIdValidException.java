package com.example.springcore.error;

public class InvalidUserIdValidException extends IllegalArgumentException {
    public InvalidUserIdValidException(String message) {
        super(message);
    }
}
