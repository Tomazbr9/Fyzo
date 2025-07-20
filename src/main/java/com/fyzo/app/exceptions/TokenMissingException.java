package com.fyzo.app.exceptions;

public class TokenMissingException extends RuntimeException  {
	
    private static final long serialVersionUID = 1L;

	public TokenMissingException(String message) {
        super(message);
    }
}

