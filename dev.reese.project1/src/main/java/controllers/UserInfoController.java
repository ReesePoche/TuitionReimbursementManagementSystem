package controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import dev.reese.project1.entities.Employee;
import dev.reese.project1.entities.GradeNote;
import dev.reese.project1.entities.ReimbursementMessage;
import dev.reese.project1.entities.ReimbursementPayout;
import dev.reese.project1.entities.RequestNote;
import dev.reese.project1.entities.SubmittedGrade;
import dev.reese.project1.entities.TuitionReimbursementRequest;
import serviceexceptions.EmployeeNotFoundException;
import services.LoginService;
import services.PayoutServices;
import services.TRFormSubmitionService;
import services.TRMSMessagingService;
import services.gradeSubmittionService;

public class UserInfoController {
	
	
	
	
	/**
	 * Server session attributes i will be using and what they mean
	 * 
	 * loggedInEmployeeID 
	 * => always an int the id of the person who is logged into the session
	 * null if noones is logged in
	 * 
	 * isBenCo
	 * stored at a T or F BenCo gets special privileges in certain parts
	 * 
	 * selectedEmployeeId
	 * an employee id that is selected for another use. like sender or reciever info
	 * 
	 * selectedPayoutId
	 * the id of a payout of interest
	 * 
	 * selectedRequestId
	 * the id of a trr or trrequest selected alternate between trr and request often
	 * 
	 * selectedMessageId
	 * the id of a message of interest
	 * 
	 * selectedGradeId
	 * all i need for a bar app.
	 * 
	 */
	
	
	
	
	private static LoginService ls = new LoginService();
	public static Gson gson = new Gson();
	private static TRFormSubmitionService TRFSS = new TRFormSubmitionService();
	private static PayoutServices ps = new PayoutServices();
	private static TRMSMessagingService ms = new TRMSMessagingService();
	private static gradeSubmittionService gss = new gradeSubmittionService();
	
	
	/**
	 * will add the logged in user's info to the response
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public static String getThisEmployeeInfo(HttpServletRequest request, HttpServletResponse response) throws IOException{
		System.out.println("in get this employee info");
		HttpSession sess = request.getSession();
		String id = sess.getAttribute("loggedInEmployeeID").toString();
		int employeeId = Integer.parseInt(id);
		Employee e = ls.getEmployee(employeeId);
		return (e != null) ?  gson.toJson(e) : "{}";
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public static String getThisEmployeeRequests(HttpServletRequest request, HttpServletResponse response) throws IOException{
		System.out.println("in get this employee requests");
		HttpSession sess = request.getSession();
		String id = sess.getAttribute("loggedInEmployeeID").toString();
		int employeeId = Integer.parseInt(id);
		List<TuitionReimbursementRequest> trrs = TRFSS.getAllRequestsForEmployee(employeeId);
		if(trrs.size() > 0) {
			StringBuilder sb = new StringBuilder();
			sb.append("[");
			for(int i = 0; i < trrs.size() -1; i++) {
				sb.append(gson.toJson(trrs.get(i)));
				sb.append(",");
			}
			sb.append(gson.toJson(trrs.get(trrs.size()-1)));
			sb.append("]");
			return sb.toString();
		}
		else {
			return "[]";
		}
	}
	
	/**
	 * adds a json list of payouts to the response
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public static String getThisEmployeePayouts(HttpServletRequest request, HttpServletResponse response) throws IOException{
		System.out.println("in get this employee requests");
		HttpSession sess = request.getSession();
		String id = sess.getAttribute("loggedInEmployeeID").toString();
		int employeeId = Integer.parseInt(id);
		List<ReimbursementPayout> payouts = ps.getEmployeePayouts(employeeId);
		if( payouts != null && payouts.size() > 0 ) {
			StringBuilder sb = new StringBuilder();
			sb.append("[");
			for(int i = 0; i < payouts.size() -1; i++) {
				sb.append(gson.toJson(payouts.get(i)));
				sb.append(",");
			}
			sb.append(gson.toJson(payouts.get(payouts.size()-1)));
			sb.append("]");
			return sb.toString();
		}
		else
			return "[]";
	}
	
	/**
	 * returns all Messages both send and recieved by this employee
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public static String getThisEmployeeRecievedMessages(HttpServletRequest request, HttpServletResponse response) throws IOException{
		System.out.println("in get this employee requests");
		HttpSession sess = request.getSession();
		String id = sess.getAttribute("loggedInEmployeeID").toString();
		int employeeId = Integer.parseInt(id);
		List<ReimbursementMessage> messages = null;
		try {
			messages = ms.getAllMessagesForEmployee(employeeId);
		} 
		catch (EmployeeNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(messages.size() > 0) {
			StringBuilder sb = new StringBuilder();
			sb.append("[");
			for(int i = 0; i < messages.size() -1; i++) {
				sb.append(gson.toJson(messages.get(i)));
				sb.append(",");
			}
			sb.append(gson.toJson(messages.get(messages.size()-1)));
			sb.append("]");
			System.out.println("in the get recieved message user info controller it will return " + sb.toString());
			return sb.toString();
		}
		else
			return "[]";
	}
	
	public static String getTheSelectedEmployee(HttpServletRequest request, HttpServletResponse response) throws IOException{ 
		HttpSession sess = request.getSession();
		if(sess.getAttribute("selectedEmployeeId") == null)
			return "{}";
		String requestIdString = sess.getAttribute("selectedEmployeeId").toString();
		int requestId = Integer.parseInt(requestIdString);
		Employee e = ls.getEmployee(requestId);
		return (e != null) ? gson.toJson(e) : "{}";
	}
	
	
	
	public static String getTheSelectedRequest(HttpServletRequest request, HttpServletResponse response) throws IOException{
		HttpSession sess = request.getSession();
		String requestIdString = sess.getAttribute("selectedRequestId").toString();
		int requestId = Integer.parseInt(requestIdString);
		TuitionReimbursementRequest trr = TRFSS.getRequest(requestId);
		return (trr != null) ? gson.toJson(trr) : "{}";
	}
	
	public static String getTheSelectedRequestNotes(HttpServletRequest request, HttpServletResponse response) throws IOException{
		HttpSession sess = request.getSession();
		String requestIdString = sess.getAttribute("selectedRequestId").toString();
		int requestId = Integer.parseInt(requestIdString); //getRequestFormNotesNoCheck
		List<RequestNote> notes = TRFSS.getRequestFormNotesNoCheck(requestId);
		
		for(int i = 0; i < notes.size(); i++) {
			RequestNote note = notes.get(i);
			if(note.getAddedBy() != -1) {
				Employee e = ls.getEmployee(note.getAddedBy());
				note.setPosterName(e.getFirstName() + " " + e.getLastName());       
			}
		}
		if(notes.size() > 0) {
			StringBuilder sb = new StringBuilder();
			sb.append("[");
			for(int i = 0; i < notes.size() -1; i++) {
				sb.append(gson.toJson(notes.get(i)));
				sb.append(",");
			}
			sb.append(gson.toJson(notes.get(notes.size()-1)));
			sb.append("]");
			return sb.toString();
		}
		else return "[]";
		
	}
	
	public static String getTheSelectedGrade(HttpServletRequest request, HttpServletResponse response) throws IOException{
		HttpSession sess = request.getSession();
		String gradeIdString = sess.getAttribute("selectedGradeId").toString();
		if(gradeIdString == null)
			return "{}";
		int gradeId = Integer.parseInt(gradeIdString);
		SubmittedGrade sg = gss.retrieveSubmittedGrade(gradeId);
		return (sg != null) ? gson.toJson(sg) : "{}";
	}
	
	
	
	public static String getTheSelectedPayout(HttpServletRequest request, HttpServletResponse response) throws IOException{
		HttpSession sess = request.getSession();
		String payoutIdString = sess.getAttribute("selectedPayoutId").toString();
		int payoutId = Integer.parseInt(payoutIdString);
		ReimbursementPayout rp = ps.getPayout(payoutId);
		return (rp != null) ? gson.toJson(rp) : "{}";
	}
	
	public static String getTheSelectedRequestsGradeAndNotes(HttpServletRequest request, HttpServletResponse response) throws IOException{
		HttpSession sess = request.getSession();
		String payoutIdString = sess.getAttribute("selectedRequestId").toString();
		int requestId = Integer.parseInt(payoutIdString);
		SubmittedGrade sg = gss.retrieveRequestsSubmittedGradeWithAttachments(requestId);
		if(sg == null ) {
			return "[]";
		}
		List<GradeNote> notes = sg.getNotes();
		sg.setNotes(null);
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append(gson.toJson(sg));
		sb.append(",");
		if(notes.size() > 0) {
			sb.append("[");
			for(int i = 0; i < notes.size()-1; i++) {
				sb.append(gson.toJson(notes.get(i)));
				sb.append(",");
			}
			sb.append(gson.toJson(notes.get(notes.size()-1)));
			sb.append("]");
		}
		else {
			sb.append("[]");
		}
		sb.append("]");
		
		return sb.toString();
	}
	
	public static String getTheTotalPayoutsPendingAndAwardedThisYear(HttpServletRequest request, HttpServletResponse response) throws IOException{
		HttpSession sess = request.getSession();
		String id = sess.getAttribute("loggedInEmployeeID").toString();
		int employeeId = Integer.parseInt(id);
		double total = ps.getTotalOfEmployeePayoutsForYear(employeeId, 2021);
		return String.format("%.2f", total);
	}
	
	
	
	public static String getTheSelectedRecievedMessage(HttpServletRequest request, HttpServletResponse response) throws IOException{
		HttpSession sess = request.getSession();
		String requestIdString = sess.getAttribute("selectedMessageId").toString();
		int MessageId = Integer.parseInt(requestIdString);
		ReimbursementMessage rm = ms.getMessage(MessageId);
		TuitionReimbursementRequest trr = TRFSS.getRequest(rm.getRequestId());
		if(rm.getSenderId() != -1) {
			setSelectedEmployeeIdVariable(request, response, rm.getSenderId());
		}
		else if(rm.getTypeOfMessage().equals("AR") || rm.getTypeOfMessage().equals("GCR")) {
			setSelectedEmployeeIdVariable(request, response, trr.getForEmployee());
		}
			
		setSelectedRequestVariable(request, response, rm.getRequestId());
		if(rm.getTypeOfMessage().equals("GCR")) {
			SubmittedGrade sg = gss.retrieveRequestsSubmittedGradeNoAttachments(rm.getRequestId());
			setSelectedGradeVariable(request, response, sg.getId());
		}
		return (rm != null) ? gson.toJson(rm) : "{}";
	}
	
	public static String getSelectedEmployeePayouts(HttpServletRequest request, HttpServletResponse response) throws IOException{
		HttpSession sess = request.getSession();
		if(sess.getAttribute("selectedEmployeeId") == null)
			return "[]";
		String eIdString = sess.getAttribute("selectedEmployeeId").toString();
		if(eIdString == null)
			return "[]";
		int employeeId = Integer.parseInt(eIdString);
		Employee e = ls.getEmployee(employeeId);
		List<ReimbursementPayout> payouts = ps.getEmployeePayouts(employeeId);
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for(int i = 0; i < payouts.size() -1; i++) {
			sb.append(gson.toJson(payouts.get(i)));
			sb.append(",");
		}
		sb.append(gson.toJson(payouts.get(payouts.size()-1)));
		sb.append("]");
		return sb.toString();
	}
	
	public static void forwardToRightPageInfo(HttpServletRequest request, HttpServletResponse response) throws IOException{
		HttpSession sess = request.getSession();
		String requestIdString = sess.getAttribute("selectedMessageId").toString();
		int MessageId = Integer.parseInt(requestIdString);
		ReimbursementMessage rm = ms.getMessage(MessageId);
		TuitionReimbursementRequest trr = TRFSS.getRequest(rm.getRequestId());
		String id = sess.getAttribute("loggedInEmployeeID").toString();
		int employeeId = Integer.parseInt(id);
		Employee e = ls.getEmployee(employeeId);
		Employee bc = ls.getBenCo();
		String pathOfForward = null;
		if(rm.getTypeOfMessage().equals("FR") || rm.getTypeOfMessage().equals("FRR") || rm.getTypeOfMessage().equals("PAN") || rm.getTypeOfMessage().equals("RAN") 
			|| rm.getTypeOfMessage().equals("RDN")) {
			pathOfForward = "/dev.reese.project1/FBRequestAndNoticeMessageViewer.pageinfo";
		}
		if(rm.getTypeOfMessage().equals("AR")) {
			if(trr.isHasSupervisorApproval() && trr.isHasDepartmentHeadApproval() && !trr.isHasBenCoApproval() && e.getId() == bc.getId()) {
				///dev.reese.project1/BenCoApprovalMessageViewer.pageinfo
				pathOfForward = "/dev.reese.project1/BenCoApprovalMessageViewer.pageinfo";
			}
			else {
				pathOfForward = "/dev.reese.project1/SVorDHApprovalMessageViewer.pageinfo";
			}
		}
	
		if(rm.getTypeOfMessage().equals("GSR")) {
			pathOfForward = "/dev.reese.project1/GradeSubmittionMessageViewer.pageinfo";
		}
		if(rm.getTypeOfMessage().equals("GCR")) {
			pathOfForward = "/dev.reese.project1/GradeConfirmationMessageViewer.pageinfo";
		}
		if (pathOfForward == null) {
			response.sendError(418);
		}
		response.getWriter().append(pathOfForward);
	}
	
	
	
	
	
	
	private static void setSelectedEmployeeIdVariable(HttpServletRequest request, HttpServletResponse response, int selectedEmployeeId) throws IOException{
		HttpSession sess = request.getSession();
		sess.setAttribute("selectedEmployeeId", Integer.toString(selectedEmployeeId));
		return;
	}
	
	private static void setSelectedRequestVariable(HttpServletRequest request, HttpServletResponse response, int selectedRequestId) throws IOException{
		HttpSession sess = request.getSession();
		sess.setAttribute("selectedRequestId", Integer.toString(selectedRequestId));
		return;
	}
	
	private static void setSelectedGradeVariable(HttpServletRequest request, HttpServletResponse response, int selectedGradeId) throws IOException{
		HttpSession sess = request.getSession();
		sess.setAttribute("selectedGradeId", Integer.toString(selectedGradeId));
		return;
	}
	
	//selectedGradeId

	
	
	
		
		
		
//		String id = sess.getAttribute("loggedInEmployeeID").toString();
//		int employeeId = Integer.parseInt(id);
//		
//		TuitionReimbursementRequest trr = null;
//		StringBuilder sb = new StringBuilder();
//	    BufferedReader reader = request.getReader();
//	    try {
//	        String line;
//	        while ((line = reader.readLine()) != null) {
//	            sb.append(line).append('\n');
//	        }
//	    } 
//	    finally {
//	        reader.close();
//	    }
//		System.out.println(sb.toString());
//		sess.setAttribute("trrId", sb.toString());
//		response.getWriter().append("SR");
		//int requestId = Integer.parseInt(sb.toString());
//		trr = TRFSS.getRequest(requestId);
//		Employee e = ls.getEmployee(trr.getForEmployee());
//		response.getWriter().append((e != null) ? gson.toJson(e) : "{}");
//		response.getWriter().append((trr != null) ? gson.toJson(trr) : "{}");
		
	
	
	
//	public static void ShowTheRequestToEmployee(HttpServletRequest request, HttpServletResponse response) throws IOException{
//		HttpSession sess = request.getSession();
//		String stringid = sess.getAttribute("trrId").toString();
//		int requestId = Integer.parseInt(stringid);
//		TuitionReimbursementRequest trr = TRFSS.getRequest(requestId);
//		Employee e = ls.getEmployee(trr.getForEmployee());
//		response.getWriter().append((e != null) ? gson.toJson(e) : "{}");
//		response.getWriter().append((trr != null) ? gson.toJson(trr) : "{}");
//	}
	
//	public static void goToViewFormPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
//		
//	}
	
	
	

}
