package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dev.reese.project1.entities.SubmittedGrade;
import dev.reese.project1.util.JDBCConnection;

public class DBImpSubmittedGradeDAO implements SubmittedGradeDAO{
	
	public static Connection conn = JDBCConnection.getConnection();

	@Override
	public boolean createSubmittedGrade(SubmittedGrade sg) {
		try {
			String sql = "INSERT INTO submitted_grades "
					+ "			(request_id, employee_reported_score, submitted_at)"
					+ "			VALUES (?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, Integer.toString(sg.getRequestId()));
			ps.setString(2, Integer.toString(sg.getEmployeeSelfReportedScore()));
			ps.setTimestamp(3, sg.getSubmittedAt());
			ps.executeQuery();
			return true;
		}
		catch (SQLException exception) {
			exception.printStackTrace();
		}
		return false;
	}

	@Override
	public SubmittedGrade getSubmittedGrade(int id) {
		try {
			String sql = "SELECT * FROM submitted_grades WHERE id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, Integer.toString(id));
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				SubmittedGrade sg = new SubmittedGrade();
				sg.setId(rs.getInt("ID"));
				sg.setRequestId(rs.getInt("REQUEST_ID")); //(rs.getInt(""));
				sg.setEmployeeSelfReportedScore(rs.getInt("EMPLOYEE_REPORTED_SCORE")); //(rs.getString("NOTE_SUBJECT"));
				sg.setSubmittedAt(rs.getTimestamp("SUBMITTED_AT"));
				sg.setReviewedBy(rs.getInt("REVIEWED_BY"));
				sg.setReviewedAt(rs.getTimestamp("REVIEWED_AT"));
				String passed = rs.getString("PASSED");
				if(passed == null || passed.equals("F"))
					sg.setPassed(false);
				else
					sg.setPassed(true);
				return sg;
			}
			return new SubmittedGrade(0);
		}
		catch (SQLException exception) {
			exception.printStackTrace();
		}
		return null;
	}

	@Override
	public SubmittedGrade getRequestsSubmittedGrade(int requestId) {
		try {
			String sql = "SELECT * FROM submitted_grades WHERE request_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, Integer.toString(requestId));
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				SubmittedGrade sg = new SubmittedGrade();
				sg.setId(rs.getInt("ID"));
				sg.setRequestId(rs.getInt("REQUEST_ID")); //(rs.getInt(""));
				sg.setEmployeeSelfReportedScore(rs.getInt("EMPLOYEE_REPORTED_SCORE")); //(rs.getString("NOTE_SUBJECT"));
				sg.setSubmittedAt(rs.getTimestamp("SUBMITTED_AT"));
				sg.setReviewedBy(rs.getInt("REVIEWED_BY"));
				sg.setReviewedAt(rs.getTimestamp("REVIEWED_AT"));
				String passed = rs.getString("PASSED");
				if(passed == null || passed.equals("F"))
					sg.setPassed(false);
				else
					sg.setPassed(true);
				return sg;
			}
			return new SubmittedGrade(0);
		}
		catch (SQLException exception) {
			exception.printStackTrace();
		}
		return null;
	}

	@Override
	public List<SubmittedGrade> getAllSubmittedGrade() {
		List<SubmittedGrade> grades = new ArrayList<SubmittedGrade>();
		try {
			String sql = "SELECT * FROM submitted_grades ";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				SubmittedGrade sg = new SubmittedGrade();
				sg.setId(rs.getInt("ID"));
				sg.setRequestId(rs.getInt("REQUEST_ID")); //(rs.getInt(""));
				sg.setEmployeeSelfReportedScore(rs.getInt("EMPLOYEE_REPORTED_SCORE")); //(rs.getString("NOTE_SUBJECT"));
				sg.setSubmittedAt(rs.getTimestamp("SUBMITTED_AT"));
				sg.setReviewedBy(rs.getInt("REVIEWED_BY"));
				sg.setReviewedAt(rs.getTimestamp("REVIEWED_AT"));
				String passed = rs.getString("PASSED");
				if(passed == null || passed.equals("F"))
					sg.setPassed(false);
				else
					sg.setPassed(true);
				grades.add(sg);
			}
		}
		catch (SQLException exception) {
			exception.printStackTrace();
		}
		return grades;
	}

	@Override
	public boolean updateSubmittedGrade(SubmittedGrade sg) {
		try {
			String sql = "UPDATE submitted_grades SET"
					+ "			request_id = ?, employee_reported_score = ?, submitted_at = ?, reviewed_by = ?"
					+ "			reviewed_at = ?, passed = ?"
					+ "			WHERE id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, Integer.toString(sg.getRequestId()));
			ps.setString(2, Integer.toString(sg.getEmployeeSelfReportedScore()));
			ps.setTimestamp(3, sg.getSubmittedAt());
			if(sg.getReviewedBy() == 0) 
				ps.setNull(4, java.sql.Types.NUMERIC);
			else
				ps.setInt(4, sg.getReviewedBy());
			if(sg.getReviewedAt() == null) {
				ps.setNull(5, java.sql.Types.TIMESTAMP);
				ps.setNull(6, java.sql.Types.VARCHAR);
			}
			else {
				ps.setTimestamp(5, sg.getReviewedAt());
				ps.setString(6, (sg.isPassed() ? "T" : "F"));
			}
			ps.setString(7, Integer.toString(sg.getId()));
			ps.executeQuery();
			return true;
		}
		catch (SQLException exception) {
			exception.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteSubmittedGrade(int id) {
		try {
			String sql = "DELETE FROM submitted_grades WHERE id = ?";
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
