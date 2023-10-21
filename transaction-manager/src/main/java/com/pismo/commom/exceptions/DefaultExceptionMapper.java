package com.pismo.commom.exceptions;

import io.quarkus.logging.Log;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class DefaultExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception e) {
        Log.error("Error when processing", e);
        return Response
                .serverError()
                .entity("Internal server error.")
                .build();
    }
}
