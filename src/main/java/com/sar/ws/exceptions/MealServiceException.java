package com.sar.ws.exceptions;

public class MealServiceException extends RuntimeException {
    private static final long serialVersionUID = 6524440028254053905L;

    public MealServiceException(String message) {
        super(message);
    }
}
