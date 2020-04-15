package com.cleventy.springboilerplate.business.services.exceptions;

public class DuplicateInstanceException extends Exception {

	private static final long serialVersionUID = -4689199922098201578L;
	
	public DuplicateInstanceException(Object key, String className) {
        super("Duplicate instance " + key + " for " + className);
    }
    public DuplicateInstanceException() {
        super();
    }
}

