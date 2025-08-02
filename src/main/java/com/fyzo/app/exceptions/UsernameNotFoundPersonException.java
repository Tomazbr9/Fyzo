package com.fyzo.app.exceptions;

public class UsernameNotFoundPersonException extends RuntimeException {
	
    private static final long serialVersionUID = 1L;

	public UsernameNotFoundPersonException(String message) {
        super(message);
    }
}

