package org.immune.wrapper.httpclient.exception;

import org.immune.wrapper.httpclient.HttpRequestPayload;
import org.immune.wrapper.httpclient.JerseyHttpClient;

/**
 * Thrown when the Payload Received is Incorrect or Malformed
 * @author Lalit Mehra
 * @since March 12, 2016
 * @see HttpRequestPayload
 * @see JerseyHttpClient
 *
 */
public class IllegalPayloadException extends Exception {

	public IllegalPayloadException() {
		super();
	}

	public IllegalPayloadException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public IllegalPayloadException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalPayloadException(String message) {
		super(message);
	}

	public IllegalPayloadException(Throwable cause) {
		super(cause);
	}

	private static final long serialVersionUID = -628736396997639730L;

}
