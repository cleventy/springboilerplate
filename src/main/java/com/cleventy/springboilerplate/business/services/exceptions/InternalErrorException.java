package com.cleventy.springboilerplate.business.services.exceptions;

public class InternalErrorException extends Exception {

	private static final long serialVersionUID = -6341323842099400893L;
	
	public InternalErrorException(Object key, String className) {
        super("Instance not found " + key + " for " + className);
    }
    public InternalErrorException() {
        super();
    }
    public InternalErrorException(Throwable t) {
        super(t);
    }
}

