package model.validation;

import java.util.ArrayList;
import java.util.List;

import model.BillPayment;

public class BillPaymentValidator implements Validator{

	private BillPayment billPayment;
	private final List<String> errors;
	
	public BillPaymentValidator(BillPayment billPayment) {
		this.billPayment = billPayment;
		errors = new ArrayList<>();	
	}
	
	
	@Override
	public List<String> getErrors() {
		return errors;
	}

	@Override
	public boolean validate() {
		validateBillAmount();
		validateBillStatus();
		validatePayment();
		return errors.isEmpty();
	}
	
	private void validateBillAmount() {
		if (billPayment.getBill().getAmount()<0) {
			errors.add("The processed bill has negative amount=> ALERT");
		}
	}
	
	private void validateBillStatus() {
		if (billPayment.getBill().isPaid()==true) {
			errors.add("The bill has been already paid");
		}
	}
	
	private void validatePayment() {
		if (billPayment.getBill().getAmount()> billPayment.getAccount().getBalance()) {
			errors.add("Not enough balance to pay the bill");
		}
	}
		

}
