package edu.immune.dropwizard.db;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.immune.dropwizard.api.CreditLimit;
import edu.immune.dropwizard.api.Spend;
import edu.immune.dropwizard.api.User;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Performs various redis based caching operations
 * 
 * @author lalit
 *
 */
public class RedisOperations {

	private static final String prefix = "CL_";
	private static final ObjectMapper objectMapper = new ObjectMapper();

	private static final Logger logger = LoggerFactory.getLogger(RedisOperations.class);

	public static synchronized void add(JedisPool jedisPool, String key, Spend spend, User user) {
		try (Jedis jedis = jedisPool.getResource()) {
			User usr = getValue(jedis, key);
			if (usr == null) {
				String value = objectMapper.writeValueAsString(user);
				jedis.set(prefix.concat(key), value);
			} else {
				usr.getSpends().add(spend);
				String value = objectMapper.writeValueAsString(usr);
				jedis.set(prefix.concat(key), value);
			}

		} catch (IOException e) {
			logger.error("error storing value in redis", e);
		}
	}

	/**
	 * returns {@link CreditLimit} object if found in redis storage
	 * 
	 * @param jedis
	 *            accessor to redis nosql
	 * @param key
	 *            record key
	 * @return {@link CreditLimit}
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public static User getValue(Jedis jedis, String key) throws JsonParseException, JsonMappingException, IOException {
		String json = jedis.get(prefix.concat(key));
		return objectMapper.readValue(json, User.class);
	}

}
