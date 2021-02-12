package daos;

import java.util.List;

import dev.reese.project1.entities.ReimbursementPayout;

public interface ReimbursementPayoutDAO {
	
	public boolean createReimbursementPayout(ReimbursementPayout rp);
	
	public ReimbursementPayout getReimbursementPayout(int id);
	
	public ReimbursementPayout getRequestsReimbursementPayout(int requestId);
	
	public List<ReimbursementPayout> getAllReimbursementPayout();
	
	public List<ReimbursementPayout> getAllEmployeesReimbursementPayout(int employeeId);
	
	public List<ReimbursementPayout> getAllEmployeesReimbursementPayout(int employeeId, int year);
	
	public boolean updateReimbursementPayout(ReimbursementPayout rp);
	
	public boolean deleteReimbursementPayout(int id);
	

}
