package com.example.listmaker.server.exception;

import com.example.listmaker.shared.ExceptionWrapper;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by david on 7/15/14.
 */
public class RestException extends WebApplicationException {

    private final ExceptionWrapper wrapped;

    public RestException(Exception e) {
        this.wrapped = new ExceptionWrapper(e);
    }

    @Override
    public Response getResponse() {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(wrapped.getMsg()).type(MediaType.TEXT_PLAIN)
                .build();
    }

}
