package model.builders;

import model.Bill;

public class BillBuilder {

	private Bill bill;
	
	public BillBuilder() {
		this.bill = new Bill();
		
	}
	
	public BillBuilder setBillId(Long id) {
		bill.setId(id);
		return this;
	}
	
	public BillBuilder setBillAmount(float amount) {
		bill.setAmount(amount);
		return this;
	}
	
	public BillBuilder setBillUtility(String utility) {
		bill.setUtility(utility);
		return this;
	}
	
	public BillBuilder setPaidStatus(boolean status) {
		bill.setPaid(status);
		return this;
	}
	
	public Bill build() {
		return bill;
	}
	
	
}
