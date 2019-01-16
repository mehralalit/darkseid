package edu.immune.dropwizard.test;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.immune.dropwizard.api.User;
import edu.immune.dropwizard.util.UserRecordManager;
import edu.immune.dropwizard.util.UserRecordPopulator;

public class UserRecordManagerTest {

	@BeforeClass
	public static void setUp() {
		UserRecordPopulator.populateTestRecord();
	}

	@Test
	public void getUser_Exists() {
		User user = UserRecordManager.getUser("9818757216");
		Assert.assertTrue(null != user);
	}

	@Test
	public void getUser_DoesNotExist() {
		User user = UserRecordManager.getUser("9818757210");
		Assert.assertTrue(null == user);
	}
	
	@Test
	public void userExists_Exists() {
		boolean result = UserRecordManager.userExists("9818757216"); 
		Assert.assertTrue(result);
	}

	@Test
	public void userExists_DoesNotExist() {
		boolean result = UserRecordManager.userExists("9818757210");
		Assert.assertFalse(result);
	}
	
	@Test
	public void getPhone_Exists() {
		String phone = UserRecordManager.getPhone(Long.toString(Long.MAX_VALUE)); 
		Assert.assertEquals("9818757216", phone);
	}

	@Test
	public void getPhone_DoesNotExist() {
		String phone = UserRecordManager.getPhone(Long.toString(Long.MIN_VALUE)); 
		Assert.assertNotEquals("9818757216", phone);
	}
	
	@Test
	public void recordExists_Exists() {
		boolean result = UserRecordManager.recordExists(Long.toString(Long.MAX_VALUE)); 
		Assert.assertTrue(result);
	}

	@Test
	public void recordExists_DoesNotExist() {
		boolean result = UserRecordManager.recordExists(Long.toString(Long.MIN_VALUE));
		Assert.assertFalse(result);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void addRecord_phoneNotPresent() {
		User user = new User("", 1230l, "Harish");
		user.setToken(Long.toString(Long.MIN_VALUE));
		UserRecordManager.addRecord(user);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void addRecord_existingUser() {
		User user = new User("9818757216", 1230l, "Lalit");
		user.setToken(Long.toString(Long.MIN_VALUE));
		UserRecordManager.addRecord(user);
	}
	
}
