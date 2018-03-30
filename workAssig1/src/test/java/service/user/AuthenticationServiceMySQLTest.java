package service.user;

import database.DBConnectionFactory;
import model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import java.sql.Connection;

public class AuthenticationServiceMySQLTest {

	private static AuthenticationService authenticationService;
	private static UserRepository userRepository;

	@BeforeClass
	public static void setUp() {
		Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
		RightsRolesRepository rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
		userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);

		authenticationService = new AuthenticationServiceMySQL(userRepository, rightsRolesRepository);
	}

	@Before
	public void cleanUp() {
		userRepository.removeAll();
	}

	@Test
	public void register() throws Exception {
		Assert.assertTrue(authenticationService.register("username@yahoo.com", "Password1#", "employee").getResult());
	}

	@Test
	public void login() throws Exception {
		String username = "username@yahoo.com";
		String password = "Password1#";
		String role = "employee";
		authenticationService.register(username, password, role);

		User user = authenticationService.login(username, password).getResult();

		Assert.assertNotNull(user);
	}


}