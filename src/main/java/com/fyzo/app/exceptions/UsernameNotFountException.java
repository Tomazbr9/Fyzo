package com.fyzo.app.exceptions;

public class UsernameNotFountException extends RuntimeException {
	
    private static final long serialVersionUID = 1L;

	public UsernameNotFountException(String message) {
        super(message);
    }
}

