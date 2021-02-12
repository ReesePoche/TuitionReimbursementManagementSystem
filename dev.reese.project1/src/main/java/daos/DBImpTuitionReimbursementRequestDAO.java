package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import dev.reese.project1.entities.TuitionReimbursementRequest;
import dev.reese.project1.util.JDBCConnection;

public class DBImpTuitionReimbursementRequestDAO implements TuitionReimbursementRequestDAO{

	public static Connection conn = JDBCConnection.getConnection();
	@Override
	public boolean createTuitionReimbursementRequest(TuitionReimbursementRequest trr) {
		try {
			String sql = "INSERT INTO tuition_reimbursement_requests "
					+ "			(for_employee, event_type, total_costs, event_description, event_location, start_datetime_of_event,"
					+ "			 work_related_justification, grade_format, score_required, hours_of_work_missed, "
					+ "			date_completed_and_submitted, status_of_request, waiting_on_message, has_supervisor_approval,"
					+ "			  has_department_head_approval, has_benco_approval"
					+ "			 )  "
					+ "			VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, Integer.toString(trr.getForEmployee()));
			ps.setString(2, trr.getEventType());
			ps.setString(3, Double.toString(trr.getTotalCost()));
			ps.setString(4, trr.getEventDescription());
			ps.setString(5, trr.getEventLocation());
			ps.setTimestamp(6, trr.getStartDateTimeOfEvent());
			ps.setString(7, trr.getWorkRelatedJustification());
			ps.setString(8, Integer.toString(trr.getGradeFormat()));
			ps.setString(9, Integer.toString(trr.getScoreRequired()));
			if(trr.getHoursOfWorkMissed() < 0 )
				ps.setNull(10, java.sql.Types.NUMERIC);
			else
				ps.setString(10, Double.toString(trr.getHoursOfWorkMissed()));
			ps.setTimestamp(11, trr.getDateSubmitted());
			ps.setString(12, trr.getstatusOfRequest());
			if(trr.getWaitingOnMessageId() == 0)
				ps.setNull(13, java.sql.Types.NUMERIC);
			else
				ps.setString(13, Integer.toString(trr.getWaitingOnMessageId()));
			ps.setString(14, (trr.isHasSupervisorApproval() ? "T" : "F"));
			ps.setString(15, (trr.isHasDepartmentHeadApproval() ? "T" : "F"));
			ps.setString(16, (trr.isHasBenCoApproval() ? "T" : "F"));
			ps.executeQuery();
			return true;
		}
		catch (SQLException exception) {
			exception.printStackTrace();
		}
		return false;
	}

	@Override
	public TuitionReimbursementRequest getTuitionReimbursementRequest(int id) {
		try {
			String sql = "SELECT * FROM tuition_reimbursement_requests WHERE id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, Integer.toString(id));
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				TuitionReimbursementRequest trr = new TuitionReimbursementRequest();
				trr.setId(rs.getInt("ID"));
				trr.setForEmployee(rs.getInt("FOR_EMPLOYEE"));
				trr.setEventType(rs.getString("EVENT_TYPE"));
				trr.setTotalCost(rs.getBigDecimal("TOTAL_COSTS").doubleValue());
				trr.setEventDescription(rs.getString("EVENT_DESCRIPTION"));
				trr.setEventLocation(rs.getString("EVENT_LOCATION"));
				trr.setStartDateTimeOfEvent(rs.getTimestamp("START_DATETIME_OF_EVENT"));
				trr.setHoursOfWorkMissed(rs.getBigDecimal("HOURS_OF_WORK_MISSED").doubleValue());
				trr.setWorkRelatedJustification(rs.getString("WORK_RELATED_JUSTIFICATION"));
				trr.setGradeFormat(rs.getInt("GRADE_FORMAT"));
				trr.setScoreRequired(rs.getInt("SCORE_REQUIRED"));
				trr.setDateSubmitted(rs.getTimestamp("DATE_COMPLETED_AND_SUBMITTED"));
				trr.setstatusOfRequest(rs.getString("STATUS_OF_REQUEST"));
				trr.setWaitingOnMessageId(rs.getInt("WAITING_ON_MESSAGE"));
				trr.setHasSupervisorApproval(rs.getString("HAS_SUPERVISOR_APPROVAL") == "T");
				trr.setHasDepartmentHeadApproval(rs.getString("HAS_DEPARTMENT_HEAD_APPROVAL") == "T");
				trr.setHasBenCoApproval(rs.getString("HAS_BENCO_APPROVAL") == "T");
				return trr;
			}
			return new TuitionReimbursementRequest(0);
		}
		catch (SQLException exception) {
			exception.printStackTrace();
		}
		return null;
	}
	
	
	public TuitionReimbursementRequest getTuitionReimbursementRequest(java.sql.Timestamp ts, int employeeId) {
		try {
			String sql = "SELECT * FROM tuition_reimbursement_requests "
					+ "		WHERE for_employee = ? AND date_completed_and_submitted = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, Integer.toString(employeeId));
			ps.setTimestamp(2, ts);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				TuitionReimbursementRequest trr = new TuitionReimbursementRequest();
				trr.setId(rs.getInt("ID"));
				trr.setForEmployee(rs.getInt("FOR_EMPLOYEE"));
				trr.setEventType(rs.getString("EVENT_TYPE"));
				trr.setTotalCost(rs.getBigDecimal("TOTAL_COSTS").doubleValue());
				trr.setEventDescription(rs.getString("EVENT_DESCRIPTION"));
				trr.setEventLocation(rs.getString("EVENT_LOCATION"));
				trr.setStartDateTimeOfEvent(rs.getTimestamp("START_DATETIME_OF_EVENT"));
				trr.setHoursOfWorkMissed(rs.getBigDecimal("HOURS_OF_WORK_MISSED").doubleValue());
				trr.setWorkRelatedJustification(rs.getString("WORK_RELATED_JUSTIFICATION"));
				trr.setGradeFormat(rs.getInt("GRADE_FORMAT"));
				trr.setScoreRequired(rs.getInt("SCORE_REQUIRED"));
				trr.setDateSubmitted(rs.getTimestamp("DATE_COMPLETED_AND_SUBMITTED"));
				trr.setstatusOfRequest(rs.getString("STATUS_OF_REQUEST"));
				trr.setWaitingOnMessageId(rs.getInt("WAITING_ON_MESSAGE"));
				trr.setHasSupervisorApproval(rs.getString("HAS_SUPERVISOR_APPROVAL") == "T");
				trr.setHasDepartmentHeadApproval(rs.getString("HAS_DEPARTMENT_HEAD_APPROVAL") == "T");
				trr.setHasBenCoApproval(rs.getString("HAS_BENCO_APPROVAL") == "T");
				return trr;
			}
			return new TuitionReimbursementRequest(0);
		}
		catch (SQLException exception) {
			exception.printStackTrace();
		}
		return null;
		
		
		
	}
	

	@Override
	public List<TuitionReimbursementRequest> getAllTuitionReimbursementRequest() {
		List<TuitionReimbursementRequest> requests = new ArrayList<TuitionReimbursementRequest>();
		try {
			String sql = "SELECT * FROM tuition_reimbursement_requests";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				TuitionReimbursementRequest trr = new TuitionReimbursementRequest();
				trr.setId(rs.getInt("ID"));
				trr.setForEmployee(rs.getInt("FOR_EMPLOYEE"));
				trr.setEventType(rs.getString("EVENT_TYPE"));
				trr.setTotalCost(rs.getBigDecimal("TOTAL_COSTS").doubleValue());
				trr.setEventDescription(rs.getString("EVENT_DESCRIPTION"));
				trr.setEventLocation(rs.getString("EVENT_LOCATION"));
				trr.setStartDateTimeOfEvent(rs.getTimestamp("START_DATETIME_OF_EVENT"));
				trr.setHoursOfWorkMissed(rs.getBigDecimal("HOURS_OF_WORK_MISSED").doubleValue());
				trr.setWorkRelatedJustification(rs.getString("WORK_RELATED_JUSTIFICATION"));
				trr.setGradeFormat(rs.getInt("GRADE_FORMAT"));
				trr.setScoreRequired(rs.getInt("SCORE_REQUIRED"));
				trr.setDateSubmitted(rs.getTimestamp("DATE_COMPLETED_AND_SUBMITTED"));
				trr.setstatusOfRequest(rs.getString("STATUS_OF_REQUEST"));
				trr.setWaitingOnMessageId(rs.getInt("WAITING_ON_MESSAGE"));
				trr.setHasSupervisorApproval(rs.getString("HAS_SUPERVISOR_APPROVAL") == "T");
				trr.setHasDepartmentHeadApproval(rs.getString("HAS_DEPARTMENT_HEAD_APPROVAL") == "T");
				trr.setHasBenCoApproval(rs.getString("HAS_BENCO_APPROVAL") == "T");
				requests.add(trr);
			}
		}
		catch (SQLException exception) {
			exception.printStackTrace();
		}
		return requests;
	}

	@Override
	public List<TuitionReimbursementRequest> getAllEmployeeTuitionReimbursementRequest(int employeeId) {
		List<TuitionReimbursementRequest> requests = new ArrayList<TuitionReimbursementRequest>();
		try {
			String sql = "SELECT * FROM tuition_reimbursement_requests WHERE for_employee = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, Integer.toString(employeeId));
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				TuitionReimbursementRequest trr = new TuitionReimbursementRequest();
				trr.setId(rs.getInt("ID"));
				trr.setForEmployee(rs.getInt("FOR_EMPLOYEE"));
				trr.setEventType(rs.getString("EVENT_TYPE"));
				trr.setTotalCost(rs.getBigDecimal("TOTAL_COSTS").doubleValue());
				trr.setEventDescription(rs.getString("EVENT_DESCRIPTION"));
				trr.setEventLocation(rs.getString("EVENT_LOCATION"));
				trr.setStartDateTimeOfEvent(rs.getTimestamp("START_DATETIME_OF_EVENT"));
				trr.setHoursOfWorkMissed(rs.getBigDecimal("HOURS_OF_WORK_MISSED").doubleValue());
				trr.setWorkRelatedJustification(rs.getString("WORK_RELATED_JUSTIFICATION"));
				trr.setGradeFormat(rs.getInt("GRADE_FORMAT"));
				trr.setScoreRequired(rs.getInt("SCORE_REQUIRED"));
				trr.setDateSubmitted(rs.getTimestamp("DATE_COMPLETED_AND_SUBMITTED"));
				trr.setstatusOfRequest(rs.getString("STATUS_OF_REQUEST"));
				trr.setWaitingOnMessageId(rs.getInt("WAITING_ON_MESSAGE"));
				trr.setHasSupervisorApproval(rs.getString("HAS_SUPERVISOR_APPROVAL") == "T");
				trr.setHasDepartmentHeadApproval(rs.getString("HAS_DEPARTMENT_HEAD_APPROVAL") == "T");
				trr.setHasBenCoApproval(rs.getString("HAS_BENCO_APPROVAL") == "T");
				requests.add(trr);
			}
		}
		catch (SQLException exception) {
			exception.printStackTrace();
		}
		return requests;
	}

	@Override
	public boolean updateTuitionReimbursementRequest(TuitionReimbursementRequest trr) {
		try { //EVENT_DESCRIPTION
			String sql = "UPDATE tuition_reimbursement_requests SET"
					+ "		for_employee = ?, event_type = ?, total_costs = ?, event_description = ?, "
					+ "		event_location = ?, start_datetime_of_event = ?, work_related_justification = ?, "
					+ "		grade_format = ?, score_required = ?, hours_of_work_missed = ?, date_completed_and_submitted = ?,"
					+ "		status_of_request = ?, waiting_on_message = ?, has_supervisor_approval = ?,"
					+ "		has_department_head_approval = ?, has_benco_approval = ?"
					+ "		WHERE id = ?		   ";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, Integer.toString(trr.getForEmployee()));
			ps.setString(2, trr.getEventType());
			ps.setString(3, Double.toString(trr.getTotalCost()));
			ps.setString(4, trr.getEventDescription());
			ps.setString(5, trr.getEventLocation());
			ps.setTimestamp(6, trr.getStartDateTimeOfEvent());
			ps.setString(7, trr.getWorkRelatedJustification());
			ps.setString(8, Integer.toString(trr.getGradeFormat()));
			ps.setString(9, Integer.toString(trr.getScoreRequired()));
			if(trr.getHoursOfWorkMissed() < 0 )
				ps.setNull(10, java.sql.Types.NUMERIC);
			else
				ps.setString(10, Double.toString(trr.getHoursOfWorkMissed()));
			ps.setTimestamp(11, new java.sql.Timestamp(new java.util.Date().getTime()));
			ps.setString(12, trr.getstatusOfRequest());
			if(trr.getWaitingOnMessageId() == 0)
				ps.setNull(13, java.sql.Types.NUMERIC);
			else
				ps.setString(13, Integer.toString(trr.getWaitingOnMessageId()));
			ps.setString(14, (trr.isHasSupervisorApproval() ? "T" : "F"));
			ps.setString(15, (trr.isHasDepartmentHeadApproval() ? "T" : "F"));
			ps.setString(16, (trr.isHasBenCoApproval() ? "T" : "F"));
			ps.setString(17, Integer.toString(trr.getId()));
			ps.executeQuery();
			return true;
		}
		catch (SQLException exception) {
			exception.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean deleteTuitionReimbursementRequest(int id) {
		try {
			String sql = "DELETE FROM tuition_reimbursement_requests WHERE id = ?";
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
