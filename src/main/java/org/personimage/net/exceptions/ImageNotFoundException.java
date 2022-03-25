package org.personimage.net.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ImageNotFoundException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 43876691117560211L;

    public ImageNotFoundException(String id) {
	super("Not exist Image with ID : " + id);
    }
}