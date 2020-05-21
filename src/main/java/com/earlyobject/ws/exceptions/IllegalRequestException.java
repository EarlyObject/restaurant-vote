package com.earlyobject.ws.exceptions;

public class IllegalRequestException extends RuntimeException {
    private static final long serialVersionUID = 7351939699401352516L;

    public IllegalRequestException(String message) {
        super(message);
    }
}
