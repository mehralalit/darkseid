package edu.lalit.httpclient.exception;

import edu.lalit.httpclient.JerseyHttpClient;

/**
 * Thrown when Client could not connect to the Host<br/>
 * @author Lalit Mehra
 * @since March 12, 2016
 * @see JerseyHttpClient
 *
 */
public class HttpCommunicationException extends Exception {

	public HttpCommunicationException() {
		super();
	}

	public HttpCommunicationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public HttpCommunicationException(String message, Throwable cause) {
		super(message, cause);
	}

	public HttpCommunicationException(String message) {
		super(message);
	}

	public HttpCommunicationException(Throwable cause) {
		super(cause);
	}

	private static final long serialVersionUID = -2175476667122100901L;

}
