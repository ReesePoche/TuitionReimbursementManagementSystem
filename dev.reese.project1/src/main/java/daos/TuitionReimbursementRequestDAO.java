package daos;

import java.util.List;

import dev.reese.project1.entities.TuitionReimbursementRequest;

public interface TuitionReimbursementRequestDAO {
	
	public boolean createTuitionReimbursementRequest(TuitionReimbursementRequest trr);
	
	public TuitionReimbursementRequest getTuitionReimbursementRequest(int id);
	
	public TuitionReimbursementRequest getTuitionReimbursementRequest(java.sql.Timestamp ts, int employeeId);
	
	public List<TuitionReimbursementRequest> getAllTuitionReimbursementRequest();
	
	public List<TuitionReimbursementRequest> getAllEmployeeTuitionReimbursementRequest(int employeeId);
	
	public boolean updateTuitionReimbursementRequest(TuitionReimbursementRequest trr);
	
	public boolean deleteTuitionReimbursementRequest(int id);
	

}
