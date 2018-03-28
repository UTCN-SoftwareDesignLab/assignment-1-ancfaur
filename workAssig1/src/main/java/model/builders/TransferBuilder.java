package model.builders;

import model.Account;
import model.Transfer;

public class TransferBuilder {
	private Transfer transfer;
	
	public TransferBuilder() {
		transfer = new Transfer();
	}
	
	public TransferBuilder setSource(Account source) {
		transfer.setSource(source);
		return this;
	}
	
	public TransferBuilder setDestination(Account dest) {
		transfer.setDestination(dest);
		return this;
	}
	
	public TransferBuilder setAmount(float amount) {
		transfer.setAmount(amount);
		return this;
	}
	
	public Transfer build() {
		return transfer;
	}

}
