package edu.lalit.httpclient;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.rx.rxjava.RxObservable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.lalit.httpclient.exception.HttpCommunicationException;
import edu.lalit.httpclient.exception.IllegalPayloadException;
import rx.Observable;

/**
 * Implementation of {@link JerseyHttpConnectionManager}
 * 
 * @author Lalit Mehra
 *
 */
public final class JerseyHttpClient {

	private static final JerseyHttpConnectionManager CONNECTION_MANAGER = JerseyHttpConnectionManager.getInstance();
	private static final Logger LOGGER = LoggerFactory.getLogger(JerseyHttpClient.class);

	private static final String CLIENT_FAILURE = "Http Client Instance not Initialized";
	private static final String ILLEGAL_PAYLOAD = "Incorrect Payload received in request";
	private static final String RESPONSE_PROCESSING_FAILURE = "Response Processing Failure";

	private JerseyHttpClient() {}

	/**
	 * Sends a Http Post Request to a specified URL<br>
	 * It is the duty of the callee code to close the client instance either
	 * through close() or readEntity() <br/>
	 * to ensure connections return to the pool<br/>
	 * To send Form Parameters as input wrap them inside
	 * {@linkplain MultivaluedMap} and pass along to this method as an Entity
	 * 
	 * @param payload {@link HttpRequestPayload}
	 * @return {@link HttpRequestPayload}
	 * @throws HttpCommunicationException
	 * @throws IllegalPayloadException
	 * @see HttpRequestPayload
	 */
	public static Response sendHttpPostRequest(final HttpRequestPayload<?> payload)
			throws HttpCommunicationException, IllegalPayloadException {
		try {
			final Client client = CONNECTION_MANAGER.getClient();
			LOGGER.debug("client obtained {}", client);
			final WebTarget target = client.target(payload.getTarget());
			LOGGER.debug("target set {}", target.getUri());
			final Entity<?> entity = getRequestEntity(payload);
			LOGGER.debug("entity filled {}", entity.toString());

			Response response = target.request(payload.getMediaTypeConsumed()).headers(payload.getHeaders()).post(entity);
			LOGGER.debug("response received {}", response.toString());
			return response;
		} catch (IllegalStateException e) {
			LOGGER.error(CLIENT_FAILURE, e);
			throw new HttpCommunicationException(e);
		} catch (IllegalArgumentException e) {
			LOGGER.error(ILLEGAL_PAYLOAD, e);
			throw new IllegalPayloadException(e);
		} catch (ProcessingException e) {
			LOGGER.error(RESPONSE_PROCESSING_FAILURE, e);
			throw new HttpCommunicationException(e);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new HttpCommunicationException(e);
		}
	}

	/**
	 * Sends a Http Post Request to a specified URL<br/>
	 * To send Form Parameters as input wrap them inside
	 * {@linkplain MultivaluedMap} and pass along to this method as an Entity
	 * 
	 * @param payload {@link HttpRequestPayload}
	 * @return {@link HttpRequestPayload}
	 * @throws IllegalPayloadException
	 * @throws HttpCommunicationException
	 * @see HttpRequestPayload
	 */
	public static <T> T sendHttpPostRequest(final HttpRequestPayload<?> payload, final Class<T> responseType)
			throws HttpCommunicationException, IllegalPayloadException {
		return sendHttpPostRequest(payload).readEntity(responseType);
	}

	@SuppressWarnings("unchecked")
	private static Entity<?> getRequestEntity(final HttpRequestPayload<?> payload) {
		if (payload.getEntity() instanceof MultivaluedMap<?, ?>) {
			return Entity.form((MultivaluedMap<String, String>) payload.getEntity());
		} else {
			return Entity.entity(payload.getEntity(), payload.getMediaTypeProduced());
		}
	}

	/**
	 * Sends a Http Get Request to a specified URL<br>
	 * To send Form Parameters as input wrap them inside
	 * {@linkplain MultivaluedMap} and pass along to this method as an Entity
	 * 
	 * @param payload {@link HttpRequestPayload}
	 * @return {@link HttpRequestPayload}
	 * @throws IllegalPayloadException
	 * @throws HttpCommunicationException
	 * @see HttpRequestPayload
	 */
	public static <T> T sendHttpGetRequest(final HttpRequestPayload<?> payload, final Class<T> responseType)
			throws HttpCommunicationException, IllegalPayloadException {
		return sendHttpGetRequest(payload).readEntity(responseType);
	}

	/**
	 * Sends a Http Get Request to a specified URL<br>
	 * It is the duty of the callee code to close the client instance either
	 * through close() or readEntity() <br/>
	 * to ensure connections return to the pool<br/>
	 * To send Form Parameters as input wrap them inside
	 * {@linkplain MultivaluedMap} and pass along to this method as an Entity
	 * 
	 * @param payload {@link HttpRequestPayload}
	 * @return {@link HttpRequestPayload}
	 * @throws HttpCommunicationException
	 * @throws IllegalPayloadException
	 * @see HttpRequestPayload
	 */
	public static Response sendHttpGetRequest(final HttpRequestPayload<?> payload)
			throws HttpCommunicationException, IllegalPayloadException {
		try {
			final Client client = CONNECTION_MANAGER.getClient();
			final WebTarget target = client.target(payload.getTarget());
			LOGGER.debug("request target: {}", payload.getTarget());
			return target.request(payload.getMediaTypeConsumed()).headers(payload.getHeaders()).get();
		} catch (IllegalStateException e) {
			LOGGER.error(CLIENT_FAILURE, e);
			throw new HttpCommunicationException(e);
		} catch (IllegalArgumentException e) {
			LOGGER.error(ILLEGAL_PAYLOAD, e);
			throw new IllegalPayloadException(e);
		} catch (ProcessingException e) {
			LOGGER.error(RESPONSE_PROCESSING_FAILURE, e);
			throw new HttpCommunicationException(e);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new HttpCommunicationException(e);
		}
	}

	/**
	 * Sends async Http Post Request to a specified URL<br/>
	 * To send Form Parameters as input wrap them inside
	 * {@linkplain MultivaluedMap} and pass along to this method as an Entity
	 * 
	 * @param payload {@link HttpRequestPayload}
	 * @return {@link HttpRequestPayload}
	 * @throws IllegalPayloadException
	 * @throws HttpCommunicationException
	 * @see HttpRequestPayload
	 */
	public static <T> Observable<T> sendASyncHttpPostRequest(final HttpRequestPayload<?> payload,
			final Class<T> responseType) throws IllegalPayloadException, HttpCommunicationException {
		try {
			final Client client = CONNECTION_MANAGER.getClient();
	
			final WebTarget target = client.target(payload.getTarget());
	
			final Entity<?> entity = getRequestEntity(payload);
			
			final Observable<T> observable = RxObservable.from(target).request().header("Rx-User", "RxJava").rx()
					.post(entity, responseType);
	
			return observable;
		} catch (IllegalStateException e) {
			LOGGER.error(CLIENT_FAILURE, e);
			throw new HttpCommunicationException(e);
		} catch (IllegalArgumentException e) {
			LOGGER.error(ILLEGAL_PAYLOAD, e);
			throw new IllegalPayloadException(e);
		} catch (ProcessingException e) {
			LOGGER.error(RESPONSE_PROCESSING_FAILURE, e);
			throw new HttpCommunicationException(e);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new HttpCommunicationException(e);
		}
	}

}
