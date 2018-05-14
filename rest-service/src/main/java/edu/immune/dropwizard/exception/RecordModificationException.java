package edu.immune.dropwizard.exception;

public class RecordModificationException extends RuntimeException {
	
	private static final long serialVersionUID = -7448443561128370804L;

	public RecordModificationException(String message) {
		super(message);
	}

	public RecordModificationException() {
		super("Unable to insert new record");
	}

	public RecordModificationException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
