package org.magnitude.stock.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class SystemFailureException extends Exception {

    private static final long serialVersionUID = 1L;

    Object object = null;
    public SystemFailureException(String message, Exception exception) {
        super(message, exception);
    }

    public SystemFailureException(String message) {
        super(message);
    }

}