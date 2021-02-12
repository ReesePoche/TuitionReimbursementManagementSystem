package services;

import java.util.List;

import daos.DBImpEmployeeDAO;
import daos.DBImpReimbursementMessageDAO;
import daos.DBImpTuitionReimbursementRequestDAO;
import daos.EmployeeDAO;
import daos.ReimbursementMessageDAO;
import daos.TuitionReimbursementRequestDAO;
import dev.reese.project1.entities.Employee;
import dev.reese.project1.entities.ReimbursementMessage;
import dev.reese.project1.entities.TuitionReimbursementRequest;
import serviceexceptions.EmployeeNotFoundException;

public class TRMSMessagingService {
	
	private ReimbursementMessageDAO rmDAO = new DBImpReimbursementMessageDAO();
	private EmployeeDAO eDAO = new DBImpEmployeeDAO();
	private TuitionReimbursementRequestDAO trrDAO = new DBImpTuitionReimbursementRequestDAO();
	
	public ReimbursementMessage sendMessage(ReimbursementMessage rm) {
		rm.setStatus("UR");
		rm.setCreatedAt(new java.sql.Timestamp(new java.util.Date().getTime()));
		this.rmDAO.createReimbursementMessage(rm);
		return this.rmDAO.getReimbursementMessage(rm.getRequestId(), rm.getRecieverId(), rm.getCreatedAt());
	}
	
	public ReimbursementMessage getMessage(int messageId) {
		return this.rmDAO.getReimbursementMessage(messageId);
	}
	
	public ReimbursementMessage markNoticeEmailAsConfimed(int messageId) {
		ReimbursementMessage message = rmDAO.getReimbursementMessage(messageId);
		message.setResponse("C");
		message.setStatus("R");
		message.setRespondedAt(new java.sql.Timestamp(new java.util.Date().getTime()));
		this.rmDAO.updateReimbursementMessage(message);
		return message;
	}
	
	public ReimbursementMessage markNoticeEmailAsGradeSubmittedAndReplied(int messageId) {
		ReimbursementMessage message = rmDAO.getReimbursementMessage(messageId);
		message.setResponse("GS");
		message.setStatus("R");
		message.setRespondedAt(new java.sql.Timestamp(new java.util.Date().getTime()));
		this.rmDAO.updateReimbursementMessage(message);
		return message;
	}
	
	
	
	
	
	
	public List<ReimbursementMessage> getAllMessagesForEmployee(int employeeId) throws EmployeeNotFoundException{
		Employee e = this.eDAO.getEmployee(employeeId);
		if(e.getId() == 0)
			throw new EmployeeNotFoundException("employee entered does not exist in the system");
		return this.rmDAO.getEmployeeRecievedReimbursementMessage(employeeId);
		
	}
	
	public List<ReimbursementMessage> getAllMessagesSentByEmployee(int employeeId) throws EmployeeNotFoundException{
		Employee e = this.eDAO.getEmployee(employeeId);
		if(e.getId() == 0)
			throw new EmployeeNotFoundException("employee entered does not exist in the system");
		return this.rmDAO.getEmployeeSentReimbursementMessage(employeeId);
	}
	
	
	public ReimbursementMessage MarkMessageAsRead(ReimbursementMessage rm) {
		if(rm.getTypeOfMessage().equals("PAN") || rm.getTypeOfMessage().equals("RDN") || rm.getTypeOfMessage().equals("RAN")
				|| rm.getTypeOfMessage().equals("FRR")) {
			rm.setStatus("R");
			rm.setResponse("C");
			rm.setRespondedAt(new java.sql.Timestamp(new java.util.Date().getTime()));
		}
		else {
			rm.setStatus("UD");
		}
		this.rmDAO.updateReimbursementMessage(rm);
		return this.rmDAO.getReimbursementMessage(rm.getId());
	}
	
	public ReimbursementMessage markAsResponded(ReimbursementMessage rm) {
		rm.setStatus("R");
		rm.setRespondedAt(new java.sql.Timestamp(new java.util.Date().getTime()));
		this.rmDAO.updateReimbursementMessage(rm);
		return this.rmDAO.getReimbursementMessage(rm.getId());
	}
	
	public ReimbursementMessage markAsApproved(ReimbursementMessage rm) {
		rm.setResponse("A");
		this.rmDAO.updateReimbursementMessage(rm);
		return this.rmDAO.getReimbursementMessage(rm.getId());
	}
	
	public ReimbursementMessage markAsDenied(ReimbursementMessage rm) {
		rm.setResponse("D");
		this.rmDAO.updateReimbursementMessage(rm);
		return this.rmDAO.getReimbursementMessage(rm.getId());
	}
	
	public ReimbursementMessage markAsRequestedFeedback(ReimbursementMessage rm) {
		rm.setResponse("RF");
		this.rmDAO.updateReimbursementMessage(rm);
		return this.rmDAO.getReimbursementMessage(rm.getId());
	}
	
	
	public ReimbursementMessage sendSVApprovalRequest(int requestId) {
		TuitionReimbursementRequest trr = this.trrDAO.getTuitionReimbursementRequest(requestId);
		LoginService ls = new LoginService();
		Employee superVisor = ls.getEmployeeSupervisor(trr.getForEmployee());
		ReimbursementMessage newRM = new ReimbursementMessage();
		newRM.setRequestId(requestId);
		newRM.setRecieverId(superVisor.getId());
		newRM.setSenderId(-1);
		newRM.setTypeOfMessage("AR");
		newRM.setStatus("UR");
		newRM.setSenderNotes("Employee requires supervisor approval for tuition Reimbursement\nSGM");
		newRM.setCreatedAt(new java.sql.Timestamp(new java.util.Date().getTime()));
		this.rmDAO.createReimbursementMessage(newRM);
		return this.rmDAO.getReimbursementMessage(requestId, superVisor.getId(), newRM.getCreatedAt());
	}
	
	public ReimbursementMessage sendDHApprovalRequest(int requestId) {
		TuitionReimbursementRequest trr = this.trrDAO.getTuitionReimbursementRequest(requestId);
		LoginService ls = new LoginService();
		Employee depHead = ls.getEmployeeDepartmentHead(trr.getForEmployee());
		ReimbursementMessage newRM = new ReimbursementMessage();
		newRM.setRequestId(requestId);
		newRM.setRecieverId(depHead.getId());
		newRM.setSenderId(-1);
		newRM.setTypeOfMessage("AR");
		newRM.setStatus("UR");
		newRM.setSenderNotes("Employee requires department head approval for tuition Reimbursement\nSGM");
		newRM.setCreatedAt(new java.sql.Timestamp(new java.util.Date().getTime()));
		this.rmDAO.createReimbursementMessage(newRM);
		return this.rmDAO.getReimbursementMessage(requestId, depHead.getId(), newRM.getCreatedAt());
	}
	
	public ReimbursementMessage sendBenCoApprovalRequest(int requestId) {
		LoginService ls = new LoginService();
		Employee benCo = ls.getBenCo();
		ReimbursementMessage newRM = new ReimbursementMessage();
		newRM.setRequestId(requestId);
		newRM.setRecieverId(benCo.getId());
		newRM.setSenderId(-1);
		newRM.setTypeOfMessage("AR");
		newRM.setStatus("UR");
		newRM.setSenderNotes("Employee requires BenCo approval for tuition Reimbursement\nSGM");
		newRM.setCreatedAt(new java.sql.Timestamp(new java.util.Date().getTime()));
		this.rmDAO.createReimbursementMessage(newRM);
		return this.rmDAO.getReimbursementMessage(requestId, benCo.getId(), newRM.getCreatedAt());
	}
	
	public ReimbursementMessage sendEmployeeFinalApprovalRequest(int requestId) {
		TuitionReimbursementRequest trr = this.trrDAO.getTuitionReimbursementRequest(requestId);
		ReimbursementMessage newRM = new ReimbursementMessage();
		newRM.setRequestId(requestId);
		newRM.setRecieverId(trr.getForEmployee());
		newRM.setSenderId(-1);
		newRM.setTypeOfMessage("AR");
		newRM.setStatus("UR");
		newRM.setSenderNotes("Benco has altered reimbursement amount. Your approval required before approval. Look in pending payout to see the amount changed\nSGM");
		newRM.setCreatedAt(new java.sql.Timestamp(new java.util.Date().getTime()));
		this.rmDAO.createReimbursementMessage(newRM);
		return this.rmDAO.getReimbursementMessage(requestId, newRM.getRecieverId(), newRM.getCreatedAt());
	}
	
	//
	
	public ReimbursementMessage sendEmployeePreApprovalNotice(int requestId) {
		TuitionReimbursementRequest trr = this.trrDAO.getTuitionReimbursementRequest(requestId);
		ReimbursementMessage newRM = new ReimbursementMessage();
		newRM.setRequestId(requestId);
		newRM.setRecieverId(trr.getForEmployee());
		newRM.setSenderId(-1);
		newRM.setTypeOfMessage("PAN");
		newRM.setStatus("UR");
		newRM.setSenderNotes("Your request has been approved. The reimbursement is pending a submition of a passing grade.\nSGM");
		newRM.setCreatedAt(new java.sql.Timestamp(new java.util.Date().getTime()));
		this.rmDAO.createReimbursementMessage(newRM);
		return this.rmDAO.getReimbursementMessage(requestId, newRM.getRecieverId(), newRM.getCreatedAt());
	}
	
	public ReimbursementMessage sendEmployeeDenialNotice(int requestId, String reason) {
		TuitionReimbursementRequest trr = this.trrDAO.getTuitionReimbursementRequest(requestId);
		ReimbursementMessage newRM = new ReimbursementMessage();
		newRM.setRequestId(requestId);
		newRM.setRecieverId(trr.getForEmployee());
		newRM.setSenderId(-1);
		newRM.setTypeOfMessage("RDN");
		newRM.setStatus("UR");
		newRM.setSenderNotes(reason);
		newRM.setCreatedAt(new java.sql.Timestamp(new java.util.Date().getTime()));
		this.rmDAO.createReimbursementMessage(newRM);
		return this.rmDAO.getReimbursementMessage(requestId, newRM.getRecieverId(), newRM.getCreatedAt());
	}
	
	public ReimbursementMessage sendEmployeeDenialNoticeFrom(int requestId, String reason, int fromId) {
		TuitionReimbursementRequest trr = this.trrDAO.getTuitionReimbursementRequest(requestId);
		ReimbursementMessage newRM = new ReimbursementMessage();
		newRM.setRequestId(requestId);
		newRM.setRecieverId(trr.getForEmployee());
		newRM.setSenderId(-1);
		newRM.setTypeOfMessage("RDN");
		newRM.setStatus("UR");
		newRM.setSenderNotes(reason);
		newRM.setCreatedAt(new java.sql.Timestamp(new java.util.Date().getTime()));
		this.rmDAO.createReimbursementMessage(newRM);
		return this.rmDAO.getReimbursementMessage(requestId, newRM.getRecieverId(), newRM.getCreatedAt());
	}
	
	public ReimbursementMessage sendEmployeeReimbursementAwardedNotice(int requestId) {
		TuitionReimbursementRequest trr = this.trrDAO.getTuitionReimbursementRequest(requestId);
		ReimbursementMessage newRM = new ReimbursementMessage();
		newRM.setRequestId(requestId);
		newRM.setRecieverId(trr.getForEmployee());
		newRM.setSenderId(-1);
		newRM.setTypeOfMessage("RAN");
		newRM.setStatus("UR");
		newRM.setSenderNotes("Your request for reimbursement has been granted\n");
		newRM.setCreatedAt(new java.sql.Timestamp(new java.util.Date().getTime()));
		this.rmDAO.createReimbursementMessage(newRM);
		return this.rmDAO.getReimbursementMessage(requestId, newRM.getRecieverId(), newRM.getCreatedAt());
	}
	
	
	public ReimbursementMessage sendGradeSubmissionRequest(int requestId) {
		TuitionReimbursementRequest trr = this.trrDAO.getTuitionReimbursementRequest(requestId);
		//int employeeId = trr.getForEmployee();
		ReimbursementMessage newRM = new ReimbursementMessage();
		newRM.setRequestId(requestId);
		newRM.setRecieverId(trr.getForEmployee());
		newRM.setSenderId(-1);
		newRM.setTypeOfMessage("GSR");
		newRM.setStatus("UR");
		newRM.setSenderNotes("Please submit grade or presentation. You will be rewarded the reimbursement when grade or presented is submitted and confirmed");
		newRM.setCreatedAt(new java.sql.Timestamp(new java.util.Date().getTime()));
		this.rmDAO.createReimbursementMessage(newRM);
		newRM = this.rmDAO.getReimbursementMessage(trr.getId(), trr.getForEmployee(), newRM.getCreatedAt());
		return newRM;
	}
	
	public ReimbursementMessage markGradeSubmissionMessageAsResponded(int gradeSubmissionRequestId) {
		ReimbursementMessage ogm = this.rmDAO.getReimbursementMessage(gradeSubmissionRequestId);
		ogm.setStatus("R");
		ogm.setResponse("GS");
		ogm.setRespondedAt(new java.sql.Timestamp(new java.util.Date().getTime()));
		this.rmDAO.updateReimbursementMessage(ogm);
		return ogm;
	}
	
	
	
	
	public ReimbursementMessage sendRequestForBenCoToConfirmGrade(int requestId) {
		TuitionReimbursementRequest trr = this.trrDAO.getTuitionReimbursementRequest(requestId);
		LoginService ls = new LoginService();
		Employee bc = ls.getBenCo();
		ReimbursementMessage newRM = new ReimbursementMessage();
		newRM.setRequestId(requestId);
		newRM.setRecieverId(bc.getId());
		newRM.setSenderId(-1);
		newRM.setTypeOfMessage("GCR");
		newRM.setStatus("UR");
		newRM.setSenderNotes("Employee has submitted the grade for confirmation\n");
		newRM.setCreatedAt(new java.sql.Timestamp(new java.util.Date().getTime()));
		this.rmDAO.createReimbursementMessage(newRM);
		newRM = this.rmDAO.getReimbursementMessage(trr.getId(), newRM.getRecieverId(), newRM.getCreatedAt());
		return newRM;
	}
	
	public ReimbursementMessage sendRequestForSVToSeePresentation(int requestId) {
		TuitionReimbursementRequest trr = this.trrDAO.getTuitionReimbursementRequest(requestId);
		LoginService ls = new LoginService();
		Employee sv = ls.getEmployeeSupervisor(trr.getForEmployee());
		ReimbursementMessage newRM = new ReimbursementMessage();
		newRM.setRequestId(requestId);
		newRM.setRecieverId(sv.getId());
		newRM.setSenderId(-1);
		newRM.setTypeOfMessage("GCR");
		newRM.setStatus("UR");
		newRM.setSenderNotes("Employee has submitted a presentation for viewing\n");
		newRM.setCreatedAt(new java.sql.Timestamp(new java.util.Date().getTime()));
		this.rmDAO.createReimbursementMessage(newRM);
		newRM = this.rmDAO.getReimbursementMessage(trr.getId(), newRM.getRecieverId(), newRM.getCreatedAt());
		return newRM;
	}
	
	public ReimbursementMessage MarkApprovalRequestOrGCMessageAsApproved(int messageId) {
		ReimbursementMessage ogm = this.rmDAO.getReimbursementMessage(messageId);
		ogm.setStatus("R");
		ogm.setResponse("A");
		ogm.setRespondedAt(new java.sql.Timestamp(new java.util.Date().getTime()));
		this.rmDAO.updateReimbursementMessage(ogm);
		return ogm;
	}
	
	public ReimbursementMessage MarkApprovalRequestOrGCMessageAsDenied(int messageId) {
		ReimbursementMessage ogm = this.rmDAO.getReimbursementMessage(messageId);
		ogm.setStatus("R");
		ogm.setResponse("D");
		ogm.setRespondedAt(new java.sql.Timestamp(new java.util.Date().getTime()));
		this.rmDAO.updateReimbursementMessage(ogm);
		return ogm;
	}
	
	
	public ReimbursementMessage sendRequestForFeedBackToEmployee(int approvalRequestMessageId, String Notes) {
		//set up reply / request for feedback message
		ReimbursementMessage ogm = this.rmDAO.getReimbursementMessage(approvalRequestMessageId);
		TuitionReimbursementRequest trr = this.trrDAO.getTuitionReimbursementRequest(ogm.getRequestId());
		int employeeId = trr.getForEmployee();
		ReimbursementMessage newRM = new ReimbursementMessage();
		newRM.setRequestId(ogm.getRequestId());
		newRM.setRecieverId(employeeId);
		newRM.setSenderId(ogm.getRecieverId());
		newRM.setTypeOfMessage("FR");
		newRM.setStatus("UR");
		if(Notes == null)
			newRM.setSenderNotes("");
		else
			newRM.setSenderNotes(Notes);
		newRM.setCreatedAt(new java.sql.Timestamp(new java.util.Date().getTime()));
		this.rmDAO.createReimbursementMessage(newRM);
		newRM = this.rmDAO.getReimbursementMessage(trr.getId(), employeeId, newRM.getCreatedAt());
		// feedback request sent
		ogm.setStatus("R");
		ogm.setResponse("RF");
		ogm.setRespondedAt(new java.sql.Timestamp(new java.util.Date().getTime()));
		this.rmDAO.updateReimbursementMessage(ogm); //orginal message marked as replied
		return newRM;
	}
	
	public ReimbursementMessage sendRequestForFeedBackToSV(int approvalRequestMessageId, String Notes) {
		//set up reply / request for feedback message
		ReimbursementMessage ogm = this.rmDAO.getReimbursementMessage(approvalRequestMessageId);
		TuitionReimbursementRequest trr = this.trrDAO.getTuitionReimbursementRequest(ogm.getRequestId());
		LoginService ls = new LoginService();
		Employee sv = ls.getEmployeeSupervisor(trr.getForEmployee());
		ReimbursementMessage newRM = new ReimbursementMessage();
		newRM.setRequestId(ogm.getRequestId());
		newRM.setRecieverId(sv.getId());
		newRM.setSenderId(ogm.getRecieverId());
		newRM.setTypeOfMessage("FR");
		newRM.setStatus("UR");
		if(Notes == null)
			newRM.setSenderNotes("");
		else
			newRM.setSenderNotes(Notes);
		newRM.setCreatedAt(new java.sql.Timestamp(new java.util.Date().getTime()));
		this.rmDAO.createReimbursementMessage(newRM);
		newRM = this.rmDAO.getReimbursementMessage(trr.getId(), sv.getId(), newRM.getCreatedAt());
		// feedback request sent
		ogm.setStatus("R");
		ogm.setResponse("RF");
		ogm.setRespondedAt(new java.sql.Timestamp(new java.util.Date().getTime()));
		this.rmDAO.updateReimbursementMessage(ogm); //orginal message marked as replied
		return newRM;
	}
	
	public ReimbursementMessage sendRequestForFeedBackToDH(int approvalRequestMessageId, String Notes) {
		//set up reply / request for feedback message
		ReimbursementMessage ogm = this.rmDAO.getReimbursementMessage(approvalRequestMessageId);
		TuitionReimbursementRequest trr = this.trrDAO.getTuitionReimbursementRequest(ogm.getRequestId());
		LoginService ls = new LoginService();
		Employee dh = ls.getEmployeeDepartmentHead(trr.getForEmployee());
		ReimbursementMessage newRM = new ReimbursementMessage();
		newRM.setRequestId(ogm.getRequestId());
		newRM.setRecieverId(dh.getId());
		newRM.setSenderId(ogm.getRecieverId());
		newRM.setTypeOfMessage("FR");
		newRM.setStatus("UR");
		if(Notes == null)
			newRM.setSenderNotes("");
		else
			newRM.setSenderNotes(Notes);
		newRM.setCreatedAt(new java.sql.Timestamp(new java.util.Date().getTime()));
		this.rmDAO.createReimbursementMessage(newRM);
		newRM = this.rmDAO.getReimbursementMessage(trr.getId(), dh.getId(), newRM.getCreatedAt());
		// feedback request sent
		ogm.setStatus("R");
		ogm.setResponse("RF");
		ogm.setRespondedAt(new java.sql.Timestamp(new java.util.Date().getTime()));
		this.rmDAO.updateReimbursementMessage(ogm); //orginal message marked as replied
		return newRM;
	}
	
		
	public ReimbursementMessage ReplyToFeedBackRequest(int FBreqeustMessageId, String Notes) {
		ReimbursementMessage ogm = this.rmDAO.getReimbursementMessage(FBreqeustMessageId);
		ReimbursementMessage newRM = new ReimbursementMessage();
		newRM.setRequestId(ogm.getRequestId());
		newRM.setRecieverId(ogm.getSenderId());
		newRM.setSenderId(ogm.getRecieverId());
		newRM.setTypeOfMessage("FRR");
		newRM.setStatus("UR");
		if(Notes == null)
			newRM.setSenderNotes("");
		else
			newRM.setSenderNotes(Notes);
		newRM.setCreatedAt(new java.sql.Timestamp(new java.util.Date().getTime()));
		this.rmDAO.createReimbursementMessage(newRM);
		//reply made so now mark ogm as replied
		ogm.setStatus("R");
		ogm.setResponse("SF");
		ogm.setRespondedAt(new java.sql.Timestamp(new java.util.Date().getTime()));
		this.rmDAO.updateReimbursementMessage(ogm);
		newRM = this.rmDAO.getReimbursementMessage(ogm.getRequestId(), newRM.getRecieverId(), newRM.getCreatedAt());
		return newRM;
	}
	
	
	

}
