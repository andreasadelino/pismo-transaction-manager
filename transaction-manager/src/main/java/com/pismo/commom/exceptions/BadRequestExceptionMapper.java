package com.pismo.commom.exceptions;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class BadRequestExceptionMapper implements ExceptionMapper<RequestValidationException> {

    @Override
    public Response toResponse(RequestValidationException e) {
        var message = !e.getMessage().isBlank() ?
                e.getMessage() :
                "Invalid request.";

        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(message)
                .build();
    }
}
