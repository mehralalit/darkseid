package org.immune.wrapper.httpclient.model;

import javax.ws.rs.core.MultivaluedMap;

public class ClientResponse<T> {

	private MultivaluedMap<String, Object> headers;
	private T response;

	public MultivaluedMap<String, Object> getHeaders() {
		return headers;
	}

	public void setHeaders(MultivaluedMap<String, Object> headers) {
		this.headers = headers;
	}

	public T getResponse() {
		return response;
	}

	public void setResponse(T response) {
		this.response = response;
	}

}
