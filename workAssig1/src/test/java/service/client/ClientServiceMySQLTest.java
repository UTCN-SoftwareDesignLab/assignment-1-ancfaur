package service.client;
import static org.junit.Assert.assertEquals;
import java.sql.Connection;
import java.util.List;
import org.junit.*;
import database.DBConnectionFactory;
import model.Client;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;

public class ClientServiceMySQLTest {
	private static AccountRepository accountRepository;
	private static ClientRepository clientRepository;
	private static ClientService clientService;

	@BeforeClass
	public static void setUp() {
		Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
		accountRepository = new AccountRepositoryMySQL(connection);
		clientRepository = new ClientRepositoryMySQL(connection, accountRepository);
		clientService = new ClientServiceMySQL(clientRepository);

	}

	@Before
	public void cleanUp() {
		clientService.removeAll();
	}

	@Test
	public void findAll() {
		List<Client> clients = clientService.findAll();
		assertEquals(clients.size(), 0);
	}

	@Test
	public void findAllWhenDbNotEmpty() throws Exception {
		clientService.register("1234567891011", "mirel", "somewhere");
		List<Client> clients = clientService.findAll();

		Assert.assertTrue(clients.size() == 1);
	}

	@Test
	public void register() throws Exception {
		Assert.assertTrue(clientService.register("1234567891011", "george", "somewhere").getResult());
	}

	@Test
	public void updateClient() {
		clientService.register("1234567891011", "george", "somewhere");
		boolean result = clientService.updateClient(new Long(1), "1234567891011", "tudor", "somewhere").getResult();
		Client client = clientService.findByCnp("1234567891011");
		Assert.assertTrue(result && client.getName().equals("tudor"));
	}

	@Test
	public void findByCnp() {
		clientService.register("1234567891011", "ionut", "strada bucuriei");
		Client client = clientService.findByCnp("1234567891011");
		Assert.assertTrue(client.getName().equals("ionut"));
	}

}
