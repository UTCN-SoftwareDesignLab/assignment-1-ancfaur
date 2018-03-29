package model.builders;

import java.util.Date;

import model.Report;

public class ReportBuilder {
	private Report report;

	public ReportBuilder() {
		report = new Report();
	}

	public ReportBuilder setId(Long id) {
		report.setId(id);
		return this;
	}
	
	public ReportBuilder setUserId(Long userId) {
		report.setUserId(userId);
		return this;
	}

	public ReportBuilder setClientId(Long clientId) {
		report.setClientId(clientId);
		return this;
	}

	public ReportBuilder setDate(Date date) {
		report.setDate(date);
		return this;
	}
	
	public ReportBuilder setOperationType(String operationType) {
		report.setOperationType(operationType);
		return this;
	}

	public Report build() {
		return report;
	}

}
