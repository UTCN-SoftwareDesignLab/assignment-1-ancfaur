package service.account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.util.List;

import org.junit.*;

import database.DBConnectionFactory;
import model.Account;
import model.Bill;
import model.builders.BillBuilder;
import repository.EntityNotFoundException;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import service.client.ClientService;
import service.client.ClientServiceMySQL;

public class AccountServiceMySQLTest {
	private static AccountRepository accountRepository;
	private static AccountService accountService;
	
	// for creating context
	private static ClientService clientService;
	
	@BeforeClass
	public static void setUp() {
		Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
		accountRepository = new AccountRepositoryMySQL(connection);
		accountService = new AccountServiceMySQL(accountRepository);
		
		//context
		ClientRepository clientRepository = new ClientRepositoryMySQL(connection, accountRepository);
		clientService = new ClientServiceMySQL(clientRepository);
		
	}
	
	@Before
	public void cleanUp() {
		accountService.removeAll();
	}
	
	@Before
	public void createOneClient() {
		clientService.removeAll();
		clientService.register("1234567891011", "gigel", "intr-o lume minunata");
	}
	
	
	@Test
	public void findAll() {
		List<Account> accounts = accountService.findAll();
		assertEquals(accounts.size(), 0);
	}
	
	
	@Test
	public void registerAccountToClient() {
		assertTrue(accountService.registerAccountToClient(500, "saving",  new Long(1)).getResult());
	}
	

	@Test
	public void findAllWhenDbNotEmpty() {
		accountService.registerAccountToClient(500, "saving",  new Long(1)).getResult();
		List<Account> accounts = accountService.findAll();
		assertEquals(accounts.size(), 1);
	}
	
	@Test
	public void findById() throws EntityNotFoundException {
		float amount = 500;
		accountService.registerAccountToClient(amount, "saving",  new Long(1)).getResult();
		accountService.registerAccountToClient(700, "saving",  new Long(2)).getResult();
		
		Account first = accountService.findById(new Long(1));
		
		Assert.assertEquals(first.getBalance(), amount, 0.0002);	
	}
	
	@Test
	 public void findAccountsForClient() {
		 int before = accountService.findAccountsForClient(new Long(1)).size();
		 accountService.registerAccountToClient(500, "saving",  new Long(1));
		 accountService.registerAccountToClient(700, "spending",  new Long(1));
		 int after =  accountService.findAccountsForClient(new Long(1)).size();
		 assertEquals(after, before+2);	 
	 }
	 
	@Test
	 public void updateAccount() throws EntityNotFoundException { 
		 float expectedAmount = 1000;
		 accountService.registerAccountToClient(500, "saving",  new Long(1)).getResult();
		 boolean result =accountService.updateAccount(new Long(1), 1000, "saving").getResult();
		 Account accountBack = accountService.findById(new Long(1));
		 assertTrue(result);	
		 Assert.assertEquals(accountBack.getBalance(), expectedAmount, 0.0002);
	 }
	
	@Test
	public void delete() {
		accountService.registerAccountToClient(500, "saving",  new Long(1)).getResult();
		int before = accountService.findAccountsForClient(new Long(1)).size();
		accountService.delete(new Long(1), new Long(1));
		int after =  accountService.findAccountsForClient(new Long(1)).size();
		assertEquals(after, before-1);	 
	}
	
	@Test
	public void transfer() throws EntityNotFoundException {
		float sourceOld, sourceNew, amount, destOld, destNew;
		sourceOld = 1000;
		destOld = 100;
		amount = 200;
		accountService.registerAccountToClient(sourceOld, "saving",  new Long(1));
		accountService.registerAccountToClient(destOld, "saving",  new Long(1));
		
		accountService.transfer(new Long(1), new Long(2), amount);
		
		sourceNew = accountService.findById(new Long(1)).getBalance();
		destNew = accountService.findById(new Long(2)).getBalance();
		Assert.assertEquals(sourceOld -amount, sourceNew, 0.0002);
		Assert.assertEquals(destOld + amount, destNew, 0.0002);
	}
	
	@Test
	public void processBill() throws EntityNotFoundException {
		float amount =100, balanceBefore=800, balanceAfter;
		Bill bill = new BillBuilder()
				.setBillAmount(amount)
				.build();
		accountService.registerAccountToClient(balanceBefore, "saving",  new Long(1));
		Account accountBefore = accountService.findById(new Long(1));
		accountService.processBill(bill, accountBefore);
		Account accountAfter = accountService.findById(new Long(1));
		balanceAfter = accountAfter.getBalance();
		Assert.assertEquals(balanceBefore - amount, balanceAfter, 0.0002);
	}
	
	
	 }
