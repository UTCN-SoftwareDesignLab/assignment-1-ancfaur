package factory;
import database.DBConnectionFactory;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import repository.bill.BillRepository;
import repository.bill.BillRepositoryMySQL;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import repository.report.ReportRepository;
import repository.report.ReportRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.account.AccountService;
import service.account.AccountServiceMySQL;
import service.client.ClientService;
import service.client.ClientServiceMySQL;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceMySQL;

import java.sql.Connection;

public class ServiceFactory {

    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;
    private final ClientService clientService;
    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final BillRepository billRepository;
    private final ReportRepository reportRepository;
    

    private static ServiceFactory instance;

    public static ServiceFactory instance(Boolean componentsForTests) {
        if (instance == null) {
            instance = new ServiceFactory(componentsForTests);
        }
        return instance;
    }

    
    private ServiceFactory(Boolean componentsForTests) {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(componentsForTests).getConnection();
        this.rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        this.userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        this.authenticationService = new AuthenticationServiceMySQL(this.userRepository, this.rightsRolesRepository);
        this.accountRepository = new AccountRepositoryMySQL(connection);
        this.accountService = new AccountServiceMySQL(this.accountRepository);
        this.clientRepository = new ClientRepositoryMySQL(connection, accountRepository);
        this.clientService = new ClientServiceMySQL(this.clientRepository);
        this.billRepository = new BillRepositoryMySQL(connection);
        this.reportRepository = new ReportRepositoryMySQL(connection);
    }

	public AccountService getAccountService() {
		return accountService;
	}

	public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }


	public ClientService getClientService() {
		return clientService;
	}

	public BillRepository getBillRepository() {
		return billRepository;
	}
	
	public ReportRepository getReportRepository() {
		return reportRepository;
	}
	    
   
}