package database;

import repository.bill.BillRepository;
import repository.bill.BillRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;

import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import model.Bill;
import model.Role;
import model.User;

import static database.Constants.Rights.RIGHTS;
import static database.Constants.Roles.ROLES;
import static database.Constants.Schemas.SCHEMAS;
import static database.Constants.getRolesRights;
import static database.Constants.getPredefinedUsers;
import static database.Constants.getPredefinedBills;

import service.user.AuthenticationService;
import service.user.AuthenticationServiceMySQL;

public class Bootstrap {

	private static RightsRolesRepository rightsRolesRepository;
	private static UserRepository userRepository;
	private static BillRepository billRepository;

	public static void main(String[] args) throws SQLException {
		dropAll();

		bootstrapTables();

		bootstrapUserData();

		bootstrapBills();

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
		Iterator<?> it = user_role.entrySet().iterator();
		AuthenticationService authenticationService = new AuthenticationServiceMySQL(userRepository,
				rightsRolesRepository);
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			String username = ((User) pair.getKey()).getUsername();
			String password = ((User) pair.getKey()).getPassword();
			String roleName = (String) pair.getValue();
			System.out.println("I will register new users");
			authenticationService.register(username, password, roleName);
			it.remove();
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
}
