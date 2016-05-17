package org.immune.wrapper.httpclient;

import javax.ws.rs.core.MultivaluedMap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Lalit Mehra
 *
 * @param <T> Entity Response Type
 */
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class HttpRequestPayload<T> {

	private String target;
	private String mediaTypeConsumed;
	private String mediaTypeProduced;
	private T entity;
	private MultivaluedMap<String, Object> headers;

	public String getTarget() {
		return target!=null?target.trim():null;
	}

}
