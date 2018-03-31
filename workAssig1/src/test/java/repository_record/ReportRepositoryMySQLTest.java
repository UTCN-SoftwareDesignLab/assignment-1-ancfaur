package repository_record;

import static database.Constants.EMPLOYEE_OPERATION_TYPES.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.*;
import database.DBConnectionFactory;
import model.Report;
import model.builders.ReportBuilder;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import repository.report.ReportRepository;
import repository.report.ReportRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.client.ClientService;
import service.client.ClientServiceMySQL;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceMySQL;

public class ReportRepositoryMySQLTest {

	private static ReportRepository reportRepository;
	
	// context
	private static AuthenticationService authenticationService;
	private static UserRepository userRepository;
	private static ClientService clientService;

	@BeforeClass
	public static void setupClass() {
		Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
		reportRepository = new ReportRepositoryMySQL(connection);
		
		
		// for context
		AccountRepository accountRepository = new AccountRepositoryMySQL(connection);
		ClientRepository clientRepository = new ClientRepositoryMySQL(connection, accountRepository);
		clientService = new ClientServiceMySQL(clientRepository);
		RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
		userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
		authenticationService = new AuthenticationServiceMySQL(userRepository, rightsRolesRepository);
		
		
		
	}

	@Before
	public void cleanUp() {
		reportRepository.removeAll();
	}
	

	@Before
	public void createUserAndClient() {
		clientService.removeAll();
		userRepository.removeAll();
		
		// create user1 and user2
		authenticationService.register("username1@yahoo.com", "Password1#", "employee");
		authenticationService.register("username2@yahoo.com", "Password1#", "employee");
		
		// create client
		clientService.register("1234567891011", "george", "somewhere");
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
		Long oneEmployeeId = new Long(1);
		Long otherEmployeeId = new Long(2);

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
