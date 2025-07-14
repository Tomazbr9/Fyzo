package com.tomaz.finance.exceptions;

public class ResourceNotFoundException extends ApplicationException {
	
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String message) {
        super(message);
    }


}
