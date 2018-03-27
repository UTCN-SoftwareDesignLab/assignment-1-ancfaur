package model.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import model.Client;

public class ClientValidator implements Validator{
	private static final String CNP_VALIDATION_REGEX = "[0-9]+";
	private static final int CNP_VALIDATION_LENGTH=13;
	private static final String NAME_VALIDATION_REGEX =  "^[a-zA-Z\\s]+";
	private final List<String> errors;
	private final Client client;
	
	public ClientValidator(Client client) {
		this.client = client;
		errors = new ArrayList<>();
	}
	
	@Override
	public List<String> getErrors() {
		return errors;
	}
	@Override
	
	public boolean validate() {
	    validateCnp(client.getCnp());
	    validateName(client.getName());
	    return errors.isEmpty();
	}
	
	private void validateCnp(String cnp) {
		if (!Pattern.compile(CNP_VALIDATION_REGEX).matcher(cnp).matches()) {
            errors.add("Forbitten character in cnp");
        }
		if (cnp.length()!=CNP_VALIDATION_LENGTH) {
			errors.add("Cnp doesn't have proper length of" + CNP_VALIDATION_LENGTH);
		}
	}
	
	private void validateName(String name) {
		if (!Pattern.compile(NAME_VALIDATION_REGEX).matcher(name).matches()) {
            errors.add("Forbitten character in name");
        }
	}
	
	
}
