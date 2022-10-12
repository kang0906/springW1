package com.example.springw1crud.exception;

public class CustomUserSignupException extends RuntimeException{
    public CustomUserSignupException() {
        super();
    }
    public CustomUserSignupException(String message, Throwable cause) {
        super(message, cause);
    }
    public CustomUserSignupException(String message) {
        super(message);
    }
    public CustomUserSignupException(Throwable cause) {
        super(cause);
    }
}
