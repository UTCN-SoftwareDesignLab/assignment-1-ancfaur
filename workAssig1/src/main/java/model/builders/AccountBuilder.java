package model.builders;

import java.util.Date;

import model.Account;

public class AccountBuilder {
	private Account account;
	public AccountBuilder() {
		account = new Account();
	}
	
	public AccountBuilder setId(Long id) {
		account.setId(id);
		return this;
	}
	
	public AccountBuilder setType(String type) {
		account.setType(type);
		return this;
	}
	
	public AccountBuilder setBalance(float balance) {
		account.setBalance(balance);
		return this;
	}
	
	public AccountBuilder setCreationDate(Date creationDate) {
		account.setCreationDate(creationDate);
		return this;
	}
	
	public Account build() {
		return this.account;
	}
}
