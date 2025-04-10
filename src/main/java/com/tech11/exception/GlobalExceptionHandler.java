package com.tech11.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {
    	 ErrorResponse errorResponse = new ErrorResponse(getErrorMessage(exception));

         return Response.status(getHttpStatus(exception))
                        .entity(errorResponse)
                        .build();
    }

    private String getErrorMessage(Throwable exception) {
       
        return switch (exception.getClass().getSimpleName()) {
            case "UserException" -> exception.getMessage();
            case "NoResultException" -> "No user found for the given criteria.";
            case "IllegalArgumentException" -> "Invalid input provided.";
            default -> "An unexpected error occurred.";
        };
    }

    private Response.Status getHttpStatus(Throwable exception) {
        return switch (exception.getClass().getSimpleName()) {
            case "UserException" -> Response.Status.NOT_FOUND; 
            case "IllegalArgumentException" -> Response.Status.BAD_REQUEST;
            case "NoResultException" -> Response.Status.NOT_FOUND;
            default -> Response.Status.INTERNAL_SERVER_ERROR;
        };
    }

}
