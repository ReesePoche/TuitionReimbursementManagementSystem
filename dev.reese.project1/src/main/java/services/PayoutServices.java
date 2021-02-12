package services;

import java.util.ArrayList;
import java.util.List;

import daos.DBImpReimbursementPayoutDAO;
import daos.DBImpTuitionReimbursementRequestDAO;
import daos.ReimbursementPayoutDAO;
import daos.TuitionReimbursementRequestDAO;
import dev.reese.project1.entities.ReimbursementPayout;
import dev.reese.project1.entities.TuitionReimbursementRequest;

public class PayoutServices {
	
	private ReimbursementPayoutDAO rpDAO = new DBImpReimbursementPayoutDAO();
	private TuitionReimbursementRequestDAO trrDAO = new DBImpTuitionReimbursementRequestDAO();
	
	public ReimbursementPayout createPendingPayout(ReimbursementPayout rp, boolean needsEmployeeApproval) {
		if(needsEmployeeApproval)
			rp.setStatus("PEA");
		else
			rp.setStatus("PG");
		this.rpDAO.createReimbursementPayout(rp);
		return this.rpDAO.getRequestsReimbursementPayout(rp.getRequestId());
	}
	
	public ReimbursementPayout createPendingPayoutWithpara(int requestId, double amount, int year, boolean needsEmployeeApproval, String Notes) {
		ReimbursementPayout rp = new ReimbursementPayout();
		rp.setRequestId(requestId);
		rp.setAmount(amount);
		rp.setForYear(year);
		if(Notes == null)
			rp.setNotes("");
		else
			rp.setNotes(Notes);
		if(needsEmployeeApproval)
			rp.setStatus("PEA");
		else
			rp.setStatus("PG");
		this.rpDAO.createReimbursementPayout(rp);
		return this.rpDAO.getRequestsReimbursementPayout(requestId);
	}
	
	
	public ReimbursementPayout getPayout(int id) {
		return this.rpDAO.getReimbursementPayout(id);
	}
	
	public ReimbursementPayout getPayoutForRequest(int requestId) {
		return this.rpDAO.getRequestsReimbursementPayout(requestId);
	}
	
	public List<ReimbursementPayout> getEmployeePayouts(int employeeId){
		 List<ReimbursementPayout>  allPayouts =  this.rpDAO.getAllReimbursementPayout();
		 List<TuitionReimbursementRequest> trrs = this.trrDAO.getAllEmployeeTuitionReimbursementRequest(employeeId);
		 List<ReimbursementPayout>  out = new  ArrayList<ReimbursementPayout>();
		 for(int i = 0; i < allPayouts.size(); i++) {
			 for(int j = 0; j < trrs.size(); j++) {
				 if(allPayouts.get(i).getRequestId() == trrs.get(j).getId() && trrs.get(j).getForEmployee() == employeeId)
					 out.add(allPayouts.get(i));
			 }
		 }
		 return null;
	}
	
	public List<ReimbursementPayout> getEmployeePayoutsForYear(int employeeId, int year){
		return this.rpDAO.getAllEmployeesReimbursementPayout(employeeId, year);
	}
	
	public List<ReimbursementPayout> getAllPayouts(){
		return this.rpDAO.getAllReimbursementPayout();
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	public double getTotalOfEmployeePayoutsForYear(int employeeId, int year) {
		List<ReimbursementPayout> payouts = this.getEmployeePayouts(employeeId);
		double sum = 0.0;
		if(payouts == null) {
			return 0.0;
		}
		for(int i = 0; i < payouts.size(); i++) {
			
			sum+=payouts.get(i).getAmount();
		}
			
		return sum;
	}
	
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public ReimbursementPayout employeeApprovedMarkAsPendingGrading(int requestId) {
		ReimbursementPayout rp = this.rpDAO.getRequestsReimbursementPayout(requestId);
		rp.setStatus("PG");
		this.rpDAO.updateReimbursementPayout(rp);
		return rp;
	}
	
	public boolean EDeniedOrFailingGradeDeletePayoutForRequest(int requestId) {
		ReimbursementPayout rp = this.rpDAO.getRequestsReimbursementPayout(requestId);
		return this.rpDAO.deleteReimbursementPayout(rp.getId());
	}
	
	public ReimbursementPayout GradePassingMakePayment(int requestId) {
		ReimbursementPayout rp = this.rpDAO.getRequestsReimbursementPayout(requestId);
		rp.setStatus("AWARDED");
		rp.setDateOfPayment(new java.sql.Timestamp(new java.util.Date().getTime()));
		this.rpDAO.updateReimbursementPayout(rp);
		return rp;
	}
	
	

}
