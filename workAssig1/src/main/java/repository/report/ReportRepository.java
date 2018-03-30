package repository.report;

import java.util.Date;
import java.util.List;

import model.Report;

public interface ReportRepository {
	
	boolean save(Report report);
	List<Report> findReports(Long userId, Date startDate, Date endDate);
	void removeAll();
}
