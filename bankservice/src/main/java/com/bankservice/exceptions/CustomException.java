package com.bankservice.exceptions;

public class CustomException extends RuntimeException{
    public CustomException (String message) {
        super(message);
    }
}
