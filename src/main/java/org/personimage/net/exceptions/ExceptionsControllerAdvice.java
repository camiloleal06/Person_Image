package org.personimage.net.exceptions;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionsControllerAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({ PersonNotFoundException.class })
    @ResponseBody
    public ExceptionMessage notFoundRequest(RuntimeException exception) {
	return new ExceptionMessage(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ PersonBadRequestException.class, DuplicateKeyException.class })
    @ResponseBody
    public ExceptionMessage badRequest(RuntimeException exception) {
	return new ExceptionMessage(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), exception.getMessage());

    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ExceptionHandler({ PersonNotContentException.class })
    @ResponseBody
    public ExceptionMessage notContentRequest(RuntimeException exception) {
	return new ExceptionMessage(HttpStatus.NO_CONTENT, HttpStatus.NO_CONTENT.value(), exception.getMessage());

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public String handleHttpMediaTypeNotAcceptableException() {
	return "acceptable MIME type:" + MediaType.IMAGE_JPEG_VALUE;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ RuntimeException.class })
    @ResponseBody
    public ExceptionMessage badRequestException(RuntimeException exception) {
	return new ExceptionMessage(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), exception.getMessage());

    }

}