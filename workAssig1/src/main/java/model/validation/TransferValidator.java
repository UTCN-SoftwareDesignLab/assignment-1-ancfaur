package model.validation;

import java.util.ArrayList;
import java.util.List;

import model.Transfer;

public class TransferValidator implements Validator {
	private final List<String> errors;
	private final Transfer transfer;
	
	public TransferValidator(Transfer transfer) {
		errors = new ArrayList<>();
		this.transfer = transfer;
	}
	
	@Override
	public List<String> getErrors() {
		return errors;
	}
	
	@Override
	public boolean validate() {
		validateAmount();
		validateTransaction();
		return errors.isEmpty();
	}
	
	private void validateAmount() {
		if (transfer.getAmount()<0) errors.add("The transfer amount cannot be a negative number");
	}
	
	private void validateTransaction() {
		if (transfer.getSource().getBalance()< transfer.getAmount()) errors.add("Not enough balance in the source account to fulfill the request");
	}


}
