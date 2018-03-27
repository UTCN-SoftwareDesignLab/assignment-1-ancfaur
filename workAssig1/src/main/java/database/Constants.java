package database;
import static database.Constants.Rights.*;
import static database.Constants.Roles.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.User;
import model.builders.UserBuilder;

public class Constants {
	
	public static class Schemas {
        public static final String TEST = "test_bank";
        public static final String PRODUCTION = "bank";

        public static final String[] SCHEMAS = new String[]{TEST, PRODUCTION};
    }

    public static class Tables {
    	public static final String CLIENT = "client";
        public static final String ACCOUNT = "account";
        public static final String USER = "user";
        public static final String ROLE = "role";
        public static final String RIGHT = "right";
        public static final String ROLE_RIGHT = "role_right";
        public static final String USER_ROLE = "user_role";
        public static final String CLIENT_ACCOUNT = "client_account";
     

        public static final String[] ORDERED_TABLES_FOR_CREATION = new String[]{USER, ROLE, RIGHT, ROLE_RIGHT ,USER_ROLE, CLIENT, ACCOUNT, CLIENT_ACCOUNT};
    }

    public static class Roles {
        public static final String ADMINISTRATOR = "administrator";
        public static final String EMPLOYEE = "employee";

        public static final String[] ROLES = new String[]{ADMINISTRATOR, EMPLOYEE};
    }

    public static class Rights {
    	public static final String CREATE_CLIENT= "create_client";
    	public static final String READ_CLIENT = "read_client";
    	public static final String UPDATE_CLIENT= "update_client";
    	
    	public static final String CREATE_ACCOUNT= "create_account";
    	public static final String READ_ACCOUNT = "read_account";
    	public static final String UPDATE_ACCOUNT= "update_account";
    	public static final String DELETE_ACCOUNT= "delete_account";
    	
    	public static final String TRANSFER_BETWEEN_ACCOUNTS = "transfer_between_accounts";
    	public static final String PROCESS_UTILITY_BILL= "process_utility_bill"; 
    	
        public static final String CREATE_EMPLOYEE = "create_employee";
        public static final String READ_EMPLOYEE = "read_employee";
        public static final String DELETE_EMPLOYEE = "delete_employee";
        public static final String UPDATE_EMPLOYEE = "update_employee";

        public static final String GENERATE_EMPLOYEE_ACTIVITY_REPORT="generate_employee_activity_report";
        public static final String[] RIGHTS = new String[]{CREATE_CLIENT, READ_CLIENT, UPDATE_CLIENT, CREATE_ACCOUNT,READ_ACCOUNT,UPDATE_ACCOUNT,DELETE_ACCOUNT,TRANSFER_BETWEEN_ACCOUNTS,PROCESS_UTILITY_BILL,CREATE_EMPLOYEE,READ_EMPLOYEE, UPDATE_EMPLOYEE, DELETE_EMPLOYEE};
    }

    
    public static Map<String, List<String>> getRolesRights() {
        Map<String, List<String>> ROLES_RIGHTS = new HashMap<>();
        for (String role : ROLES) {
            ROLES_RIGHTS.put(role, new ArrayList<>());
        }
        

        ROLES_RIGHTS.get(EMPLOYEE).addAll(Arrays.asList(CREATE_CLIENT, READ_CLIENT, UPDATE_CLIENT, CREATE_ACCOUNT,READ_ACCOUNT,UPDATE_ACCOUNT,DELETE_ACCOUNT,TRANSFER_BETWEEN_ACCOUNTS,PROCESS_UTILITY_BILL));

        ROLES_RIGHTS.get(ADMINISTRATOR).addAll(Arrays.asList(CREATE_EMPLOYEE,READ_EMPLOYEE, UPDATE_EMPLOYEE, DELETE_EMPLOYEE));
        

        return ROLES_RIGHTS;
    }

    public static Map<User, String> getPredefinedUsers() {
    	Map<User, String> user_role = new HashMap<>();
        User employee1 = new UserBuilder()
        		.setUsername("employee1@yahoo.com")
        		.setPassword("Employee1#")
        		.build();
        User employee2 = new UserBuilder()
        		.setUsername("employee2@yahoo.com")
        		.setPassword("Employee2#")
        		.build();
        User administrator1 = new UserBuilder()
        		.setUsername("administrator1@yahoo.com")
        		.setPassword("Administrator1#")
        		.build();
        User administrator2 = new UserBuilder()
        		.setUsername("administrator2@yahoo.com")
        		.setPassword("Administrator2#")
        		.build();
        
        user_role.put(employee1, EMPLOYEE);
        user_role.put(employee2, EMPLOYEE);
        user_role.put(administrator1, ADMINISTRATOR);
        user_role.put(administrator2, ADMINISTRATOR);
        return user_role;
    }

}
