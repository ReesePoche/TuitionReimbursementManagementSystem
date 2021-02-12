package controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import dev.reese.project1.entities.Department;
import dev.reese.project1.entities.Employee;
import dev.reese.project1.entities.ReimbursementMessage;
import dev.reese.project1.entities.ReimbursementPayout;
import dev.reese.project1.entities.SubmittedGrade;
import dev.reese.project1.entities.TuitionReimbursementRequest;
import serviceexceptions.RequestNotFoundException;
import services.LoginService;
import services.PayoutServices;
import services.TRFormSubmitionService;
import services.TRMSMessagingService;
import services.gradeSubmittionService;

public class FormSubmittingController {
	
	private static LoginService ls = new LoginService();
	private static TRMSMessagingService ms = new TRMSMessagingService();
	private static TRFormSubmitionService trfss = new TRFormSubmitionService();
	public static Gson gson = new Gson();
	private static PayoutServices ps = new PayoutServices();
	private static gradeSubmittionService gs = new gradeSubmittionService();
	
	public static void SubmitTRRForm(HttpServletRequest request, HttpServletResponse response) throws IOException{
		HttpSession sess = request.getSession();
		String id = sess.getAttribute("loggedInEmployeeID").toString();
		int employeeId = Integer.parseInt(id);
		StringBuilder sb = new StringBuilder();
	    BufferedReader reader = request.getReader();
	    try {
	        String line;
	        while ((line = reader.readLine()) != null) {
	            sb.append(line).append('\n');
	        }
	    } 
	    finally {
	        reader.close();
	    }
	    JsonObject jsonFormObject = new Gson().fromJson(sb.toString(), JsonObject.class);
	    System.out.println(sb.toString());
	    TuitionReimbursementRequest trr = new TuitionReimbursementRequest();
	    trr.setForEmployee(employeeId);
	    trr.setEventDescription(jsonFormObject.get("description").getAsString());
	    trr.setWorkRelatedJustification(jsonFormObject.get("workJustification").getAsString());
	    trr.setEventType(jsonFormObject.get("typeOfEvent").getAsString());
	    trr.setEventLocation(jsonFormObject.get("location").getAsString());
	    
	    String costString = jsonFormObject.get("cost").getAsString();
	    trr.setTotalCost(Double.parseDouble(costString));
	    String formatString = jsonFormObject.get("gradeFormat").getAsString();
	    trr.setGradeFormat(Integer.parseInt(formatString));
	    if(trr.getGradeFormat() == 1 || trr.getGradeFormat() == 2)
	    	trr.setScoreRequired(100);
	    else {
	    	String scoreString =  jsonFormObject.get("scoreNeeded").getAsString();
	    	trr.setScoreRequired(Integer.parseInt(scoreString));
	    }
	    String hourOfWorkString = jsonFormObject.get("HoursOfWork").getAsString();
	    double hoursOfWork = Double.parseDouble(hourOfWorkString);
	    if(hoursOfWork <= 0.00)
	    	trr.setHoursOfWorkMissed(0.00);
	    else
	    	trr.setHoursOfWorkMissed(hoursOfWork);
	    String orginalTimeStamp = jsonFormObject.get("dateTimeOfEvent").getAsString();
	    StringBuilder sbtest = new StringBuilder(orginalTimeStamp);
	    sbtest.setCharAt(10, ' ');
	    sbtest.append(":00");
	    System.out.println(sbtest.toString());
	    trr.setStartDateTimeOfEvent(Timestamp.valueOf(sbtest.toString()));
	    trr.setstatusOfRequest("PROS");
	    trr = trfss.submitRequest(trr);
	    System.out.println(trr.getId());
	    sess.setAttribute("SucessfulFormSubmit", "T");
	    processAndSendInitalApprovalMessage(trr);
	    response.getWriter().append("SS");
	}
	
	private static void processAndSendInitalApprovalMessage(TuitionReimbursementRequest trr) {
		Employee employeeSubmitting = ls.getEmployee(trr.getForEmployee());
		int supervisorID = employeeSubmitting.getSupervisorId();
		Employee departmentHead = ls.getEmployeeDepartmentHead(employeeSubmitting.getId());
		if(supervisorID == 0 || supervisorID == -1 || departmentHead.getId() == supervisorID) {
			trr = trfss.MarkRequestAsApprovedBySV(trr.getId());
		}
		if(employeeSubmitting.getId() == departmentHead.getId()) {
			trr = trfss.MarkRequestAsApprovedByDH(supervisorID);
		}
		if(!trr.isHasSupervisorApproval()) {
			//send sv the message
			ReimbursementMessage message = ms.sendSVApprovalRequest(trr.getId());
			trr = trfss.MarkRequestPendingSVApproval(trr.getId());
		}
		else if(!trr.isHasDepartmentHeadApproval()) {
			//sned dh the message
			ReimbursementMessage message = ms.sendDHApprovalRequest(trr.getId());
			trr = trfss.MarkRequestPendingDHApproval(trr.getId());
		}
		else {
			//send benco the message
			ReimbursementMessage message = ms.sendBenCoApprovalRequest(trr.getId());
			trr = trfss.MarkRequestPendingBenCoApproval(trr.getId());
		}
		
	}

	
	
	public static void replyToNotice(HttpServletRequest request, HttpServletResponse response) throws IOException{
		HttpSession sess = request.getSession();
		String id = sess.getAttribute("loggedInEmployeeID").toString();
		int employeeId = Integer.parseInt(id);
		StringBuilder sb = new StringBuilder();
	    BufferedReader reader = request.getReader();
	    try {
	        String line;
	        while ((line = reader.readLine()) != null) {
	            sb.append(line).append('\n');
	        }
	    } 
	    finally {
	        reader.close();
	    }
	    JsonObject jsonFormObject = new Gson().fromJson(sb.toString(), JsonObject.class);
	    System.out.println(sb.toString());
	    String messageIdString = jsonFormObject.get("messageId").getAsString();
	    int messageId = Integer.parseInt(messageIdString);
	    ms.markNoticeEmailAsConfimed(messageId);
	}
	
	public static void replyToFBRequest(HttpServletRequest request, HttpServletResponse response) throws IOException{
		HttpSession sess = request.getSession();
		String id = sess.getAttribute("loggedInEmployeeID").toString();
		int employeeId = Integer.parseInt(id);
		StringBuilder sb = new StringBuilder();
	    BufferedReader reader = request.getReader();
	    try {
	        String line;
	        while ((line = reader.readLine()) != null) {
	            sb.append(line).append('\n');
	        }
	    } 
	    finally {
	        reader.close();
	    }
	    JsonObject jsonFormObject = new Gson().fromJson(sb.toString(), JsonObject.class);
	    System.out.println(sb.toString());
	    String messageIdString = jsonFormObject.get("messageId").getAsString();
	    int messageId = Integer.parseInt(messageIdString);
	    String bodyOfMessage = jsonFormObject.get("bodyOfMessage").getAsString();
	    ms.ReplyToFeedBackRequest(messageId, bodyOfMessage);
	    //now resend approval message
	    ReimbursementMessage message = ms.getMessage(messageId);
	    int requestId = message.getRequestId();
		TuitionReimbursementRequest trr = trfss.getRequest(requestId);
		String trrStatus = trr.getstatusOfRequest();
		sendApprovalMessage(trrStatus, requestId);
	}

	
	
	
	public static void approvalRequestResponse(HttpServletRequest request, HttpServletResponse response) throws IOException{
		HttpSession sess = request.getSession();
		String id = sess.getAttribute("loggedInEmployeeID").toString();
		int employeeId = Integer.parseInt(id);
		StringBuilder sb = new StringBuilder();
	    BufferedReader reader = request.getReader();
	    try {
	        String line;
	        while ((line = reader.readLine()) != null) {
	            sb.append(line).append('\n');
	        }
	    } 
	    finally {
	        reader.close();
	    }
	    System.out.println(sb.toString());
	    JsonObject jsonFormObject = new Gson().fromJson(sb.toString(), JsonObject.class);
	    System.out.println(sb.toString());
	    String messageResponse = jsonFormObject.get("messageResponse").getAsString();
	    if(messageResponse.equals("A")) {
	    	handleApproval(request, response, jsonFormObject);
	    }
	    else if(messageResponse.equals("D")) {
	    	handleApprovalDenial(request, response, jsonFormObject);
	    }
	    else if(messageResponse.equals("RF")) {
	    	handleFBrequest(request, response, jsonFormObject);
	    }
	}
	
	private static void handleApproval(HttpServletRequest request, HttpServletResponse response, JsonObject  jsonFormObject) throws IOException{
		String messageIdString = jsonFormObject.get("messageId").getAsString();
		int messageId = Integer.parseInt(messageIdString);
		ReimbursementMessage message = ms.getMessage(messageId);
		message = ms.markAsApproved(message);
		message = ms.markAsResponded(message);
		//orginal message taken care of now handle who will get it..
		//lets get the trr
		int requestId = message.getRequestId();
		TuitionReimbursementRequest trr = trfss.getRequest(requestId);
		String trrStatus = trr.getstatusOfRequest();
		if(trrStatus.equals("PSVA") || trrStatus.equals("PROS")) {
			trr = trfss.MarkRequestAsApprovedBySV(requestId);
			if(trr.isHasDepartmentHeadApproval()) {
				// go to the benco
				ms.sendBenCoApprovalRequest(requestId);
				trr = trfss.MarkRequestPendingBenCoApproval(requestId);
			}
			else {
				//go to the DH
				ms.sendDHApprovalRequest(requestId);
				trr = trfss.MarkRequestPendingDHApproval(requestId);
			}
		}
		else if(trrStatus.equals("PDHA")) {
			trr = trfss.MarkRequestAsApprovedByDH(requestId);
			ms.sendBenCoApprovalRequest(requestId);
			trr = trfss.MarkRequestPendingBenCoApproval(requestId);
		}
		else if(trrStatus.equals("PBCA")) {
			trr = trfss.MarkRequestAsApprovedByBenCo(requestId);
			String amountString = jsonFormObject.get("payoutAmount").getAsString();
			String payoutNotes = jsonFormObject.get("payoutNotes").getAsString();
			double bcAmount = Double.parseDouble(amountString);
			double usualAmount = getUsualAmountReimbursed(trr);
			double difference = bcAmount - usualAmount;
			boolean needsEmployeeApproval = false;
			if(difference < 0.01 && difference > -0.01) {
				//its fine everything is as expected :)
				//make payout, notice employee, send grade submittion request. 
				needsEmployeeApproval = false;
			}
			else {
				needsEmployeeApproval = true;
			}
			String fullDate = trr.getStartDateTimeOfEvent().toString();
			String yearString = fullDate.substring(0, 4);
			int year = Integer.parseInt(yearString);		
			ps.createPendingPayoutWithpara(requestId, bcAmount, year, needsEmployeeApproval, payoutNotes);
			if(needsEmployeeApproval) {
				trr = trfss.MarkRequestAsWaitingOnFBFromEmployee(requestId);
				ms.sendEmployeeFinalApprovalRequest(requestId);
			}
			else {
				trr = trfss.MarkRequestPreApprovedPendingGrading(requestId);
				ms.sendEmployeePreApprovalNotice(requestId);
				ms.sendGradeSubmissionRequest(requestId);
			}
		}
		else if(trrStatus.equals("PEFB")) {
			trr = trfss.MarkRequestPreApprovedPendingGrading(requestId);
			ps.employeeApprovedMarkAsPendingGrading(requestId);
			ms.sendEmployeeFinalApprovalRequest(requestId);
			ms.sendGradeSubmissionRequest(requestId);
		}
		
		
		
		
		
		
	}
	
	private static double getUsualAmountReimbursed(TuitionReimbursementRequest trr) {
		String eventType = trr.getEventType();
		if(eventType.equals("UC")) {
			return .80*trr.getTotalCost();
		}
		if(eventType.equals("S")) {
			return .60*trr.getTotalCost();
		}
		if(eventType.equals("C")) {
			return trr.getTotalCost();
		}
		if(eventType.equals("CPC")) {
			return .75*trr.getTotalCost();
		}
		if(eventType.equals("TT")) {
			return .90*trr.getTotalCost();
		}
		if(eventType.equals("O")) {
			return .30*trr.getTotalCost();
		}
		return 0.00;
	}
	
	
	
	
	
	
	private static void sendApprovalMessage(String trrStatus, int requestId) {
		if(trrStatus.equals("PSVA")) {
			ms.sendSVApprovalRequest(requestId);
		}
		else if(trrStatus.equals("PDHA")) {
			ms.sendDHApprovalRequest(requestId);
		}
		else if(trrStatus.equals("PBCA")) {
			ms.sendBenCoApprovalRequest(requestId);
		}
		else if(trrStatus.equals("PEFB")) {
			ms.sendEmployeeFinalApprovalRequest(requestId);
		}
	}
	
	public static void handleApprovalDenial(HttpServletRequest request, HttpServletResponse response, JsonObject  jsonFormObject) throws IOException{
		String messageIdString = jsonFormObject.get("messageId").getAsString();
		int messageId = Integer.parseInt(messageIdString);
		ReimbursementMessage message = ms.getMessage(messageId);
		message = ms.markAsDenied(message);
		message = ms.markAsResponded(message);
		//orginal message taken care of now handle who will get it..
		//lets get the trr
		int requestId = message.getRequestId();
		TuitionReimbursementRequest trr = trfss.getRequest(requestId);
		String trrStatus = trr.getstatusOfRequest();
		if(trrStatus.equals("PSVA") || trrStatus.equals("PDHA") || trrStatus.equals("PBCA")) {
			String reason = jsonFormObject.get("reason").getAsString();
			ms.sendEmployeeDenialNotice(requestId, reason);
		}
		else {
			//means it is a denial by employee themselves
			ms.sendEmployeeDenialNotice(requestId, "Employee rejected BenCo's offer\n");
			ps.EDeniedOrFailingGradeDeletePayoutForRequest(requestId);
		}
		trr = trfss.MarkRequestAsDenied(requestId);
	}
	
	public static void handleFBrequest(HttpServletRequest request, HttpServletResponse response, JsonObject  jsonFormObject) throws IOException{
		String messageIdString = jsonFormObject.get("messageId").getAsString();
		int messageId = Integer.parseInt(messageIdString);
		ReimbursementMessage message = ms.getMessage(messageId);
		message = ms.markAsRequestedFeedback(message);
		message = ms.markAsResponded(message);
		//orginal message taken care of now handle who will get it..
		//lets get the trr
		int requestId = message.getRequestId();
		String fbMessageBody = jsonFormObject.get("fbMessageBody").getAsString();
		String sendTo = jsonFormObject.get("sendTo").getAsString();
		if(sendTo.equals("E")) {
			ms.sendRequestForFeedBackToEmployee(messageId, fbMessageBody);
		}
		else if(sendTo.equals("SV")) {
			ms.sendRequestForFeedBackToSV(messageId, fbMessageBody);
		}
		else if(sendTo.equals("DH")) {
			ms.sendRequestForFeedBackToDH(messageId, fbMessageBody);
		}
		
	}
	
	
	
	public static void handleGradeSubmittion(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession sess = request.getSession();
		String id = sess.getAttribute("loggedInEmployeeID").toString();
		int employeeId = Integer.parseInt(id);
		StringBuilder sb = new StringBuilder();
	    BufferedReader reader = request.getReader();
	    try {
	        String line;
	        while ((line = reader.readLine()) != null) {
	            sb.append(line).append('\n');
	        }
	    } 
	    finally {
	        reader.close();
	    }
	    JsonObject jsonFormObject = new Gson().fromJson(sb.toString(), JsonObject.class);
	    System.out.println(sb.toString());
	    String requestIdString = jsonFormObject.get("requestId").getAsString();
	    String reportedScoreString = jsonFormObject.get("reportedScore").getAsString();
	    int requestId = Integer.parseInt(requestIdString);
	    int reportedScore = Integer.parseInt(reportedScoreString);
	    String noteSubject = jsonFormObject.get("noteSubject").getAsString();
	    String note = jsonFormObject.get("note").getAsString();
	    boolean hasNote = note != null;
	    SubmittedGrade sg = gs.submitGrade(requestId, reportedScore);
	    if(hasNote) {
	    	gs.attachNoteToGrade(sg.getId(), employeeId, noteSubject, note);
	    }
	    //grade submitted, now mark message as replied
	    String messageIdString = jsonFormObject.get("messageId").getAsString();
	    int messageId = Integer.parseInt(messageIdString);
	    ms.markNoticeEmailAsGradeSubmittedAndReplied(messageId);
	    
	    //BIG CHAGE LETS SEE IF THEY PASSED WITH WHAT THEY SUBMITED
	    ReimbursementMessage rm = ms.getMessage(messageId);
	    TuitionReimbursementRequest trr = trfss.getRequest(rm.getRequestId());
	    if(sg.getEmployeeSelfReportedScore() >= trr.getScoreRequired()) {
	    	gs.MarkSubmittionAsReviewedAndPassed(sg.getId(), employeeId);
	    	//next deal with trr itself
	    	trfss.MarkRequestAsApproved(trr.getId());
	    	//next the payout
	    	ps.GradePassingMakePayment(trr.getId());
	    	//now notify the employee
	    	ms.sendEmployeeReimbursementAwardedNotice(trr.getId());
	    }
	    else {
	    	gs.MarkSubmittionAsReviewedAndFailed(sg.getId(), employeeId);
	    	// deal with trr itself
	    	trfss.MarkRequestAsDenied(trr.getId());
	    	//next payout
	    	ps.EDeniedOrFailingGradeDeletePayoutForRequest(trr.getId());
	    	//now notify employee
	    	ms.sendEmployeeDenialNotice(trr.getId(), "You scored too low to get the payout");
	    }
	    
	    
	    
	    
	    
	    //now to change the trr
//	    TuitionReimbursementRequest trr = trfss.getRequest(requestId);
//	    trfss.MarkRequestAsApprovedPendingGradingConfimation(requestId);
//	    // now to send the message
//	    if(trr.getGradeFormat() != 2) {
//	    	ms.sendRequestForBenCoToConfirmGrade(requestId);
//	    }
//	    else {
//	    	ms.sendRequestForSVToSeePresentation(requestId);
//	    }
	    
	    
	}
	
	
	
	public static void handleGradeConfirmation(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession sess = request.getSession();
		String id = sess.getAttribute("loggedInEmployeeID").toString();
		int employeeId = Integer.parseInt(id);
		StringBuilder sb = new StringBuilder();
	    BufferedReader reader = request.getReader();
	    try {
	        String line;
	        while ((line = reader.readLine()) != null) {
	            sb.append(line).append('\n');
	        }
	    } 
	    finally {
	        reader.close();
	    }
	    JsonObject jsonFormObject = new Gson().fromJson(sb.toString(), JsonObject.class);
	    System.out.println(sb.toString());
	    String messageIdString = jsonFormObject.get("messageId").getAsString();
	    int messageId = Integer.parseInt(messageIdString);
	    String messageResponse = jsonFormObject.get("messageResponse").getAsString();
	    String reason = jsonFormObject.get("reason").getAsString();
	    ReimbursementMessage rm = ms.getMessage(messageId);
	    TuitionReimbursementRequest trr = trfss.getRequest(rm.getRequestId());
	    SubmittedGrade sg = gs.retrieveRequestsSubmittedGradeWithAttachments(trr.getId());
	    if(messageResponse.equals("A")) {
	    	//first deal with the message
	    	rm = ms.markAsApproved(rm);
	    	rm = ms.markAsResponded(rm);
	    	//change the grade itself
	    	gs.MarkSubmittionAsReviewedAndPassed(sg.getId(), employeeId);
	    	//next deal with trr itself
	    	trfss.MarkRequestAsApproved(trr.getId());
	    	//next the payout
	    	ps.GradePassingMakePayment(trr.getId());
	    	//now notify the employee
	    	ms.sendEmployeeReimbursementAwardedNotice(trr.getId());
	    }
	    else {
	    	//deal with message
	    	rm = ms.markAsDenied(rm);
	    	rm = ms.markAsResponded(rm);
	    	//deal with grade itself
	    	gs.MarkSubmittionAsReviewedAndFailed(sg.getId(), employeeId);
	    	// deal with trr itself
	    	trfss.MarkRequestAsDenied(trr.getId());
	    	//next payout
	    	ps.EDeniedOrFailingGradeDeletePayoutForRequest(trr.getId());
	    	//now notify employee
	    	ms.sendEmployeeDenialNotice(trr.getId(), reason);
	    }
	}
	
	
}
