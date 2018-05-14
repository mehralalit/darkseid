package edu.immune.dropwizard.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.immune.dropwizard.api.Spend;
import edu.immune.dropwizard.api.User;

public class UserRecordPopulator {

	private static final Logger logger = LoggerFactory.getLogger(UserRecordPopulator.class);

	public static void populate() {
		User user1 = new User("9818757216", 1000l, "Lalit");
		User user2 = new User("9821860771", 1500l, "Atishay");
		User user3 = new User("9997998877", 560l, "Kamal");
		
		try {
			
			UserRecordManager.addRecord(user1);
			UserRecordManager.addRecord(user2);
			UserRecordManager.addRecord(user3);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	public static void populateTestRecord() {
		User user1 = new User("9818757216", 1000l, "Lalit", Long.toString(Long.MAX_VALUE));
		user1.setToken(Long.toString(Long.MAX_VALUE));
		try {
			UserRecordManager.addRecordWithToken(user1);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

	public static void populateTestRecord_Spend() {
		User user = UserRecordManager.getUser("9818757216");
		Spend spend = new Spend();
		spend.setAmount("30");
		spend.setDescription("milk");
		spend.setTransactionDate("29/04/2018");
		user.getSpends().add(spend);
	}

	public static void removeTestRecord_Spend() {
		UserRecordManager.getUser("9818757216").getSpends().clear();
	}

}
