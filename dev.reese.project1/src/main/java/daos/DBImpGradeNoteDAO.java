package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dev.reese.project1.entities.GradeNote;
import dev.reese.project1.util.JDBCConnection;

public class DBImpGradeNoteDAO implements GradeNoteDAO{
	
	public static Connection conn = JDBCConnection.getConnection();

	@Override
	public boolean createGradeNote(GradeNote gn) {
		try {
			String sql = "INSERT INTO grade_notes "
					+ "			(grade_submittion_id, note_subject, note, added_at, added_by)"
					+ "			VALUES (?, ?, ?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, Integer.toString(gn.getGradeSubmissionId()));
			ps.setString(2, gn.getSubject());
			ps.setString(3, gn.getNote());
			ps.setTimestamp(4, gn.getAddedAt());
			ps.setString(5, Integer.toString(gn.getAddedBy()));
			ps.executeQuery();
			return true;
		}
		catch (SQLException exception) {
			exception.printStackTrace();
		}
		return false;
	}

	@Override
	public GradeNote getGradeNote(int id) {
		try {
			String sql = "SELECT * FROM grade_notes WHERE id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, Integer.toString(id));
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				GradeNote gn = new GradeNote();
				gn.setId(rs.getInt("ID"));
				gn.setGradeSubmissionId(rs.getInt("GRADE_SUBMITTION_ID"));
				gn.setSubject(rs.getString("NOTE_SUBJECT"));
				gn.setNote(rs.getString("NOTE"));
				gn.setAddedAt(rs.getTimestamp("ADDED_AT"));
				gn.setAddedBy(rs.getInt("ADDED_BY"));
				return gn;
			}
			return new GradeNote(0);
		}
		catch (SQLException exception) {
			exception.printStackTrace();
		}
		return null;
	}
	
	
	public GradeNote getGradeNote(int gradeId, java.sql.Timestamp time) {
		try {
			String sql = "SELECT * FROM grade_notes WHERE grade_submittion_id = ? AND added_at = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, Integer.toString(gradeId));
			ps.setTimestamp(2, time);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				GradeNote gn = new GradeNote();
				gn.setId(rs.getInt("ID"));
				gn.setGradeSubmissionId(rs.getInt("GRADE_SUBMITTION_ID"));
				gn.setSubject(rs.getString("NOTE_SUBJECT"));
				gn.setNote(rs.getString("NOTE"));
				gn.setAddedAt(rs.getTimestamp("ADDED_AT"));
				gn.setAddedBy(rs.getInt("ADDED_BY"));
				return gn;
			}
			return new GradeNote(0);
		}
		catch (SQLException exception) {
			exception.printStackTrace();
		}
		return null;
	}
	

	@Override
	public List<GradeNote> getAllGradeNotes() {
		List<GradeNote> notes = new ArrayList<GradeNote>();
		try {
			String sql = "SELECT * FROM grade_notes";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				GradeNote gn = new GradeNote();
				gn.setId(rs.getInt("ID"));
				gn.setGradeSubmissionId(rs.getInt("GRADE_SUBMITTION_ID"));
				gn.setSubject(rs.getString("NOTE_SUBJECT"));
				gn.setNote(rs.getString("NOTE"));
				gn.setAddedAt(rs.getTimestamp("ADDED_AT"));
				gn.setAddedBy(rs.getInt("ADDED_BY"));
				notes.add(gn);
			}
		}
		catch (SQLException exception) {
			exception.printStackTrace();
		}
		return notes;
	}

	@Override
	public List<GradeNote> getAllNotesForGrade(int gradeId) {
		List<GradeNote> notes = new ArrayList<GradeNote>();
		try {
			String sql = "SELECT * FROM grade_notes WHERE grade_submittion_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, Integer.toString(gradeId));
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				GradeNote gn = new GradeNote();
				gn.setId(rs.getInt("ID"));
				gn.setGradeSubmissionId(rs.getInt("GRADE_SUBMITTION_ID"));
				gn.setSubject(rs.getString("NOTE_SUBJECT"));
				gn.setNote(rs.getString("NOTE"));
				gn.setAddedAt(rs.getTimestamp("ADDED_AT"));
				gn.setAddedBy(rs.getInt("ADDED_BY"));
				notes.add(gn);
			}
		}
		catch (SQLException exception) {
			exception.printStackTrace();
		}
		return notes;
	}

	@Override
	public boolean updateGradeNote(GradeNote gn) {
		try {
			String sql = "UPDATE grade_notes SET"
					+ "			grade_submittion_id = ?, note_subject = ? , note = ?, added_at = ?, added_by = ?"
					+ "			WHERE id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, Integer.toString(gn.getGradeSubmissionId()));
			ps.setString(2, gn.getSubject());
			ps.setString(3, gn.getNote());
			ps.setTimestamp(4, gn.getAddedAt());
			ps.setString(5, Integer.toString(gn.getAddedBy()));
			ps.setString(6, Integer.toString(gn.getId()));
			ps.executeQuery();
			return true;
		}
		catch (SQLException exception) {
			exception.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteGradeNote(int id) {
		try {
			String sql = "DELETE FROM grade_notes WHERE id = ?";
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
