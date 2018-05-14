package edu.immune.dropwizard.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.immune.dropwizard.api.User;
import edu.immune.dropwizard.exception.RecordModificationException;

/**
 * Manages User Records
 * 
 * @author lalit
 *
 */
public class UserRecordManager {

	private static Logger logger = LoggerFactory.getLogger(UserRecordManager.class);

	/**
	 * Stores User record. Uses user phone number as key
	 */
	private static Map<String, User> userRecords = new ConcurrentHashMap<>();
	/**
	 * Stores mapping of token and user phone number
	 */
	private static Map<String, String> tokens = new ConcurrentHashMap<>();

	/**
	 * Adds new record to both the maps<br>
	 * In case of an exception partial records are removed i.e. if only one of
	 * the map has a record it is removed
	 * 
	 * @param user
	 *            user record
	 */
	public static void addRecord(User user) {

		if (StringUtils.isBlank(user.getPhone()))
			throw new IllegalArgumentException("Mandatory field phone not present");

		if (userExists(user.getPhone()))
			throw new IllegalArgumentException("User already exists");

		userRecords.put(user.getPhone(), user);
		logger.info("Record Added Successfully, PhoneNo: {}", user.getPhone());

	}

	/**
	 * Updates the token provided to the user
	 * @param phone phone number of the user
	 * @param token generated token
	 */
	public static void updateToken(String phone, String token) {
		if (StringUtils.isBlank(phone))
			throw new IllegalArgumentException("Mandatory field phone not present");

		if (StringUtils.isBlank(token))
			throw new IllegalArgumentException("Mandatory field token not present");

		String prevToken = getUser(phone).getToken();

		tokens.put(token, phone);
		getUser(phone).setToken(token);

		if (StringUtils.isNotBlank(prevToken)) {
			tokens.remove(prevToken);
		}
	}

	public static void addRecordWithToken(User user) {

		if (StringUtils.isBlank(user.getPhone()))
			throw new IllegalArgumentException("Mandatory field phone not present");

		if (userExists(user.getPhone()))
			throw new IllegalArgumentException("User already exists");

		try {
			tokens.put(user.getToken(), user.getPhone());
			userRecords.put(user.getPhone(), user);
		} catch (Exception e) {
			logger.error("Error adding new entries", e);
			tokens.remove(user.getToken());
			userRecords.remove(user.getPhone());
			throw new RecordModificationException("Error adding new entries", e);
		}

	}

	/**
	 * Check if the user record exists
	 * 
	 * @param token
	 *            authorization token
	 * @return boolean
	 */
	public static boolean recordExists(String token) {
		return tokens.containsKey(token);
	}

	/**
	 * get phone number from token
	 * 
	 * @param token
	 *            token issued to the user
	 * @return phone number of the user
	 */
	public static String getPhone(String token) {
		return tokens.get(token);
	}

	/**
	 * check if user record exists
	 * 
	 * @param phone
	 *            phone number of the user
	 * @return boolean true is exists otherwise false
	 */
	public static boolean userExists(String phone) {
		return userRecords.containsKey(phone);
	}

	/**
	 * get user record
	 * 
	 * @param phone
	 *            phone number of the user
	 * @return {@link User}
	 */
	public static User getUser(String phone) {
		return userRecords.get(phone);
	}

}
