package edu.immune.dropwizard.test;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.immune.dropwizard.api.Spend;
import edu.immune.dropwizard.util.SpendManager;
import edu.immune.dropwizard.util.UserRecordPopulator;

public class SpendManagerTest {

	@BeforeClass
	public static void setUp() {
		UserRecordPopulator.populateTestRecord();
	}

	@Test
	public void getSpendsTest_EmptySpendRecord() {
		List<Spend> spends = SpendManager.getSpends(Long.toString(Long.MAX_VALUE));
		Assert.assertTrue(spends.isEmpty());
	}

	@Test
	public void getSpendsTest_FilledSpendRecord() {
		UserRecordPopulator.populateTestRecord_Spend();
		List<Spend> spends = SpendManager.getSpends(Long.toString(Long.MAX_VALUE));
		Assert.assertFalse(spends.isEmpty());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void getSpendsTest_MissingToken() {
		SpendManager.getSpends(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void getSpendsTest_EmptyToken() {
		SpendManager.getSpends("");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void addSpendTest_MissingToken() {
		Spend spend = new Spend();
		spend.setAmount("35");
		spend.setDescription("bread");
		spend.setTransactionDate("29/04/2018");
		SpendManager.addSpend(null, spend, null);
	}
	
//	@Test
	public void addSpendTest() {
		Spend spend = new Spend();
		spend.setAmount("35");
		spend.setDescription("bread");
		spend.setTransactionDate("29/04/2018");
		SpendManager.addSpend(Long.toString(Long.MAX_VALUE), spend, null);
		List<Spend> spends = SpendManager.getSpends(Long.toString(Long.MAX_VALUE));
		Assert.assertEquals(spends.size(), 1);
	}
	
	@After
	public void tearUp() {
		UserRecordPopulator.removeTestRecord_Spend();
	}
	
}
