package edu.immune.dropwizard.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
	
	private static final long serialVersionUID = 3326297736472279805L;
	
	private String phone;
	private Long creditLimit;
	private String name;
	private List<Spend> spends;
	private String token;

	public User() {
		super();
	}
	
	public User(String phone, Long creditLimit, String name, String token) {
		super();
		this.phone = phone;
		this.creditLimit = creditLimit;
		this.name = name;
		this.token = token;
		spends = new ArrayList<>();
	}
	
	public User(String phone, Long creditLimit, String name) {
		super();
		this.phone = phone;
		this.creditLimit = creditLimit;
		this.name = name;
		spends = new ArrayList<>();
	}
	
	public User(String phone) {
		super();
		this.phone = phone;
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Long getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(Long creditLimit) {
		this.creditLimit = creditLimit;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Spend> getSpends() {
		return spends;
	}

	public void setSpends(List<Spend> spends) {
		this.spends = spends;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [phone=");
		builder.append(phone);
		builder.append(", creditLimit=");
		builder.append(creditLimit);
		builder.append(", name=");
		builder.append(name);
		builder.append(", spends=");
		builder.append(spends);
		builder.append(", token=");
		builder.append(token);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		return true;
	}

}
