package edu.immune.dropwizard.exception;

public class InvalidArgumentException extends RuntimeException {
	
	private static final long serialVersionUID = -7448443561128370804L;

	public InvalidArgumentException(String message) {
		super(message);
	}

	public InvalidArgumentException() {
		super("Invalid Argument. No Record Found");
	}

	public InvalidArgumentException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
