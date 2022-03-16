package org.personimage.net.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PersonNotContentException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 43876691117560211L;
    private static final String MESSAGE = "Not Content Person";

    public PersonNotContentException(String data) {
	super(MESSAGE + ". " + data);
    }
}