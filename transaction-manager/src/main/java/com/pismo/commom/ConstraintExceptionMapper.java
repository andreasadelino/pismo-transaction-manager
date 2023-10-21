package com.pismo.commom;

import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.List;
import java.util.stream.Collectors;

@Provider
public class ConstraintExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .type(MediaType.APPLICATION_JSON)
                .entity(prepareMessage(exception))
                .build();
    }

    private List<ConstraintError> prepareMessage(ConstraintViolationException exception) {
        return exception
                .getConstraintViolations()
                .stream()
                .map(cv -> new ConstraintError(cv.getMessage()))
                .collect(Collectors.toList());
    }
}
