package repository.report;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Report;
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
		List<Report> reports = new ArrayList<>();
		System.out.println("Search for reports");
        try {
        	java.sql.Date start = new java.sql.Date(startDate.getTime());
        	java.sql.Date end = new java.sql.Date(endDate.getTime());
        	Statement statement = connection.createStatement();
			String fetchReportsSql = "Select * from report where user_id= " + userId.toString() + " and date >= '" +start.toString() +"' and date <= '"+end.toString()+"'";
			ResultSet rs= statement.executeQuery(fetchReportsSql);
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


    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from report where id >= 0";
            statement.executeUpdate(sql);
            String sqlResetIncrement = "ALTER TABLE report AUTO_INCREMENT = 1";
            statement.executeUpdate(sqlResetIncrement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
}
