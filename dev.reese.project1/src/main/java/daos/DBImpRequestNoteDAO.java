package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dev.reese.project1.entities.RequestNote;
import dev.reese.project1.util.JDBCConnection;

public class DBImpRequestNoteDAO implements RequestNoteDAO{
	
	public static Connection conn = JDBCConnection.getConnection();

	@Override
	public boolean createRequestNote(RequestNote rn) {
		try {
			String sql = "INSERT INTO request_notes "
					+ "			(request_id, note_subject, note, added_at, added_by)"
					+ "			VALUES (?, ?, ?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, Integer.toString(rn.getRequestId()));
			ps.setString(2, rn.getSubject());
			ps.setString(3, rn.getNote());
			ps.setTimestamp(4, rn.getAddedAt());
			ps.setString(5, Integer.toString(rn.getAddedBy()));
			ps.executeQuery();
			return true;
		}
		catch (SQLException exception) {
			exception.printStackTrace();
		}
		return false;
	}

	@Override
	public RequestNote getRequestNote(int id) {
		try {
			String sql = "SELECT * FROM request_notes WHERE id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, Integer.toString(id));
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				RequestNote rn = new RequestNote();
				rn.setId(rs.getInt("ID"));
				rn.setRequestId(rs.getInt("REQUEST_ID"));
				rn.setSubject(rs.getString("NOTE_SUBJECT"));
				rn.setNote(rs.getString("NOTE"));
				rn.setAddedAt(rs.getTimestamp("ADDED_AT"));
				rn.setAddedBy(rs.getInt("ADDED_BY"));
//				d.setDepartmentName(rs.getString("DEPARTMENT_NAME"));
//				d.setHeadOfDepartmentId(rs.getInt("HEAD_OF_DEPARTMENT"));
				return rn;
			}
			return new RequestNote(0);
		}
		catch (SQLException exception) {
			exception.printStackTrace();
		}
		return null;
	}
	
	public RequestNote getRequestNote(int requestId, int addedBy, java.sql.Timestamp time) {
		try {
			String sql = "SELECT * FROM request_notes WHERE request_id = ? AND added_by = ? AND added_at = ? ";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, Integer.toString(requestId));
			ps.setString(2, Integer.toString(addedBy));
			ps.setTimestamp(3, time);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				RequestNote rn = new RequestNote();
				rn.setId(rs.getInt("ID"));
				rn.setRequestId(rs.getInt("REQUEST_ID"));
				rn.setSubject(rs.getString("NOTE_SUBJECT"));
				rn.setNote(rs.getString("NOTE"));
				rn.setAddedAt(rs.getTimestamp("ADDED_AT"));
				rn.setAddedBy(rs.getInt("ADDED_BY"));
//				d.setDepartmentName(rs.getString("DEPARTMENT_NAME"));
//				d.setHeadOfDepartmentId(rs.getInt("HEAD_OF_DEPARTMENT"));
				return rn;
			}
			return new RequestNote(0);
		}
		catch (SQLException exception) {
			exception.printStackTrace();
		}
		return null;
	}

	@Override
	public List<RequestNote> getAllRequestNote() {
		List<RequestNote> notes = new ArrayList<RequestNote>();
		try {
			String sql = "SELECT * FROM request_notes";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				RequestNote rn = new RequestNote();
				rn.setId(rs.getInt("ID"));
				rn.setRequestId(rs.getInt("REQUEST_ID"));
				rn.setSubject(rs.getString("NOTE_SUBJECT"));
				rn.setNote(rs.getString("NOTE"));
				rn.setAddedAt(rs.getTimestamp("ADDED_AT"));
				rn.setAddedBy(rs.getInt("ADDED_BY"));
				notes.add(rn);
			}
		}
		catch (SQLException exception) {
			exception.printStackTrace();
		}
		return notes;
	}

	@Override
	public List<RequestNote> getAllRequestsRequestNote(int requestId) {
		List<RequestNote> notes = new ArrayList<RequestNote>();
		try {
			String sql = "SELECT * FROM request_notes where request_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, Integer.toString(requestId));
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				RequestNote rn = new RequestNote();
				rn.setId(rs.getInt("ID"));
				rn.setRequestId(rs.getInt("REQUEST_ID"));
				rn.setSubject(rs.getString("NOTE_SUBJECT"));
				rn.setNote(rs.getString("NOTE"));
				rn.setAddedAt(rs.getTimestamp("ADDED_AT"));
				rn.setAddedBy(rs.getInt("ADDED_BY"));
				notes.add(rn);
			}
		}
		catch (SQLException exception) {
			exception.printStackTrace();
		}
		return notes;
	}

	@Override
	public boolean updateRequestNote(RequestNote rn) {
		try {
			String sql = "UPDATE request_notes SET"
					+ "			request_id = ?, note_subject = ? , note = ?, added_at = ?, added_by = ?"
					+ "			WHERE id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, Integer.toString(rn.getRequestId()));
			ps.setString(2, rn.getSubject());
			ps.setString(3, rn.getNote());
			ps.setTimestamp(4, rn.getAddedAt());
			ps.setString(5, Integer.toString(rn.getAddedBy()));
			ps.setString(6, Integer.toString(rn.getId()));
			ps.executeQuery();
			return true;
		}
		catch (SQLException exception) {
			exception.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean deleteRequestNote(int id) {
		try {
			String sql = "DELETE FROM request_notes WHERE id = ?";
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
