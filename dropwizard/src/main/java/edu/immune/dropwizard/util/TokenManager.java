package edu.immune.dropwizard.util;

import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang3.StringUtils;

import edu.immune.dropwizard.exception.AuthenticationException;

/**
 * Manages Authorization Token
 * @author lalit
 *
 */
public class TokenManager {

	/**
	 * Atomic Long is used for simple token for concurrency control
	 */
	private static AtomicLong token = new AtomicLong(Long.MAX_VALUE);
	
	/**
	 * check for an existing token
	 * @param phone user phone number
	 * @return true if the record exists false otherwise
	 */
	private static boolean checkIfExists(String phone) {
		return UserRecordManager.userExists(phone);
	}
	
	/**
	 * generates a new token
	 * @return generated token
	 */
	private static String generateNewToken() {
		return Long.toString(token.getAndDecrement());
	}
	
	
	/**
	 * generates a new token if the phone number is not present in the local cache <br>
	 * and adds it to the cache, else existing token is returned <br>
	 * @param phone user phone number 
	 * @return authorization token
	 * @throws AuthenticationException if the authentication fails
	 */
	public static String getToken(String phone) throws AuthenticationException {
		String token;
		
		if (StringUtils.isBlank(phone))
			throw new IllegalArgumentException("invalid request, phone number missing");
		
//		if(!checkIfExists(phone)) 
//			throw new AuthenticationException();
//		else if(StringUtils.isBlank(UserRecordManager.getUser(phone).getToken())) {
//			token = generateNewToken();
//			UserRecordManager.getUser(phone).setToken(token);
//		} else {
//			return UserRecordManager.getUser(phone).getToken();
//		}
		
		if(!checkIfExists(phone)) 
			throw new AuthenticationException();
		else {
			token = generateNewToken();
		}
		
		return token;
	}
	
	/**
	 * check if the token is present is the local cache
	 * @param token token entered for authorization
	 * @return true if the record exists false otherwise
	 */
	public static boolean authorizeToken(String token) {
		if (StringUtils.isBlank(token))
			throw new IllegalArgumentException("invalid authorization request, token missing");
		
		return UserRecordManager.recordExists(token);
	}
	
}
