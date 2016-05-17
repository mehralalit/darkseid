package org.immune.wrapper.httpclient;

import java.util.Properties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.apache.http.annotation.ThreadSafe;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.glassfish.jersey.apache.connector.ApacheClientProperties;
import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.RequestEntityProcessing;
import org.immune.wrapper.httpclient.config.ClientConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

/**
 * Provides a single instance of Jersey Rest Client. <br>
 * Uses Apache Http Client for Connection Pooling.
 * 
 * @author Lalit Mehra
 * @since February 13, 2016
 * 
 * @see ApacheConnectorProvider
 * @see PoolingHttpClientConnectionManager
 * @see ClientConfig
 * @see Client
 *
 */

@ThreadSafe
public final class JerseyHttpConnectionManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(JerseyHttpConnectionManager.class);
	private static final JerseyHttpConnectionManager _INSTANCE = new JerseyHttpConnectionManager();
	private Client client;
	
	private final String MAX_TOTAL = "httpconnection.pool.maxtotal";
	private final String DEFAULT_MAX_PER_ROUTE = "httpconnection.pool.defaultmaxperroute";
	private final String READ_TIMEOUT = "httpconnection.pool.readtimeout";
	private final String CONNECTION_TIMEOUT = "httpconnection.pool.connectiontimeout";
	
	private JerseyHttpConnectionManager() {
		
		Properties prop = ClientConfiguration.getInstance().getProperties();
		
		LOGGER.debug("max total: {}", Integer.parseInt(prop.getProperty(MAX_TOTAL)));
		
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
		connectionManager.setMaxTotal(Integer.parseInt(prop.getProperty(MAX_TOTAL)));
		connectionManager.setDefaultMaxPerRoute(Integer.parseInt(prop.getProperty(DEFAULT_MAX_PER_ROUTE)));

		ApacheConnectorProvider connectorProvider = new ApacheConnectorProvider();
		ClientConfig configuration = new ClientConfig();

		/* values are in milliseconds */
		configuration = configuration.property(ClientProperties.READ_TIMEOUT, Integer.parseInt(prop.getProperty(READ_TIMEOUT)));
		configuration = configuration.property(ClientProperties.CONNECT_TIMEOUT, Integer.parseInt(prop.getProperty(CONNECTION_TIMEOUT)));
		configuration = configuration.property(ApacheClientProperties.CONNECTION_MANAGER, connectionManager).register(JacksonJaxbJsonProvider.class);
		configuration = configuration.property(ClientProperties.REQUEST_ENTITY_PROCESSING, 	RequestEntityProcessing.BUFFERED);
		configuration = configuration.connectorProvider(connectorProvider);

		client = ClientBuilder.newClient(configuration);
		
	}

	public static JerseyHttpConnectionManager getInstance() {
		return _INSTANCE;
	}

	public Client getClient() {
		if(_INSTANCE == null) {
			throw new IllegalStateException("Jersey Http Connection Manager is not initialzed yet");
		}
		return client;
	}
	
}
