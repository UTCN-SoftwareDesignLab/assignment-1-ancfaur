package repository.bill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import model.Bill;
import model.builders.BillBuilder;
import repository.EntityNotFoundException;

public class BillRepositoryMySQL implements BillRepository {

	private final Connection connection;

	public BillRepositoryMySQL(Connection connection) {
		this.connection = connection;
	}

	@Override
	public Bill findById(Long id) throws EntityNotFoundException {
		try {
			Statement statement = connection.createStatement();
			String sql = "Select * from bill where id=" + id;
			ResultSet rs = statement.executeQuery(sql);

			if (rs.next()) {
				return getBillFromResultSet(rs);
			} else {
				throw new EntityNotFoundException(id, Bill.class.getSimpleName());
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new EntityNotFoundException(id, Bill.class.getSimpleName());
		}
	}

	private Bill getBillFromResultSet(ResultSet rs) {
		try {
			String paid = rs.getString(4);
			boolean val;
			if (paid.equals("true")) val=true;
			else val=false;
			
			Bill bill = new BillBuilder().setBillId(rs.getLong(1)).setBillAmount(rs.getFloat(2))
					.setBillUtility(rs.getString(3)).setPaidStatus(val).build();
			return bill;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean save(Bill bill) {
		try {
			PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO bill values (null, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			String paid; 
			if (bill.isPaid()) paid="true";
			else paid="false";
			
			insertStatement.setFloat(1, bill.getAmount());
			insertStatement.setString(2, bill.getUtility());
			insertStatement.setString(3, paid);
			insertStatement.executeUpdate();

			ResultSet rs = insertStatement.getGeneratedKeys();
			rs.next();
			long billId = rs.getLong(1);
			bill.setId(billId);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateStatus(Bill bill) {
		try {
			PreparedStatement updateStatement = connection.prepareStatement("UPDATE bill SET paid=? WHERE id=?;");
			updateStatement.setString(1, "true");
			updateStatement.setLong(2, bill.getId());
			updateStatement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}
