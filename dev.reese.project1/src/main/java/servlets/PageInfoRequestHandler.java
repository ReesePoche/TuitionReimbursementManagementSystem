package servlets;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controllers.UserInfoController;

public class PageInfoRequestHandler {
	
	private static boolean isLoggedin(HttpServletRequest request, HttpServletResponse response) throws IOException{
		HttpSession sess = request.getSession();
		if(sess.getAttribute("loggedInEmployeeID") == null) 
			return false;
		return true;
	}
	
	
	
	
	public static void process(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String uri = request.getRequestURI();
		
		System.out.println(uri + " in the request helper");
		
		switch (uri) {
			/*
			 * cases here would be... 
			 * 1) info on user
			 * 2) info to get all requests with relivant info attached
			 * 3) info to get all payouts with relivant info attached
			 * 4) info to get all messages with relivant info attached
			 * 
			 */
		case "/dev.reese.project1/Hubinfo.pageinfo": {
			if(!isLoggedin(request, response))
				response.sendError(401, "not loggin in  for to /dev.reese.project1/LoginPage.goto to login");
			else {
				String einfo = UserInfoController.getThisEmployeeInfo(request, response);
				response.getWriter().append(einfo);
			}
			break;
		}
		case "/dev.reese.project1/Requests.pageinfo": {
			if(!isLoggedin(request, response))
				response.sendError(401, "not loggin in  for to /dev.reese.project1/LoginPage.goto to login");
			else {
				StringBuilder sb = new StringBuilder();
				sb.append("[");
				sb.append(UserInfoController.getThisEmployeeInfo(request, response));
				sb.append(",");
				sb.append(UserInfoController.getThisEmployeeRequests(request, response));
				sb.append("]");
				response.getWriter().append(sb.toString());
			}
			break;
		}
		case "/dev.reese.project1/RequestViewer.pageinfo": {
			if(!isLoggedin(request, response))
				response.sendError(401, "not loggin in  for to /dev.reese.project1/LoginPage.goto to login");
			else {
				StringBuilder sb = new StringBuilder();
				sb.append("[");
				sb.append(UserInfoController.getThisEmployeeInfo(request, response));
				sb.append(",");
				sb.append(UserInfoController.getTheSelectedRequest(request, response));
				sb.append(",");
				sb.append(UserInfoController.getTheSelectedRequestNotes(request, response));
				sb.append(",");
				sb.append(UserInfoController.getTheSelectedRequestsGradeAndNotes(request, response));
				sb.append("]");
				response.getWriter().append(sb.toString());
			}
			break;
		}
		case "/dev.reese.project1/Payouts.pageinfo": {
			if(!isLoggedin(request, response))
				response.sendError(401, "not loggin in  for to /dev.reese.project1/LoginPage.goto to login");
			else {
				StringBuilder sb = new StringBuilder();
				sb.append("[");
				sb.append(UserInfoController.getThisEmployeeInfo(request, response));
				sb.append(",");
				sb.append(UserInfoController.getThisEmployeePayouts(request, response));
				sb.append(",");
				sb.append( UserInfoController.getTheTotalPayoutsPendingAndAwardedThisYear(request, response));
				sb.append("]");
				response.getWriter().append(sb.toString());
			}
			break;
		}
		case "/dev.reese.project1/Messages.pageinfo": {
			if(!isLoggedin(request, response))
				response.sendError(401, "not loggin in  for to /dev.reese.project1/LoginPage.goto to login");
			else {
				StringBuilder sb = new StringBuilder();
				sb.append("[");
				sb.append(UserInfoController.getThisEmployeeInfo(request, response));
				sb.append(",");
				sb.append(UserInfoController.getThisEmployeeRecievedMessages(request, response));
				sb.append("]");
				response.getWriter().append(sb.toString());
			}
			break;
		}
		case "/dev.reese.project1/MessageViewer.pageinfo": {
			if(!isLoggedin(request, response))
				response.sendError(401, "not loggin in  for to /dev.reese.project1/LoginPage.goto to login");
			else {
				UserInfoController.forwardToRightPageInfo(request, response);
			}
			break;
		}
		case "/dev.reese.project1/SVorDHApprovalMessageViewer.pageinfo": {
			if(!isLoggedin(request, response))
				response.sendError(401, "not loggin in  for to /dev.reese.project1/LoginPage.goto to login");
			else {
				StringBuilder sb = new StringBuilder();
				sb.append("[");
				sb.append(UserInfoController.getThisEmployeeInfo(request, response));
				sb.append(",");
				sb.append(UserInfoController.getTheSelectedRecievedMessage(request, response));
				sb.append(",");
				sb.append(UserInfoController.getTheSelectedRequest(request, response));
				sb.append(",");
				sb.append(UserInfoController.getTheSelectedRequestNotes(request, response));
				sb.append(",");
				sb.append(UserInfoController.getTheSelectedEmployee(request, response));
//				sb.append(",");
//				sb.append(UserInfoController.getTheSelectedGrade(request, response));
				sb.append("]");
				response.getWriter().append(sb.toString());
			}
			break;
		}
		case "/dev.reese.project1/FBRequestAndNoticeMessageViewer.pageinfo": {
			if(!isLoggedin(request, response))
				response.sendError(401, "not loggin in  for to /dev.reese.project1/LoginPage.goto to login");
			else {
				StringBuilder sb = new StringBuilder();
				sb.append("[");
				sb.append(UserInfoController.getThisEmployeeInfo(request, response));
				sb.append(",");
				sb.append(UserInfoController.getTheSelectedRecievedMessage(request, response));
				sb.append(",");
				sb.append(UserInfoController.getTheSelectedRequest(request, response));
				sb.append(",");
				sb.append(UserInfoController.getTheSelectedRequestNotes(request, response));
				sb.append(",");
				sb.append(UserInfoController.getTheSelectedEmployee(request, response));
				sb.append("]");
				response.getWriter().append(sb.toString());
			}
			break;
		}
		case "/dev.reese.project1/BenCoApprovalMessageViewer.pageinfo": {
			if(!isLoggedin(request, response))
				response.sendError(401, "not loggin in  for to /dev.reese.project1/LoginPage.goto to login");
			else {
				StringBuilder sb = new StringBuilder();
				sb.append("[");
				sb.append(UserInfoController.getThisEmployeeInfo(request, response));
				sb.append(",");
				sb.append(UserInfoController.getTheSelectedRecievedMessage(request, response));
				sb.append(",");
				sb.append(UserInfoController.getTheSelectedRequest(request, response));
				sb.append(",");
				sb.append(UserInfoController.getTheSelectedRequestNotes(request, response));
				sb.append(",");
				sb.append(UserInfoController.getTheSelectedEmployee(request, response));
				sb.append(",");
				sb.append(UserInfoController.getSelectedEmployeePayouts(request, response));
				sb.append(",");
				sb.append(UserInfoController.getTheTotalPayoutsPendingAndAwardedThisYear(request, response));
				sb.append("]");
				response.getWriter().append(sb.toString());
			}
			break;
		}
		case "/dev.reese.project1/GradeSubmittionMessageViewer.pageinfo": {
			if(!isLoggedin(request, response))
				response.sendError(401, "not loggin in  for to /dev.reese.project1/LoginPage.goto to login");
			else {
				StringBuilder sb = new StringBuilder();
				sb.append("[");
				sb.append(UserInfoController.getThisEmployeeInfo(request, response));
				sb.append(",");
				sb.append(UserInfoController.getTheSelectedRecievedMessage(request, response));
				sb.append(",");
				sb.append(UserInfoController.getTheSelectedRequest(request, response));
				sb.append(",");
				sb.append(UserInfoController.getTheSelectedRequestNotes(request, response));
				sb.append("]");
				response.getWriter().append(sb.toString());
			}
			break;
		}
		case "/dev.reese.project1/GradeConfirmationMessageViewer.pageinfo": {
			if(!isLoggedin(request, response))
				response.sendError(401, "not loggin in  for to /dev.reese.project1/LoginPage.goto to login");
			else {
				StringBuilder sb = new StringBuilder();
				sb.append("[");
				sb.append(UserInfoController.getThisEmployeeInfo(request, response));
				sb.append(",");
				sb.append(UserInfoController.getTheSelectedRecievedMessage(request, response));
				sb.append(",");
				sb.append(UserInfoController.getTheSelectedRequest(request, response));
				sb.append(",");
				sb.append(UserInfoController.getTheSelectedRequestNotes(request, response));
				sb.append(",");
				sb.append(UserInfoController.getTheSelectedEmployee(request, response));
				sb.append(",");
				sb.append(UserInfoController.getTheSelectedRequestsGradeAndNotes(request, response));
				sb.append("]");
				response.getWriter().append(sb.toString());
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
