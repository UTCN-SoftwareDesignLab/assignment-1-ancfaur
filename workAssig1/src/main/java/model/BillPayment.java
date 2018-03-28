package model;

public class BillPayment {

	private Bill bill;
	private Account account;
	
	public BillPayment(Bill bill, Account account) {
		this.bill = bill;
		this.account = account;
	}

	public Bill getBill() {
		return bill;
	}

	public Account getAccount() {
		return account;
	}
	
	
}
