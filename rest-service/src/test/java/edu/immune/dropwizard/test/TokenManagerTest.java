package edu.immune.dropwizard.test;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.immune.dropwizard.exception.AuthenticationException;
import edu.immune.dropwizard.util.TokenManager;
import edu.immune.dropwizard.util.UserRecordPopulator;

public class TokenManagerTest {

	@BeforeClass
	public static void setUp() {
		UserRecordPopulator.populateTestRecord();
	}

	@Test(expected = IllegalArgumentException.class)
	public void authorizeTokenTest_MissingToken() {
		TokenManager.authorizeToken(null);
	}
	
	@Test()
	public void authorizeTokenTest_RecordExist() {
		boolean result = TokenManager.authorizeToken(Long.toString(Long.MAX_VALUE));
		Assert.assertTrue(result);
	}
	
	@Test()
	public void authorizeTokenTest_RecordDoesNotExist() {
		boolean result = TokenManager.authorizeToken(Long.toString(Long.MIN_VALUE));
		Assert.assertFalse(result);
	}
	
	@Test(expected = AuthenticationException.class)
	public void getToken_AuthFailure_InvalidPhoneNo() throws AuthenticationException {
		TokenManager.getToken("9818757210");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void getToken_MissingPhoneNo() throws AuthenticationException {
		TokenManager.getToken(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void getToken_EmptyPhoneNo() throws AuthenticationException {
		TokenManager.getToken("");
	}
	
	@Test()
	public void getToken() throws AuthenticationException {
		String token = TokenManager.getToken("9818757216");
		Assert.assertEquals(Long.toString(Long.MAX_VALUE), token);
	}
	
}
