package com.sar.ws.exceptions;

public class RestaurantServiceException extends RuntimeException {
    private static final long serialVersionUID = -7932568922924065582L;

    public RestaurantServiceException(String message) {
        super(message);
    }
}
