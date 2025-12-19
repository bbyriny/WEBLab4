package org.bbyriny.exceptions;

import jakarta.ws.rs.core.Response;

public class UserAlreadyExistsException extends ApplicationException {
    public UserAlreadyExistsException(Response.Status status, String message) {

        super(status, message);
    }
}
