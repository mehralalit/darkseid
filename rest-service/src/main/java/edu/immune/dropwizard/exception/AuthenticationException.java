package edu.immune.dropwizard.exception;

public class AuthenticationException extends Exception {
	
	private static final long serialVersionUID = -7448443561128370804L;

	public AuthenticationException(String message) {
		super(message);
	}

	public AuthenticationException() {
		super("Illegal Access. Permission Denied");
	}

	public AuthenticationException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
