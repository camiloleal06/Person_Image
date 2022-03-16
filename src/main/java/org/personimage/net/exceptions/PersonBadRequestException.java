package org.personimage.net.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PersonBadRequestException extends RuntimeException {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE = "Bad Request";

    public PersonBadRequestException(String data) {
	super(MESSAGE + ". " + data);
    }

}