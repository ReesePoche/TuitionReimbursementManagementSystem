package servlets;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GoToRequestHandler {
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
	 *  selectedGradeId
	 * all i need for a bar app.
	 * all i need for a bar app.
	 * 
	 */
	private static boolean isLoggedin(HttpServletRequest request, HttpServletResponse response) throws IOException{
		HttpSession sess = request.getSession();
		if(sess.getAttribute("loggedInEmployeeID") == null) 
			return false;
		return true;
	}
	
	private static void loggout(HttpServletRequest request, HttpServletResponse response) throws IOException{
		HttpSession sess = request.getSession();
		sess.setAttribute("loggedInEmployeeID", null);
		sess.setAttribute("isBenCo", null);
		sess.setAttribute("selectedEmployeeId", null);
		sess.setAttribute("selectedPayoutId", null);
		sess.setAttribute("selectedRequestId", null);
		sess.setAttribute("selectedMessageId", null);
		sess.setAttribute("selectedGradeId", null);
		//selectedGradeId
		return;
	}
	
	private static void clearSelectedVariables(HttpServletRequest request, HttpServletResponse response) {
		HttpSession sess = request.getSession();
		sess.setAttribute("selectedEmployeeId", null);
		sess.setAttribute("selectedPayoutId", null);
		sess.setAttribute("selectedRequestId", null);
		sess.setAttribute("selectedMessageId", null);
		sess.setAttribute("selectedGradeId", null);
		return;
	}
	
	
	public static void process(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String uri = request.getRequestURI();
		
		System.out.println(uri + " in the goto request helper");
		
		switch (uri) {
			case "/dev.reese.project1/LoginPage.goto": {
				loggout(request, response);
				response.sendRedirect("LoginPage.html");
				break;
			}
			case "/dev.reese.project1/TRMSHub.goto": {
				if(!isLoggedin(request, response))
					response.sendRedirect("/dev.reese.project1/LoginPage.goto");
				else {
					clearSelectedVariables(request, response);
					response.sendRedirect("TRMSHub.html");
				}
				break;
			}
			case "/dev.reese.project1/SubmitForm.goto": {
				if(!isLoggedin(request, response))
					response.sendRedirect("/dev.reese.project1/LoginPage.goto");
				else {
					clearSelectedVariables(request, response);
					response.sendRedirect("TuitionReimbursementRequestSubmittionForm.html");
				}
				break;
			}
			case "/dev.reese.project1/Requests.goto": {
				if(!isLoggedin(request, response)) {
					response.sendRedirect("/dev.reese.project1/LoginPage.goto");
				}
				else {
					clearSelectedVariables(request, response);
					response.sendRedirect("SubmittedRequests.html");
				}
				break;
			}
			case "/dev.reese.project1/RequestViewer.goto": {
				if(!isLoggedin(request, response)) {
					response.sendRedirect("/dev.reese.project1/LoginPage.goto");
				}
				else {
					response.sendRedirect("RequestViewer.html");
				}
				break;
			}
			case "/dev.reese.project1/Payouts.goto": {
				if(!isLoggedin(request, response)) {
					response.sendRedirect("/dev.reese.project1/LoginPage.goto");
				}
				else {
					clearSelectedVariables(request, response);
					response.sendRedirect("ReimbursementPayouts.html");
				}
				break;
			}
			case "/dev.reese.project1/PayoutViewer.goto": {
				if(!isLoggedin(request, response)) {
					response.sendRedirect("/dev.reese.project1/LoginPage.goto");
				}
				else {
					response.sendRedirect("PayoutViewer.html");
				}
				break;
			}
			case "/dev.reese.project1/Messages.goto": {
				if(!isLoggedin(request, response))
					response.sendRedirect("LoginPage.html");
				else {
					clearSelectedVariables(request, response);
					response.sendRedirect("Messages.html");
				}
				break;
			}
			case "/dev.reese.project1/FeedbackMessage.goto": {
				if(!isLoggedin(request, response))
					response.sendRedirect("LoginPage.html");
				else {
					response.sendRedirect("FeedbackMessageViewer.html");
				}
				break;
			}
			case "/dev.reese.project1/ApprovalRequestMessage.goto": {
				if(!isLoggedin(request, response))
					response.sendRedirect("LoginPage.html");
				else {
					response.sendRedirect("ApprovalRequestMessageViewer.html");
				}
				break;
			}
			case "/dev.reese.project1/BenCoApprovalMessage.goto": {
				if(!isLoggedin(request, response))
					response.sendRedirect("LoginPage.html");
				else {
					response.sendRedirect("BenCoApprovalMessageViewer.html");
				}
				break;
			}
			case "/dev.reese.project1/ConfirmGradeMessage.goto": {
				if(!isLoggedin(request, response))
					response.sendRedirect("LoginPage.html");
				else {
					response.sendRedirect("ConfirmGradeMessageViewer.html");
				}
				break;
			}
			case "/dev.reese.project1/SubmitGradeMessage.goto": {
				if(!isLoggedin(request, response))
					response.sendRedirect("LoginPage.html");
				else {
					response.sendRedirect("SubmitGradeMessageViewer.html");
				}
				break;
			}
			case "/dev.reese.project1/ReplyAndNoticeMessage.goto": {
				if(!isLoggedin(request, response))
					response.sendRedirect("LoginPage.html");
				else {
					response.sendRedirect("ReplyAndNoticeMessageViewer.html");
				}
				break;
			}
			default: {
				response.sendError(418, "Default case hit. Cannot brew coffee, is teapot.");
				break;
			}
		}
	}
	

}
