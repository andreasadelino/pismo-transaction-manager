package com.pismo.commom.exceptions;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class RequestValidationExceptionMapper implements ExceptionMapper<RequestValidationException> {

    @Override
    public Response toResponse(RequestValidationException e) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(e.getMessage())
                .build();
    }
}
