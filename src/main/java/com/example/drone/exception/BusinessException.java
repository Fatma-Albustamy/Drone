package com.example.drone.exception;

public class BusinessException extends Exception {
    public BusinessException(String msg) {
        super(msg);
    }

    public BusinessException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
