package repository_record;

import static database.Constants.EMPLOYEE_OPERATION_TYPES.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.*;
import database.DBConnectionFactory;
import model.Report;
import model.builders.ReportBuilder;
import repository.report.ReportRepository;
import repository.report.ReportRepositoryMySQL;

public class ReportRepositoryMySQLTest {

	private static ReportRepository reportRepository;

	@BeforeClass
	public static void setupClass() {
		reportRepository = new ReportRepositoryMySQL(
				new DBConnectionFactory().getConnectionWrapper(true).getConnection());
	}

	@Before
	public void cleanUp() {
		reportRepository.removeAll();
	}

	private Date getCurrentDate() {
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		return date;
	}

	@Test
	public void findReports() throws Exception {
		Long userId = new Long(1);
		List<Report> reports = reportRepository.findReports(userId, getCurrentDate(), getCurrentDate());
		assertEquals(reports.size(), 0);
	}

	@Test
	public void findReportsWhenDbNotEmpty() throws Exception {
		Long oneEmployeeId = new Long(3);
		Long otherEmployeeId = new Long(4);

		Report reportUser1 = new ReportBuilder().setUserId(oneEmployeeId).setDate(getCurrentDate())
				.setClientId(new Long(1)).setOperationType(TRANSFER).build();
		Report reportUser2 = new ReportBuilder().setUserId(otherEmployeeId).setDate(getCurrentDate())
				.setClientId(new Long(1)).setOperationType(CREATE_ACCOUNT).build();

		reportRepository.save(reportUser1);
		reportRepository.save(reportUser2);

		List<Report> reportsUser1 = reportRepository.findReports(oneEmployeeId, getCurrentDate(),
				getCurrentDate());
		List<Report> reportsUser2 = reportRepository.findReports(otherEmployeeId, getCurrentDate(),
				getCurrentDate());
		assertEquals(reportsUser1.size(), 1);
		assertEquals(reportsUser2.size(), 1);
	}

	@Test
	public void save() throws Exception {
		assertTrue(reportRepository.save(new ReportBuilder().setUserId(new Long(1)).setDate(getCurrentDate())
				.setClientId(new Long(1)).setOperationType(CREATE_ACCOUNT).build()));
	}

}
