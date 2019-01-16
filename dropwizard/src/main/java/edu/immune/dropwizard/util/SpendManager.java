package edu.immune.dropwizard.util;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.immune.dropwizard.api.Spend;
import edu.immune.dropwizard.api.User;
import edu.immune.dropwizard.db.RedisOperations;
import edu.immune.dropwizard.exception.InvalidArgumentException;
import redis.clients.jedis.JedisPool;

/**
 * Manages Spend Records
 * @author lalit
 *
 */
public class SpendManager {

	private static Logger logger = LoggerFactory.getLogger(SpendManager.class);
	private static final String missingTokenErrorMessage = "token missing for fetching Spend";
	private static final String invalidTokenMessage = "token provided is invalid # ";
	
	/**
	 * fetches spend data
	 * @param token authorization token
	 * @return List of spends for an user
	 */
	public static List<Spend> getSpends(final String token) {
		if (StringUtils.isBlank(token)) {
			logger.error(missingTokenErrorMessage);
			throw new IllegalArgumentException(missingTokenErrorMessage);
		}

		String phone = UserRecordManager.getPhone(token);
		return UserRecordManager.getUser(phone).getSpends();
	}

	/**
	 * adds a new spend
	 * @param token authorization token
	 * @param spend user spend
	 */
	public static void addSpend(final String token, final Spend spend, JedisPool jedisPool) {
		if (StringUtils.isBlank(token)) {
			logger.error(missingTokenErrorMessage);
			throw new IllegalArgumentException(missingTokenErrorMessage);
		}
		
		if(!UserRecordManager.recordExists(token)) {
			logger.error(invalidTokenMessage.concat(token));
			throw new InvalidArgumentException();
		}
		
		String phone = UserRecordManager.getPhone(token);
		// Without Using Redis. Local Cache
		User user = UserRecordManager.getUser(phone);
		user.getSpends().add(spend);
		
		RedisOperations.add(jedisPool, phone, spend, user);
		
	}

}
