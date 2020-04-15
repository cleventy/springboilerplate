package com.cleventy.springboilerplate.business.services.exceptions;

public class InstanceNotFoundException extends Exception {

	private static final long serialVersionUID = 3202777853762428562L;
	
	public InstanceNotFoundException(Object key, String className) {
        super("Instance not found " + key + " for " + className);
    }
    public InstanceNotFoundException() {
        super();
    }
	public InstanceNotFoundException(String message) {
        super(message);
    }
}

