package org.personimage.net.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PersonNotFoundException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 43876691117560211L;

    public PersonNotFoundException(Integer id) {
	super(String.format("Not exist Person with : %d", id));
    }
}