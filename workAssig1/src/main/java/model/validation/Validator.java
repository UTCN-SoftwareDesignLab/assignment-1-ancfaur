package model.validation;

import java.util.List;

public interface Validator {
	public List<String> getErrors();
	public boolean validate(); 
}
