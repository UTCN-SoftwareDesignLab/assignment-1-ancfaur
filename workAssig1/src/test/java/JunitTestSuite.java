import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
   repository_record.ReportRepositoryMySQLTest.class,
   service.account.AccountServiceMySQLTest.class,
   service.client.ClientServiceMySQLTest.class,
   service.user.AuthenticationServiceMySQLTest.class
})

public class JunitTestSuite {
	
	
}  