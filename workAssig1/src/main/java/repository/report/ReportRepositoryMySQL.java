package repository.report;

import static database.Constants.Tables.REPORT;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Account;
import model.Client;
import model.Report;
import model.builders.ClientBuilder;
import model.builders.ReportBuilder;


public class ReportRepositoryMySQL implements ReportRepository {

	private final Connection connection;
    public ReportRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }
    
	 @Override
	    public boolean save(Report report) {
	    	try {
	            PreparedStatement insertStatement = connection
	                    .prepareStatement("INSERT INTO report values (null, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
	            insertStatement.setLong(1, report.getUserId());
	            insertStatement.setDate(2, new java.sql.Date(report.getDate().getTime()));
	            insertStatement.setLong(3, report.getClientId());
	            insertStatement.setString(4, report.getOperationType());
	            insertStatement.executeUpdate();

	            ResultSet rs = insertStatement.getGeneratedKeys();
	            rs.next();
	            long reportId = rs.getLong(1);
	            report.setId(reportId);
	            return true;
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return false;
	        }

	    }

	 private Report getReportFromResultSet(ResultSet rs) throws SQLException {
	        return new ReportBuilder()
	        		.setId(rs.getLong("id"))
	        		.setUserId(rs.getLong("user_id"))
	        		.setDate(rs.getDate("date"))
	        		.setClientId(rs.getLong("client_id"))
	        		.setOperationType(rs.getString("operation_type"))
	        		.build();
	        		
	    }

	@Override
	public List<Report> findReports(Long userId, Date startDate, Date endDate) {
		Statement statement;
        try {
        
            statement = connection.createStatement();
            String fetchSql = "Select * from report WHERE user_id= " + userId + " AND date >= " + startDate +" AND date<= " + endDate;
            ResultSet rs = statement.executeQuery(fetchSql);
            
            List<Report> reports = new ArrayList<>();
            while(rs.next()) {
            	Report report = getReportFromResultSet(rs);
            	reports.add(report);
            }
            return reports;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
	}

	
}
