package model.builders;

import model.Client;

import java.util.List;

import model.Account;

public class ClientBuilder {
	private Client client;
	public ClientBuilder() {
		client = new Client();
	}
	
	public ClientBuilder setCnp(String cnp) {
		client.setCnp(cnp);
		return this;
	}
	
	public ClientBuilder setName(String name) {
		client.setName(name);
		return this;
	}
	
	public ClientBuilder setAddress(String address) {
		client.setAddress(address);
		return this;
	}
	
	public ClientBuilder setIdCard(Long idCard) {
		client.setIdCard(idCard);
		return this;
	}
	
	public ClientBuilder setAccounts(List<Account> accounts) {
		client.setAccounts(accounts);
		return this;
	}
	public Client build() {
		return this.client;
	}
	
}
