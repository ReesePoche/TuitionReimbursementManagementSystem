package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dev.reese.project1.entities.ReimbursementMessage;
import dev.reese.project1.util.JDBCConnection;

public class DBImpReimbursementMessageDAO implements ReimbursementMessageDAO{

	public static Connection conn = JDBCConnection.getConnection();
	@Override
	public boolean createReimbursementMessage(ReimbursementMessage rm) {
		try {
			String sql = "INSERT INTO reimbursement_messages "
					+ "			(request_id, type_of_message, receiver_id, sender_id, sender_notes, "
					+ "				status, time_created)"
					+ "			VALUES (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, Integer.toString(rm.getRequestId()));
			ps.setString(2, rm.getTypeOfMessage());
			ps.setString(3, Integer.toString(rm.getRecieverId()));
			if(rm.getSenderId() == 0)
				ps.setNull(4, java.sql.Types.NUMERIC);
			else
				ps.setString(4, Integer.toString(rm.getSenderId()));
			if(rm.getSenderNotes() == null)
				ps.setNull(5, java.sql.Types.VARCHAR);
			else
				ps.setString(5, rm.getSenderNotes());
			ps.setString(6, rm.getStatus());
			ps.setTimestamp(7, rm.getCreatedAt());
			ps.executeQuery();
			return true;
		}
		catch (SQLException exception) {
			exception.printStackTrace();
		}
		return false;
	}

	@Override
	public ReimbursementMessage getReimbursementMessage(int id) {
		try {
			String sql = "SELECT * FROM reimbursement_messages WHERE id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, Integer.toString(id));
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				ReimbursementMessage rm = new ReimbursementMessage();
				rm.setId(rs.getInt("ID"));
				rm.setRequestId(rs.getInt("REQUEST_ID"));
				rm.setTypeOfMessage(rs.getString("TYPE_OF_MESSAGE"));
				rm.setRecieverId(rs.getInt("RECEIVER_ID"));
				rm.setSenderId(rs.getInt("SENDER_ID"));
				rm.setSenderNotes(rs.getString("SENDER_NOTES"));
				rm.setStatus(rs.getString("STATUS"));
				rm.setResponse(rs.getString("RESPONSE"));
				rm.setCreatedAt(rs.getTimestamp("TIME_CREATED"));
				rm.setRespondedAt(rs.getTimestamp("TIME_OF_RESPONSE"));
				return rm;
			}
			return new ReimbursementMessage(0);
		}
		catch (SQLException exception) {
			exception.printStackTrace();
		}
		return null;
	}
	
	public ReimbursementMessage getReimbursementMessage(int requestId, int recieverId, java.sql.Timestamp createdAt) {
		try {
			String sql = "SELECT * FROM reimbursement_messages WHERE request_id = ? AND receiver_id = ? AND time_created = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, Integer.toString(requestId));
			ps.setString(2, Integer.toString(recieverId));
			ps.setTimestamp(3, createdAt);
			//ps.setTimestamp(3, at);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				ReimbursementMessage rm = new ReimbursementMessage();
				rm.setId(rs.getInt("ID"));
				rm.setRequestId(rs.getInt("REQUEST_ID"));
				rm.setTypeOfMessage(rs.getString("TYPE_OF_MESSAGE"));
				rm.setRecieverId(rs.getInt("RECEIVER_ID"));
				rm.setSenderId(rs.getInt("SENDER_ID"));
				rm.setSenderNotes(rs.getString("SENDER_NOTES"));
				rm.setStatus(rs.getString("STATUS"));
				rm.setResponse(rs.getString("RESPONSE"));
				rm.setCreatedAt(rs.getTimestamp("TIME_CREATED"));
				rm.setRespondedAt(rs.getTimestamp("TIME_OF_RESPONSE"));
				return rm;
			}
			return new ReimbursementMessage(0);
		}
		catch (SQLException exception) {
			exception.printStackTrace();
		}
		return null;
	}

	@Override
	public List<ReimbursementMessage> getAllReimbursementMessage() {
		List<ReimbursementMessage> messages = new ArrayList<ReimbursementMessage>();
		try {
			String sql = "SELECT * FROM reimbursement_messages";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				ReimbursementMessage rm = new ReimbursementMessage();
				rm.setId(rs.getInt("ID"));
				rm.setRequestId(rs.getInt("REQUEST_ID"));
				rm.setTypeOfMessage(rs.getString("TYPE_OF_MESSAGE"));
				rm.setRecieverId(rs.getInt("RECEIVER_ID"));
				rm.setSenderId(rs.getInt("SENDER_ID"));
				rm.setSenderNotes(rs.getString("SENDER_NOTES"));
				rm.setStatus(rs.getString("STATUS"));
				rm.setResponse(rs.getString("RESPONSE"));
				rm.setCreatedAt(rs.getTimestamp("TIME_CREATED"));
				rm.setRespondedAt(rs.getTimestamp("TIME_OF_RESPONSE"));
				messages.add(rm);
			}
		}
		catch (SQLException exception) {
			exception.printStackTrace();
		}
		return messages;
	}

	@Override
	public List<ReimbursementMessage> getRequestsReimbursementMessages(int requestId) {
		List<ReimbursementMessage> messages = new ArrayList<ReimbursementMessage>();
		try {
			String sql = "SELECT * FROM reimbursement_messages WHERE request_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, Integer.toString(requestId));
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				ReimbursementMessage rm = new ReimbursementMessage();
				rm.setId(rs.getInt("ID"));
				rm.setRequestId(rs.getInt("REQUEST_ID"));
				rm.setTypeOfMessage(rs.getString("TYPE_OF_MESSAGE"));
				rm.setRecieverId(rs.getInt("RECEIVER_ID"));
				rm.setSenderId(rs.getInt("SENDER_ID"));
				rm.setSenderNotes(rs.getString("SENDER_NOTES"));
				rm.setStatus(rs.getString("STATUS"));
				rm.setResponse(rs.getString("RESPONSE"));
				rm.setCreatedAt(rs.getTimestamp("TIME_CREATED"));
				rm.setRespondedAt(rs.getTimestamp("TIME_OF_RESPONSE"));
				messages.add(rm);
			}
		}
		catch (SQLException exception) {
			exception.printStackTrace();
		}
		return messages;
	}

	@Override
	public List<ReimbursementMessage> getEmployeeRecievedReimbursementMessage(int employeeId) {
		List<ReimbursementMessage> messages = new ArrayList<ReimbursementMessage>();
		try {
			String sql = "SELECT * FROM reimbursement_messages WHERE receiver_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, Integer.toString(employeeId));
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				ReimbursementMessage rm = new ReimbursementMessage();
				rm.setId(rs.getInt("ID"));
				rm.setRequestId(rs.getInt("REQUEST_ID"));
				rm.setTypeOfMessage(rs.getString("TYPE_OF_MESSAGE"));
				rm.setRecieverId(rs.getInt("RECEIVER_ID"));
				rm.setSenderId(rs.getInt("SENDER_ID"));
				rm.setSenderNotes(rs.getString("SENDER_NOTES"));
				rm.setStatus(rs.getString("STATUS"));
				rm.setResponse(rs.getString("RESPONSE"));
				rm.setCreatedAt(rs.getTimestamp("TIME_CREATED"));
				rm.setRespondedAt(rs.getTimestamp("TIME_OF_RESPONSE"));
				messages.add(rm);
			}
		}
		catch (SQLException exception) {
			exception.printStackTrace();
		}
		System.out.println("from the dao the messagea are of length " + messages.size());
		return messages;
	}

	@Override
	public List<ReimbursementMessage> getEmployeeSentReimbursementMessage(int employeeId) {
		List<ReimbursementMessage> messages = new ArrayList<ReimbursementMessage>();
		try {
			String sql = "SELECT * FROM reimbursement_messages WHERE sender_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, Integer.toString(employeeId));
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				ReimbursementMessage rm = new ReimbursementMessage();
				rm.setId(rs.getInt("ID"));
				rm.setRequestId(rs.getInt("REQUEST_ID"));
				rm.setTypeOfMessage(rs.getString("TYPE_OF_MESSAGE"));
				rm.setRecieverId(rs.getInt("RECEIVER_ID"));
				rm.setSenderId(rs.getInt("SENDER_ID"));
				rm.setSenderNotes(rs.getString("SENDER_NOTES"));
				rm.setStatus(rs.getString("STATUS"));
				rm.setResponse(rs.getString("RESPONSE"));
				rm.setCreatedAt(rs.getTimestamp("TIME_CREATED"));
				rm.setRespondedAt(rs.getTimestamp("TIME_OF_RESPONSE"));
				messages.add(rm);
			}
		}
		catch (SQLException exception) {
			exception.printStackTrace();
		}
		return messages;
	}

	@Override
	public boolean updateReimbursementMessage(ReimbursementMessage rm) {
		try {
			String sql = "UPDATE reimbursement_messages SET"
					+ "			request_id = ?, type_of_message = ?, receiver_id = ?, sender_id = ?, sender_notes = ?, "
					+ "				status = ?, response = ?, time_created = ?, time_of_response = ?"
					+ "			WHERE id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, Integer.toString(rm.getRequestId()));
			ps.setString(2, rm.getTypeOfMessage());
			ps.setString(3, Integer.toString(rm.getRecieverId()));
			if(rm.getSenderId() == 0)
				ps.setNull(4, java.sql.Types.NUMERIC);
			else
				ps.setString(4, Integer.toString(rm.getSenderId()));
			if(rm.getSenderNotes() == null)
				ps.setNull(5, java.sql.Types.VARCHAR);
			else
				ps.setString(5, rm.getSenderNotes());
			ps.setString(6, rm.getStatus());
			if(rm.getResponse() == null)
				ps.setNull(7, java.sql.Types.VARCHAR);
			else
				ps.setString(7, rm.getResponse());
			ps.setTimestamp(8, rm.getCreatedAt());
			if(rm.getRespondedAt() == null)
				ps.setNull(9, java.sql.Types.TIMESTAMP);
			else
				ps.setTimestamp(9, rm.getRespondedAt());
			ps.setString(10, Integer.toString(rm.getId()));
			ps.executeQuery();
			return true;
		}
		catch (SQLException exception) {
			exception.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteReimbursementMessage(int id) {
		try {
			String sql = "DELETE FROM reimbursement_messages WHERE id = ?";
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
