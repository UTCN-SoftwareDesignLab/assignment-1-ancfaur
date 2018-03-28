package model;

public class Transfer {

	private Account source;
	private Account destination;
	private float amount;


	
	public Account getSource() {
		return source;
	}



	public void setSource(Account source) {
		this.source = source;
	}



	public Account getDestination() {
		return destination;
	}



	public void setDestination(Account destination) {
		this.destination = destination;
	}



	public float getAmount() {
		return amount;
	}



	public void setAmount(float amount) {
		this.amount = amount;
	}



	public String toString() {
		return "Transaction from account "+ source.getId() + " to account "+destination.getId();
	}
	
	
	
}
