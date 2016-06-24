package org.immune.wrapper.httpclient.model;

import javax.ws.rs.core.MultivaluedMap;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientResponse<T> {

	private MultivaluedMap<String, Object> headers;
	private T response;
	
}
