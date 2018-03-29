package repository.client;
import model.Account;
import model.Client;
import model.User;
import model.builders.ClientBuilder;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static database.Constants.Tables.CLIENT;


public class ClientRepositoryMySQL implements ClientRepository {

    private final Connection connection;
    private final AccountRepository accountRepository;

    public ClientRepositoryMySQL(Connection connection, AccountRepository accountRepository) {
        this.connection = connection;
        this.accountRepository=accountRepository;
    }

    @Override
    public List<Client> findAll() {
    	 List<Client> clients = new ArrayList<>();
         try {
             Statement statement = connection.createStatement();
             String sql = "Select * from client";
             ResultSet rs = statement.executeQuery(sql);

             while (rs.next()) {
                 clients.add(getClientFromResultSet(rs));
             }
         } catch (SQLException e) {
             e.printStackTrace();
         }

         return clients;
    }

    @Override
    public Client findByCnp(String cnp) {
    	Statement statement;
        try {
            statement = connection.createStatement();
            String fetchClientSql = "Select * from " + CLIENT + " where `cnp`=\'" + cnp + "\'";
            ResultSet clientResultSet = statement.executeQuery(fetchClientSql);
            clientResultSet.next();
            Long clientId = clientResultSet.getLong("id");
            List<Account> accounts = accountRepository.findAccountsForClient(clientId);
            Client client = getClientFromResultSet(clientResultSet);
            return client;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    @Override
    public Client findById(Long id) {
    	Statement statement;
        try {
            statement = connection.createStatement();
            String fetchClientSql = "Select * from " + CLIENT + " where `id`=\'" + id+ "\'";
            ResultSet clientResultSet = statement.executeQuery(fetchClientSql);
            clientResultSet.next();
            List<Account> accounts = accountRepository.findAccountsForClient(id);
            Client client = getClientFromResultSet(clientResultSet);
            client.setAccounts(accounts);
            return client;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public boolean save(Client client) {
    	try {
            PreparedStatement insertClientStatement = connection
                    .prepareStatement("INSERT INTO client values (null, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertClientStatement.setString(1, client.getCnp());
            insertClientStatement.setString(2, client.getName());
            insertClientStatement.setString(3, client.getAddress());
            insertClientStatement.setLong(4, new Long(new Random().nextInt(500)));
            insertClientStatement.executeUpdate();

            ResultSet rs = insertClientStatement.getGeneratedKeys();
            rs.next();
            long clientId = rs.getLong(1);
            client.setId(clientId);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from client where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private Client getClientFromResultSet(ResultSet rs) throws SQLException {
        return new ClientBuilder()
                .setId(rs.getLong("id"))
                .setCnp(rs.getString("cnp"))
                .setName(rs.getString("name"))
                .setAddress(rs.getString("address"))
                .setIdCard(rs.getLong("idCard"))
                .build();
    }
    
    public boolean update(Client client) {
		try {
			PreparedStatement updateUserStatement = connection
					.prepareStatement("UPDATE client  SET cnp=?, name=?, address=? WHERE id=?;");
			updateUserStatement.setString(1, client.getCnp());
			updateUserStatement.setString(2, client.getName());
			updateUserStatement.setString(3, client.getAddress());
			updateUserStatement.setLong(4, client.getId());
			updateUserStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}