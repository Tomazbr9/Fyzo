package com.fyzo.app.exceptions;

public class UnauthorizedResourceAccessException extends RuntimeException  {
	
	private static final long serialVersionUID = 1L;

	public UnauthorizedResourceAccessException(String message) {
        super(message);
    }

}
