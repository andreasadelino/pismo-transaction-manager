package com.pismo.commom.exceptions;

import jakarta.ws.rs.BadRequestException;

public class RequestValidationException extends BadRequestException {
    public RequestValidationException(String message) {
        super(message);
    }
}
