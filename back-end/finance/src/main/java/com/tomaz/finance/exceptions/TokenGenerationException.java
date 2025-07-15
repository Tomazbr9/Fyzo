package com.tomaz.finance.exceptions;

public class TokenGenerationException extends RuntimeException  {
	
    private static final long serialVersionUID = 1L;

	public TokenGenerationException(String message) {
        super(message);
    }
}

