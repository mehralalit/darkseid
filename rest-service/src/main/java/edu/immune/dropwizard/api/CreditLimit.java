package edu.immune.dropwizard.api;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores cached data for User's credit limit and spends.<br>
 * Only those spends are maintained in this object which are still to be written
 * to the database.
 * 
 * @author lalit
 *
 */
public class CreditLimit {

	public Long creditLimit;
	public List<Spend> spends;

	public CreditLimit(Long creditLimit) {
		super();
		this.creditLimit = creditLimit;
		this.spends = new ArrayList<>();
	}

	public Long getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(Long creditLimit) {
		this.creditLimit = creditLimit;
	}

	public List<Spend> getSpends() {
		return spends;
	}

	public void setSpends(List<Spend> spends) {
		this.spends = spends;
	}

}
