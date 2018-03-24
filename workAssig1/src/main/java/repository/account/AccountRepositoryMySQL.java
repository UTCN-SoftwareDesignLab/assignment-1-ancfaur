package repository.account;

import model.Account;
import model.Role;
import model.builders.AccountBuilder;
import repository.EntityNotFoundException;

import static database.Constants.Tables.USER_ROLE;
import static database.Constants.Tables.CLIENT_ACCOUNT;
import static database.Constants.Tables.ROLE_RIGHT;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class AccountRepositoryMySQL implements AccountRepository {

    private final Connection connection;

    public AccountRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from account";
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                accounts.add(getAccountFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return accounts;
    }

    public Account findById(Long id) throws EntityNotFoundException {
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from account where id=" + id;
            ResultSet rs = statement.executeQuery(sql);

            if (rs.next()) {
                return getAccountFromResultSet(rs);
            } else {
                throw new EntityNotFoundException(id, Account.class.getSimpleName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new EntityNotFoundException(id, Account.class.getSimpleName());
        }
    }

    private Long saveAccount(Account account) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT INTO account values (null, ?, ?, ?)");
            insertStatement.setString(1, account.getType());
            insertStatement.setFloat(2, account.getBalance());
            insertStatement.setDate(3, new java.sql.Date(account.getCreationDate().getTime()));
            ResultSet rs=insertStatement.executeQuery();
            rs.next();
            return rs.getLong(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean addAccountToClient(Account account, Long clientId) {
    	Long accountId = saveAccount(account);
    	try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT IGNORE INTO " + CLIENT_ACCOUNT + " values (null, ?, ?)");
            insertStatement.setLong(1, clientId);
            insertStatement.setLong(2, accountId);
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
        		return false;
        }
    }
    
    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from account where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Account getAccountFromResultSet(ResultSet rs) throws SQLException {
        return new AccountBuilder()
                .setId(rs.getLong("id"))
                .setType(rs.getString("type"))
                .setBalance(rs.getFloat("balance"))
                .setCreationDate(new Date(rs.getDate("creationDate").getTime()))
                .build();
    }
    
    @Override
    public List<Account> findAccountsForClient(Long clientId) {
        try {
            List<Account> accounts = new ArrayList<>();
            Statement statement = connection.createStatement();
            String fetchAccountSql = "Select * from " + CLIENT_ACCOUNT + " where `client_id`=\'" + clientId + "\'";
            ResultSet userRoleResultSet = statement.executeQuery(fetchAccountSql);
            while (userRoleResultSet.next()) {
                long accountId = userRoleResultSet.getLong("role_id");
                accounts.add(findById(accountId));
            }
            return accounts;
        } catch (SQLException | EntityNotFoundException e) {
        return null;
    }
    }
}
