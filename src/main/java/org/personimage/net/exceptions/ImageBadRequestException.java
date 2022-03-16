package org.personimage.net.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ImageBadRequestException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 43876691117560211L;
    private static final String MESSAGE = "Bad Request ContentType Only JPG";

    public ImageBadRequestException(String data) {
	super(MESSAGE + ". " + data);
    }
}