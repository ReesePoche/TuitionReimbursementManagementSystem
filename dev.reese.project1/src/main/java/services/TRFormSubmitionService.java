package services;

import java.util.List;

import daos.DBImpRequestNoteDAO;
import daos.DBImpTuitionReimbursementRequestDAO;
import daos.RequestNoteDAO;
import daos.TuitionReimbursementRequestDAO;
import dev.reese.project1.entities.Employee;
import dev.reese.project1.entities.RequestNote;
import dev.reese.project1.entities.TuitionReimbursementRequest;
import serviceexceptions.RequestNotFoundException;

public class TRFormSubmitionService {
	
	private TuitionReimbursementRequestDAO trrDAO = new DBImpTuitionReimbursementRequestDAO();
	private RequestNoteDAO rnDAO = new DBImpRequestNoteDAO();
	
	public TuitionReimbursementRequest submitRequest(TuitionReimbursementRequest requestForm) {
		requestForm.setDateSubmitted(new java.sql.Timestamp(new java.util.Date().getTime()));
		this.trrDAO.createTuitionReimbursementRequest(requestForm);
		return this.trrDAO.getTuitionReimbursementRequest(requestForm.getDateSubmitted(), requestForm.getForEmployee());
	}
	
	public RequestNote attachNote(int requestId, RequestNote rn) {
		
		rn.setAddedAt(new java.sql.Timestamp(new java.util.Date().getTime()));
		rnDAO.createRequestNote(rn);
		return rnDAO.getRequestNote(rn.getRequestId(), rn.getAddedBy(), rn.getAddedAt());
	}
	
	public RequestNote attachNote(int requestId, String noteSubject, String note, int addedBy) {
		
		RequestNote newNote = new RequestNote();
		newNote.setAddedBy(addedBy);
		newNote.setRequestId(requestId);
		newNote.setSubject(noteSubject);
		newNote.setNote(note);
		newNote.setAddedAt(new java.sql.Timestamp(new java.util.Date().getTime()));
		this.rnDAO.createRequestNote(newNote);
		return this.rnDAO.getRequestNote(newNote.getRequestId(), newNote.getAddedBy(), newNote.getAddedAt());
	}
	
	
	public TuitionReimbursementRequest getRequestFormNoAttachments(int id) throws RequestNotFoundException{
		TuitionReimbursementRequest trr = this.trrDAO.getTuitionReimbursementRequest(id);
		if(trr.getId() == 0)
			throw new RequestNotFoundException("the form with that id does not exist in our system");
		return trr;
	}
	
	public TuitionReimbursementRequest getRequestFormWithAttachments(int id) throws RequestNotFoundException{
		TuitionReimbursementRequest trr = this.trrDAO.getTuitionReimbursementRequest(id);
		if(trr.getId() == 0)
			throw new RequestNotFoundException("the form with that id does not exist in our system");
		trr.setNotes(this.getRequestFormNotesNoCheck(id));
		return trr;
	}
	
	public List<RequestNote> getRequestFormNotesNoCheck(int requestId){
		return this.rnDAO.getAllRequestsRequestNote(requestId);
	}
	
	
	public List<TuitionReimbursementRequest> getAllRequestsForEmployee(int employeeId){
		return this.trrDAO.getAllEmployeeTuitionReimbursementRequest(employeeId);
	}
	
	public TuitionReimbursementRequest getRequest(int requestId) {
		return this.trrDAO.getTuitionReimbursementRequest(requestId);
	}
	
	
	///////////////////////////////////////////////////////////////////
	public TuitionReimbursementRequest markAsWaitingOnMessage(int requestId, int messageId) throws RequestNotFoundException{
		TuitionReimbursementRequest trr = this.trrDAO.getTuitionReimbursementRequest(requestId);
		if(trr.getId() == 0)
			throw new RequestNotFoundException("the form with that id does not exist in our system");
		trr.setWaitingOnMessageId(messageId);
		this.trrDAO.updateTuitionReimbursementRequest(trr);
		return trr;
	}
	
	public TuitionReimbursementRequest markApprovalsNotNeededAsGotten(int requestId){
		TuitionReimbursementRequest trr = this.trrDAO.getTuitionReimbursementRequest(requestId);
		LoginService ls = new LoginService();
		Employee e = ls.getEmployee(trr.getForEmployee());
		Employee dh = ls.getEmployeeDepartmentHead(requestId);
		boolean hasNoSupervisor = e.getSupervisorId() == 0;
		boolean SVSameAsDH = dh.getId() == e.getSupervisorId();
		boolean isDH = e.getId() == dh.getId();
		boolean hasNoDH = dh.getId() == 0;
		if (hasNoSupervisor){
			trr.setHasSupervisorApproval(true);
			this.getNoteForHasNoSV(e, requestId);
		}
		if(hasNoDH) {
			trr.setHasDepartmentHeadApproval(true);
			this.getNoteForEmployeeHasNoDH(e, requestId);
		}
		if(isDH) {
			trr.setHasDepartmentHeadApproval(true);
			this.getNoteForEmployeeIsDH(e, requestId);
		}
		if(SVSameAsDH && hasNoSupervisor) {
			trr.setHasSupervisorApproval(true);
			this.getNoteForSVisDH(e, requestId);
		}
		if(e.getSupervisorId() == 0) {
			trr.setHasSupervisorApproval(true);
		}
		this.trrDAO.updateTuitionReimbursementRequest(trr);
		return trr;
	}
	
	public String whoShouldBeSentApprovalNext(int requestId){
		TuitionReimbursementRequest trr = this.trrDAO.getTuitionReimbursementRequest(requestId);
		boolean SVA = trr.isHasSupervisorApproval();
		boolean DHA = trr.isHasDepartmentHeadApproval();
		boolean BCA = trr.isHasBenCoApproval();
		if(SVA && DHA && BCA) {
			if(trr.getstatusOfRequest().equals("APG"))
				return "E";
			if(trr.getstatusOfRequest().equals("PEF"))
				return "E";
			if(trr.getstatusOfRequest().equals("APGC")) {
				if(trr.getGradeFormat() == 2) {
					return "SV";
				}
				return "BC";
			}
		}
		if(SVA && DHA && !BCA) {
			return "BC";
		}
		if(!SVA && !DHA) {
			return "SV";
		}
		if(SVA && !DHA) {
			return "DH";
		}
		if(!SVA && DHA)
			return "SV";
		return null;
	}
	
	
	private RequestNote getNoteForEmployeeIsDH(Employee e, int requestId) {
		RequestNote newRN = new RequestNote();
		newRN.setRequestId(requestId);
		newRN.setSubject("DH PREAPPROVAL SGN");
		newRN.setNote("DH approval process was skipped in this request because the employee is the DH");
		newRN.setAddedBy(e.getId());
		newRN.setAddedAt(new java.sql.Timestamp(new java.util.Date().getTime()));
		this.rnDAO.createRequestNote(newRN);
		return this.rnDAO.getRequestNote(requestId, e.getId(), newRN.getAddedAt());
	}
	
	private RequestNote getNoteForEmployeeHasNoDH(Employee e, int requestId) {
		RequestNote newRN = new RequestNote();
		newRN.setRequestId(requestId);
		newRN.setSubject("DH PREAPPROVAL SGN");
		newRN.setNote("DH approval process was skipped in this request because the employee has no DH");
		newRN.setAddedBy(e.getId());
		newRN.setAddedAt(new java.sql.Timestamp(new java.util.Date().getTime()));
		this.rnDAO.createRequestNote(newRN);
		return this.rnDAO.getRequestNote(requestId, e.getId(), newRN.getAddedAt());
	}
	
	private RequestNote getNoteForSVisDH(Employee e, int requestId) {
		RequestNote newRN = new RequestNote();
		newRN.setRequestId(requestId);
		newRN.setSubject("SV PREAPPROVAL SGN");
		newRN.setNote("SV approval process was skipped in this request because the employee's SV is the DH");
		newRN.setAddedBy(e.getId());
		newRN.setAddedAt(new java.sql.Timestamp(new java.util.Date().getTime()));
		this.rnDAO.createRequestNote(newRN);
		return this.rnDAO.getRequestNote(requestId, e.getId(), newRN.getAddedAt());
	}
	
	private RequestNote getNoteForHasNoSV(Employee e, int requestId) {
		RequestNote newRN = new RequestNote();
		newRN.setRequestId(requestId);
		newRN.setSubject("SV PREAPPROVAL SGN");
		newRN.setNote("SV approval process was skipped in this request because the employee has no SV");
		newRN.setAddedBy(e.getId());
		newRN.setAddedAt(new java.sql.Timestamp(new java.util.Date().getTime()));
		this.rnDAO.createRequestNote(newRN);
		return this.rnDAO.getRequestNote(requestId, e.getId(), newRN.getAddedAt());
	}
	
	
	public TuitionReimbursementRequest MarkRequestAsDenied(int requestId) {
		TuitionReimbursementRequest trr = this.trrDAO.getTuitionReimbursementRequest(requestId);
		trr.setstatusOfRequest("DENIED");
		this.trrDAO.updateTuitionReimbursementRequest(trr);
		return trr;
	}
	
	
	
	//////////////////////////////////////////////////////////////////////////////
	public TuitionReimbursementRequest MarkRequestAsDeniedBySV(int requestId, RequestNote reasonForDenialNote) {
		TuitionReimbursementRequest trr = this.trrDAO.getTuitionReimbursementRequest(requestId);
		trr.setHasSupervisorApproval(false);
		trr.setstatusOfRequest("DENIED");
		this.trrDAO.updateTuitionReimbursementRequest(trr);
		this.attachNote(requestId, reasonForDenialNote);
		return trr;
	}
	
	public TuitionReimbursementRequest MarkRequestAsDeniedByDH(int requestId,  RequestNote reasonForDenialNote) {
		TuitionReimbursementRequest trr = this.trrDAO.getTuitionReimbursementRequest(requestId);
		trr.setHasDepartmentHeadApproval(false);
		trr.setstatusOfRequest("DENIED");
		this.trrDAO.updateTuitionReimbursementRequest(trr);
		this.attachNote(requestId, reasonForDenialNote);
		return trr;
	}
	
	public TuitionReimbursementRequest MarkRequestAsDeniedByBenCo(int requestId,  RequestNote reasonForDenialNote) {
		TuitionReimbursementRequest trr = this.trrDAO.getTuitionReimbursementRequest(requestId);
		
		trr.setHasBenCoApproval(false);
		trr.setstatusOfRequest("DENIED");
		this.trrDAO.updateTuitionReimbursementRequest(trr);
		this.attachNote(requestId, reasonForDenialNote);
		return trr;
	}
	
	public TuitionReimbursementRequest MarkRequestAsDeniedByE(int requestId, RequestNote reasonForDenialNote) {
		TuitionReimbursementRequest trr = this.trrDAO.getTuitionReimbursementRequest(requestId);
		
		trr.setstatusOfRequest("DENIED");
		this.trrDAO.updateTuitionReimbursementRequest(trr);
		this.attachNote(requestId, reasonForDenialNote);
		return trr;
	}
	
	public TuitionReimbursementRequest MarkRequestAsDeniedForFailingGrade(int requestId, RequestNote reasonForDenialNote) {
		TuitionReimbursementRequest trr = this.trrDAO.getTuitionReimbursementRequest(requestId);
		
		trr.setstatusOfRequest("DENIED");
		this.trrDAO.updateTuitionReimbursementRequest(trr);
		this.attachNote(requestId, reasonForDenialNote);
		return trr;
	}
	
	public TuitionReimbursementRequest MarkRequestAsApprovedBySV(int requestId) {
		TuitionReimbursementRequest trr = this.trrDAO.getTuitionReimbursementRequest(requestId);
		
		trr.setHasSupervisorApproval(true);
		this.trrDAO.updateTuitionReimbursementRequest(trr);
		return trr;
	}
	
	
	
	public TuitionReimbursementRequest MarkRequestAsApprovedByDH(int requestId){
		TuitionReimbursementRequest trr = this.trrDAO.getTuitionReimbursementRequest(requestId);
		trr.setHasDepartmentHeadApproval(true);
		this.trrDAO.updateTuitionReimbursementRequest(trr);
		return trr;
	}
	
	public TuitionReimbursementRequest MarkRequestAsApprovedByBenCo(int requestId) {
		TuitionReimbursementRequest trr = this.trrDAO.getTuitionReimbursementRequest(requestId);
		trr.setHasBenCoApproval(true);
		this.trrDAO.updateTuitionReimbursementRequest(trr);
		return trr;
	}
	
	public TuitionReimbursementRequest MarkRequestPendingSVApproval(int requestId) {
		TuitionReimbursementRequest trr = this.trrDAO.getTuitionReimbursementRequest(requestId);
		trr.setstatusOfRequest("PSVA");
		this.trrDAO.updateTuitionReimbursementRequest(trr);
		return trr;
	}
	
	public TuitionReimbursementRequest MarkRequestPendingDHApproval(int requestId) {
		TuitionReimbursementRequest trr = this.trrDAO.getTuitionReimbursementRequest(requestId);
		trr.setstatusOfRequest("PDHA");
		this.trrDAO.updateTuitionReimbursementRequest(trr);
		return trr;
	}
	
	public TuitionReimbursementRequest MarkRequestPendingBenCoApproval(int requestId) {
		TuitionReimbursementRequest trr = this.trrDAO.getTuitionReimbursementRequest(requestId);
		trr.setstatusOfRequest("PBCA");
		this.trrDAO.updateTuitionReimbursementRequest(trr);
		return trr;
	}
	
	public TuitionReimbursementRequest MarkRequestPreApprovedPendingGrading(int requestId) {
		TuitionReimbursementRequest trr = this.trrDAO.getTuitionReimbursementRequest(requestId);
		trr.setstatusOfRequest("APG");
		this.trrDAO.updateTuitionReimbursementRequest(trr);
		return trr;
	}
	
	
	public TuitionReimbursementRequest MarkRequestAsApprovedPendingGradingConfimation(int requestId) {
		TuitionReimbursementRequest trr = this.trrDAO.getTuitionReimbursementRequest(requestId);
		trr.setstatusOfRequest("APGC");
		this.trrDAO.updateTuitionReimbursementRequest(trr);
		return trr;
	}
	
	public TuitionReimbursementRequest MarkRequestAsApproved(int requestId) {
		TuitionReimbursementRequest trr = this.trrDAO.getTuitionReimbursementRequest(requestId);
		
		trr.setstatusOfRequest("APPROVED");
		trr.setWaitingOnMessageId(0);
		this.trrDAO.updateTuitionReimbursementRequest(trr);
		return trr;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public TuitionReimbursementRequest MarkRequestAsWaitingOnFBFromEmployee(int requestId) {
		TuitionReimbursementRequest trr = this.trrDAO.getTuitionReimbursementRequest(requestId);
		trr.setstatusOfRequest("PEFB");
		this.trrDAO.updateTuitionReimbursementRequest(trr);
		return trr;
	}
	
	public TuitionReimbursementRequest MarkRequestAsWaitingOnFBFromSV(int requestId) throws RequestNotFoundException{
		TuitionReimbursementRequest trr = this.trrDAO.getTuitionReimbursementRequest(requestId);
		if(trr.getId() == 0)
			throw new RequestNotFoundException("the form with that id does not exist in our system");
		trr.setstatusOfRequest("PSVFB");
		this.trrDAO.updateTuitionReimbursementRequest(trr);
		return trr;
	}
	
	public TuitionReimbursementRequest MarkRequestAsWaitingOnFBFromDH(int requestId) throws RequestNotFoundException{
		TuitionReimbursementRequest trr = this.trrDAO.getTuitionReimbursementRequest(requestId);
		if(trr.getId() == 0)
			throw new RequestNotFoundException("the form with that id does not exist in our system");
		trr.setstatusOfRequest("PDHFB");
		this.trrDAO.updateTuitionReimbursementRequest(trr);
		return trr;
	}
	
	
	

}
