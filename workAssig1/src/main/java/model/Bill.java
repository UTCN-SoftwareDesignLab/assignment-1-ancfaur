package model;

import java.util.Date;

public class Bill {

	private Long id;
	private float amount;
	private String utility;
	private boolean paid;
	
	public String toString() {
		return "BILL: "+ id +"\n"+
				"UTILITY: " +utility+"\n"+
				"AMOUNT: " +amount +"\n"+
				"PAID: " +paid;
				
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public String getUtility() {
		return utility;
	}

	public void setUtility(String utility) {
		this.utility = utility;
	}

	public boolean isPaid() {
		return paid;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}
	
	

	
}
