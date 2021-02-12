package daos;

import java.util.List;

import dev.reese.project1.entities.ReimbursementMessage;

public interface ReimbursementMessageDAO {
	
	public boolean createReimbursementMessage(ReimbursementMessage rm);
	
	public ReimbursementMessage getReimbursementMessage(int id);
	
	public ReimbursementMessage getReimbursementMessage(int requestId, int recieverId, java.sql.Timestamp createdAt);
	
	public List<ReimbursementMessage> getAllReimbursementMessage();
	
	public List<ReimbursementMessage> getRequestsReimbursementMessages(int requestId);
	
	public List<ReimbursementMessage> getEmployeeRecievedReimbursementMessage(int employeeId);
	
	public List<ReimbursementMessage> getEmployeeSentReimbursementMessage(int employeeId);
	
	public boolean updateReimbursementMessage(ReimbursementMessage rm);
	
	public boolean deleteReimbursementMessage(int id);
	

}
