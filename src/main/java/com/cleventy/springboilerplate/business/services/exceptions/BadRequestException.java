package com.cleventy.springboilerplate.business.services.exceptions;

public class BadRequestException extends RuntimeException {

	private static final long serialVersionUID = -9144958997537219376L;
	
	public BadRequestException(Object key, String message) {
        super("Bad request for " + key + ": " + message);
    }
    public BadRequestException(String message) {
        super(message);
    }
    public BadRequestException() {
        super();
    }
}

