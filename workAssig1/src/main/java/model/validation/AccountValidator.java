package model.validation;

import java.util.ArrayList;
import java.util.List;
import model.Account;

public class AccountValidator implements Validator {
	private final List<String> errors;
	private final Account account;
	
	public AccountValidator(Account account) {
		errors = new ArrayList<>();
		this.account = account;
	}
	
	
	@Override
	public List<String> getErrors() {
		return errors;
	}

	@Override
	public boolean validate() {
		validateBalance();
		return errors.isEmpty();
	}
	
	private void validateBalance() {
		if (account.getBalance()<0) errors.add("The balance cannot be a negative number");
	}

}
