package edu.immune.dropwizard.api;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Model class to store Spend Records
 * @author lalit
 *
 */
public class Spend {

	@NotBlank
	private String amount;

	@NotBlank
	@Length(min = 3, max = 50)
	private String description;

	@NotBlank
	private String transactionDate;

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTransactionDate() {
		return transactionDate;
	}
	
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Spend [amount=");
		builder.append(amount);
		builder.append(", description=");
		builder.append(description);
		builder.append(", transactionDate=");
		builder.append(transactionDate);
		builder.append("]");
		return builder.toString();
	}
	
}
