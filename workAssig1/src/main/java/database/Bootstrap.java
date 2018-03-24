package database;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import static database.Constants.Rights.RIGHTS;
import static database.Constants.Roles.ROLES;
import static database.Constants.Schemas.SCHEMAS;
import static database.Constants.getRolesRights;

public class Bootstrap {
	
	    private static RightsRolesRepository rightsRolesRepository;

	    public static void main(String[] args) throws SQLException {
	        dropAll();

	        bootstrapTables();

	        bootstrapUserData();
	    }

	    private static void dropAll() throws SQLException {
	        for (String schema : SCHEMAS) {
	            System.out.println("Dropping all tables in schema: " + schema);

	            Connection connection = new JDBConnectionWrapper(schema).getConnection();
	            Statement statement = connection.createStatement();

	            String[] dropSQL = {"TRUNCATE `role_right`; \n" ,
	                    "DROP TABLE `role_right`; \n" ,
	                    
	                    "TRUNCATE `right`; \n" ,
	                    "DROP TABLE `right`; \n" ,
	                    
	                    "TRUNCATE `user_role`; \n" ,
	                    "DROP TABLE `user_role`; \n" ,
	                    
	                    
	                    "TRUNCATE `role`; \n" ,
	                    "DROP TABLE `role`",
	                    
	                    "TRUNCATE `account`; \n",
	                    "DROP TABLE `account`",
	                    
	                    "TRUNCATE `client`; \n",
	                    "DROP TABLE `client`",
	                    
	                    "TRUNCATE `user`; \n",
	                    "DROP TABLE `user`;"};
	            
	            for (String stmt: dropSQL) {
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
	                statement.execute(createTableSQL);
	            }
	        System.out.println("Done table bootstrap in "+schema);
	        } 
	    }

	    private static void bootstrapUserData() throws SQLException {
	        for (String schema : SCHEMAS) {
	            System.out.println("Bootstrapping user data for " + schema);

	            JDBConnectionWrapper connectionWrapper = new JDBConnectionWrapper(schema);
	            rightsRolesRepository = new RightsRolesRepositoryMySQL(connectionWrapper.getConnection());

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

	    }
}

