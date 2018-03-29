package model;

import java.util.Date;

public class Report {
	private Long id;
	private Long userId;
	private Date date;
	private Long clientId;
	private String operationType;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Long getClientId() {
		return clientId;
	}
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
	public String getOperationType() {
		return operationType;
	}
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String toString() {
		return "REPORT: "+id +"\n "+
			    "USER_ID: "+userId +"\n "+
			    "DATE: "+date +"\n "+
			    "CLIENT_ID: "+clientId +"\n "+
			    "OPERATION TYPE: "+ operationType +"\n\n\n";		
	}


}
