package repository.client;
import model.Account;
import model.Client;
import model.builders.ClientBuilder;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static database.Constants.Tables.CLIENT;


public class ClientRepositoryMySQL implements ClientRepository {

    private final Connection connection;
    private final AccountRepository accountRepository;

    public ClientRepositoryMySQL(Connection connection) {
        this.connection = connection;
        accountRepository= new AccountRepositoryMySQL(connection);
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
            Client client = new ClientBuilder()
            		.setId(clientId)
            		.setCnp(clientResultSet.getString("cnp"))
            		.setName(clientResultSet.getString("name"))
            		.setAddress(clientResultSet.getString("address"))
            		.setIdCard(clientResultSet.getLong("idCard"))
            		.setAccounts(accounts)
            		.build();
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
                    .prepareStatement("INSERT INTO client values (null, ?, ?, ?, ?)");
            insertClientStatement.setString(1, client.getCnp());
            insertClientStatement.setString(2, client.getName());
            insertClientStatement.setString(3, client.getAddress());
            insertClientStatement.setLong(4, client.getIdCard());
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

}