package com.tomaz.finance.exceptions;

public class TokenMissingException extends ApplicationException {
	
    private static final long serialVersionUID = 1L;

	public TokenMissingException() {
        super("O token está ausente.");
    }
}

