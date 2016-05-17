package org.immune.wrapper.httpclient.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.http.annotation.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Load Jersey Http Client configuration from property file
 * 
 * @author Lalit Mehra
 * @since February 20, 2016
 *
 */
@ThreadSafe
public final class ClientConfiguration {

	private static final ClientConfiguration _INSTANCE = new ClientConfiguration();
	private transient final Properties prop = new Properties();
	private static final String PROP_FILENAME = "httpconfig.properties";
	private static final Logger LOGGER = LoggerFactory.getLogger(ClientConfiguration.class);

	private ClientConfiguration() {

		final InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(PROP_FILENAME);

		try {
			prop.load(inputStream);
		} catch (IOException e) {
			LOGGER.error("Property File could not be obtained | Jersey Client Configuration", e);
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				LOGGER.error("Http Client Connection could not be closed", e);
			}
		}

	}

	/**
	 * Returns Singleton Instance of this class
	 * 
	 * @return {@linkplain ClientConfiguration}
	 */
	public static ClientConfiguration getInstance() {
		return _INSTANCE;
	}

	/**
	 * Returns the encapsulated property instance
	 * 
	 * @return {@link Properties}
	 */
	public Properties getProperties() {
		return prop;
	}

}
