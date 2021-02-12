package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dev.reese.project1.entities.ReimbursementPayout;
import dev.reese.project1.util.JDBCConnection;

public class DBImpReimbursementPayoutDAO implements ReimbursementPayoutDAO{

	public static Connection conn = JDBCConnection.getConnection();
	
	@Override
	public boolean createReimbursementPayout(ReimbursementPayout rp) {
		try {
			String sql = "INSERT INTO reimbursement_payouts "
					+ "			(request_id, reimbursement_amount, for_year, status, notes)"
					+ "			VALUES (?, ?, ?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, Integer.toString(rp.getRequestId()));
			ps.setString(2, Double.toString(rp.getAmount()));
			ps.setString(3, Integer.toString(rp.getForYear()));
			ps.setString(4, rp.getStatus());
			ps.setString(5, rp.getNotes());
			ps.executeQuery();
			return true;
		}
		catch (SQLException exception) {
			exception.printStackTrace();
		}
		return false;
	}

	@Override
	public ReimbursementPayout getReimbursementPayout(int id) {
		try {
			String sql = "SELECT * FROM reimbursement_payouts WHERE id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, Integer.toString(id));
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				ReimbursementPayout rp = new ReimbursementPayout();
				rp.setId(rs.getInt("ID"));
				rp.setRequestId(rs.getInt("REQUEST_ID")); 
				rp.setAmount(rs.getBigDecimal("REIMBURSEMENT_AMOUNT").doubleValue());
				rp.setForYear(rs.getInt("FOR_YEAR"));
				rp.setStatus(rs.getString("STATUS"));
				rp.setDateOfPayment(rs.getTimestamp("DATE_OF_PAYMENT"));
				rp.setNotes(rs.getString("NOTES"));
				return rp;
			}
			return new ReimbursementPayout(0);
		}
		catch (SQLException exception) {
			exception.printStackTrace();
		}
		return null;
	}

	@Override
	public ReimbursementPayout getRequestsReimbursementPayout(int requestId) {
		try {
			String sql = "SELECT * FROM reimbursement_payouts WHERE request_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, Integer.toString(requestId));
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				ReimbursementPayout rp = new ReimbursementPayout();
				rp.setId(rs.getInt("ID"));
				rp.setRequestId(rs.getInt("REQUEST_ID")); 
				rp.setAmount(rs.getBigDecimal("REIMBURSEMENT_AMOUNT").doubleValue());
				rp.setForYear(rs.getInt("FOR_YEAR"));
				rp.setStatus(rs.getString("STATUS"));
				rp.setDateOfPayment(rs.getTimestamp("DATE_OF_PAYMENT"));
				rp.setNotes(rs.getString("NOTES"));
				return rp;
			}
			return new ReimbursementPayout(0);
		}
		catch (SQLException exception) {
			exception.printStackTrace();
		}
		return null;
	}

	@Override
	public List<ReimbursementPayout> getAllReimbursementPayout() {
		List<ReimbursementPayout> payouts = new ArrayList<ReimbursementPayout>();
		try {
			String sql = "SELECT * FROM reimbursement_payouts ";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				ReimbursementPayout rp = new ReimbursementPayout();
				rp.setId(rs.getInt("ID"));
				rp.setRequestId(rs.getInt("REQUEST_ID")); 
				rp.setAmount(rs.getBigDecimal("REIMBURSEMENT_AMOUNT").doubleValue());
				rp.setForYear(rs.getInt("FOR_YEAR"));
				rp.setStatus(rs.getString("STATUS"));
				rp.setDateOfPayment(rs.getTimestamp("DATE_OF_PAYMENT"));
				rp.setNotes(rs.getString("NOTES"));
				payouts.add(rp);
			}
		}
		catch (SQLException exception) {
			exception.printStackTrace();
		}
		return payouts;
	}

	@Override
	public List<ReimbursementPayout> getAllEmployeesReimbursementPayout(int employeeId) {
		List<ReimbursementPayout> payouts = new ArrayList<ReimbursementPayout>();
		try {
			String sql = "SELECT * FROM reimbursement_payouts";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				ReimbursementPayout rp = new ReimbursementPayout();
				rp.setId(rs.getInt("ID"));
				rp.setRequestId(rs.getInt("REQUEST_ID")); 
				rp.setAmount(rs.getBigDecimal("REIMBURSEMENT_AMOUNT").doubleValue());
				rp.setForYear(rs.getInt("FOR_YEAR"));
				rp.setStatus(rs.getString("STATUS"));
				rp.setDateOfPayment(rs.getTimestamp("DATE_OF_PAYMENT"));
				rp.setNotes(rs.getString("NOTES"));
				payouts.add(rp);
			}
		}
		catch (SQLException exception) {
			exception.printStackTrace();
		}
		
		
		
		return payouts;
	}

	@Override
	public List<ReimbursementPayout> getAllEmployeesReimbursementPayout(int employeeId, int year) {
		List<ReimbursementPayout> payouts = new ArrayList<ReimbursementPayout>();
		try {
			String sql = "SELECT * FROM reimbursement_payouts AS rp "
					+ "			WHERE rp.for_year = ? AND "
					+ "					rp.id in (SELECT trr.id FROM tuition_reimbursement_requests AS trr"
					+ "									WHERE trr.for_employee = ? )";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, Integer.toString(year));
			ps.setString(2, Integer.toString(employeeId));
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				ReimbursementPayout rp = new ReimbursementPayout();
				rp.setId(rs.getInt("ID"));
				rp.setRequestId(rs.getInt("REQUEST_ID")); 
				rp.setAmount(rs.getBigDecimal("REIMBURSEMENT_AMOUNT").doubleValue());
				rp.setForYear(rs.getInt("FOR_YEAR"));
				rp.setStatus(rs.getString("STATUS"));
				rp.setDateOfPayment(rs.getTimestamp("DATE_OF_PAYMENT"));
				rp.setNotes(rs.getString("NOTES"));
				payouts.add(rp);
			}
		}
		catch (SQLException exception) {
			exception.printStackTrace();
		}
		return payouts;
	}

	@Override
	public boolean updateReimbursementPayout(ReimbursementPayout rp) {
		try {
			String sql = "UPDATE reimbursement_payouts SET"
					+ "		request_id = ?, reimbursement_amount = ?, for_year = ?,"
					+ "		 status = ?, date_of_payment = ?,  notes = ?"
					+ "			WHERE id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, Integer.toString(rp.getRequestId()));
			ps.setString(2, Double.toString(rp.getAmount()));
			ps.setString(3, Integer.toString(rp.getForYear()));
			ps.setString(4, rp.getStatus());
			if (rp.getDateOfPayment() == null)
				ps.setNull(5, java.sql.Types.TIMESTAMP);
			else
				ps.setTimestamp(5, rp.getDateOfPayment());
			ps.setString(5, rp.getNotes());
			ps.executeQuery();
			return true;
		}
		catch (SQLException exception) {
			exception.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteReimbursementPayout(int id) {
		try {
			String sql = "DELETE FROM reimbursement_payouts WHERE id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeQuery();
			return true;
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
