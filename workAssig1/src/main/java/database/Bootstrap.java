package database;

import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import repository.bill.BillRepository;
import repository.bill.BillRepositoryMySQL;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;

import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import model.Bill;
import model.Client;
import model.User;

import static database.Constants.Rights.RIGHTS;
import static database.Constants.Roles.ROLES;
import static database.Constants.Schemas.SCHEMAS;
import static database.Constants.getRolesRights;
import static database.Constants.getPredefinedUsers;
import static database.Constants.getPredefinedBills;
import static database.Constants.getPredefinedClientsWithAccounts;

import service.user.AuthenticationService;
import service.user.AuthenticationServiceMySQL;

public class Bootstrap {

	private static RightsRolesRepository rightsRolesRepository;
	private static UserRepository userRepository;
	private static BillRepository billRepository;
	private static ClientRepository clientRepository;
	private static AccountRepository accountRepository;

	public static void main(String[] args) throws SQLException {
		dropAll();

		bootstrapTables();

		bootstrapUserData();

		bootstrapBills();
		
		bootstrapClients();

	}

	private static void dropAll() throws SQLException {
		for (String schema : SCHEMAS) {
			System.out.println("Dropping all tables in schema: " + schema);

			Connection connection = new JDBConnectionWrapper(schema).getConnection();
			Statement statement = connection.createStatement();

			String[] dropSQL = { "TRUNCATE `role_right`; \n", "DROP TABLE `role_right`; \n",

					"TRUNCATE `right`; \n", "DROP TABLE `right`; \n",
					
					"TRUNCATE `report`; \n", "DROP TABLE `report`;",

					"TRUNCATE `user_role`; \n", "DROP TABLE `user_role`; \n",

					"TRUNCATE `role`; \n", "DROP TABLE `role`",

					"TRUNCATE `client_account`; \n", "DROP TABLE `client_account`",

					"TRUNCATE `account`; \n", "DROP TABLE `account`",

					"TRUNCATE `client`; \n", "DROP TABLE `client`",

					"TRUNCATE `user`; \n", "DROP TABLE `user`;",
					
					"TRUNCATE `bill`; \n", "DROP TABLE `bill`;",
					
					
			};

			for (String stmt : dropSQL) {
				statement.execute(stmt);
			}
		}

		System.out.println("Done table bootstrap");
	}

	private static void bootstrapTables() throws SQLException {
		SQLTableCreationFactory sqlTableCreationFactory = new SQLTableCreationFactory();

		for (String schema : SCHEMAS) {
			System.out.println("Bootstrapping " + schema + " schema");

			JDBConnectionWrapper connectionWrapper = new JDBConnectionWrapper(schema);
			Connection connection = connectionWrapper.getConnection();

			Statement statement = connection.createStatement();

			for (String table : Constants.Tables.ORDERED_TABLES_FOR_CREATION) {
				String createTableSQL = sqlTableCreationFactory.getCreateSQLForTable(table);
				System.out.println("incerc sa fac: " + table);
				statement.execute(createTableSQL);
			}
			System.out.println("Done table bootstrap in " + schema);
		}
	}

	private static void bootstrapUserData() throws SQLException {
		for (String schema : SCHEMAS) {
			System.out.println("Bootstrapping user data for " + schema);

			JDBConnectionWrapper connectionWrapper = new JDBConnectionWrapper(schema);
			rightsRolesRepository = new RightsRolesRepositoryMySQL(connectionWrapper.getConnection());
			userRepository = new UserRepositoryMySQL(connectionWrapper.getConnection(), rightsRolesRepository);

			bootstrapRoles();
			bootstrapRights();
			bootstrapRoleRight();
			bootstrapUserRoles();
		}
	}

	private static void bootstrapRoles() throws SQLException {
		for (String role : ROLES) {
			rightsRolesRepository.addRole(role);
		}
	}

	private static void bootstrapRights() throws SQLException {
		for (String right : RIGHTS) {
			rightsRolesRepository.addRight(right);
		}
	}

	private static void bootstrapRoleRight() throws SQLException {
		Map<String, List<String>> rolesRights = getRolesRights();

		for (String role : rolesRights.keySet()) {
			Long roleId = rightsRolesRepository.findRoleByTitle(role).getId();

			for (String right : rolesRights.get(role)) {
				Long rightId = rightsRolesRepository.findRightByTitle(right).getId();

				rightsRolesRepository.addRoleRight(roleId, rightId);
			}
		}
	}

	private static void bootstrapUserRoles() throws SQLException {
		Map<User, String> user_role = getPredefinedUsers();
		AuthenticationService authenticationService = new AuthenticationServiceMySQL(userRepository,
				rightsRolesRepository);
		for (Map.Entry<User, String> pair : user_role.entrySet())
		{
			String username = pair.getKey().getUsername();
			String password = pair.getKey().getPassword();
			String roleName = pair.getValue();
			authenticationService.register(username, password, roleName);
		}
			
	}

	private static void bootstrapBills() throws SQLException {
		for (String schema : SCHEMAS) {
			System.out.println("Bootstrapping bills for " + schema);

			JDBConnectionWrapper connectionWrapper = new JDBConnectionWrapper(schema);
			billRepository = new BillRepositoryMySQL(connectionWrapper.getConnection());
			
			for (Bill bill : getPredefinedBills()) {
				billRepository.save(bill);
			}

			
		}
	}
	
	private static void bootstrapClients() {
		for (String schema : SCHEMAS) {
			System.out.println("Bootstrapping clients for " + schema);

			JDBConnectionWrapper connectionWrapper = new JDBConnectionWrapper(schema);
			accountRepository=  new AccountRepositoryMySQL(connectionWrapper.getConnection());
			clientRepository = new ClientRepositoryMySQL(connectionWrapper.getConnection(), accountRepository);
			
			List<Client> clients = getPredefinedClientsWithAccounts();
			for(Client client:clients) {
				clientRepository.save(client);
			}
		}
	}
}
