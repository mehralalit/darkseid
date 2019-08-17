package org.immune.wrapper.httpclient;

import javax.ws.rs.core.MultivaluedMap;

/**
 * @author Lalit Mehra
 *
 * @param <T> Entity Response Type
 */

public class HttpRequestPayload<T> {

	private String target;
	private String mediaTypeConsumed;
	private String mediaTypeProduced;
	private T entity;
	private MultivaluedMap<String, Object> headers;

	public HttpRequestPayload(String target, String mediaTypeConsumed, String mediaTypeProduced, T entity,
			MultivaluedMap<String, Object> headers) {
		super();
		this.target = target;
		this.mediaTypeConsumed = mediaTypeConsumed;
		this.mediaTypeProduced = mediaTypeProduced;
		this.entity = entity;
		this.headers = headers;
	}

	public HttpRequestPayload() {
		super();
	}

	public String getTarget() {
		return target != null ? target.trim() : null;
	}

	public String getMediaTypeConsumed() {
		return mediaTypeConsumed;
	}

	public void setMediaTypeConsumed(String mediaTypeConsumed) {
		this.mediaTypeConsumed = mediaTypeConsumed;
	}

	public String getMediaTypeProduced() {
		return mediaTypeProduced;
	}

	public void setMediaTypeProduced(String mediaTypeProduced) {
		this.mediaTypeProduced = mediaTypeProduced;
	}

	public T getEntity() {
		return entity;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}

	public MultivaluedMap<String, Object> getHeaders() {
		return headers;
	}

	public void setHeaders(MultivaluedMap<String, Object> headers) {
		this.headers = headers;
	}

	public void setTarget(String target) {
		this.target = target;
	}

}
