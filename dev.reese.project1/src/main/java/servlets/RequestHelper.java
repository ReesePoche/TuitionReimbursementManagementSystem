package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controllers.UserInfoController;

public class RequestHelper {
	
	private static boolean isLoggedin(HttpServletRequest request, HttpServletResponse response) throws IOException{
		HttpSession sess = request.getSession();
		if(sess.getAttribute("loggedInEmployeeID") == null) 
			return false;
		return true;
	}
	
	private static void loggout(HttpServletRequest request, HttpServletResponse response) throws IOException{
		HttpSession sess = request.getSession();
		sess.setAttribute("loggedInEmployeeID", null);
		return;
	}
	
	
	
	public static void process(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String uri = request.getRequestURI();
		
		System.out.println(uri + " in the request helper");
		
		switch (uri) {
//			case "/dev.reese.project1/LoginPage.do": {
//				loggout(request, response);
//				response.sendRedirect("LoginPage.html");
//				break;
//			}
//			case "/dev.reese.project1/TRMSHub.do": {
//				if(!isLoggedin(request, response))
//					response.sendRedirect("/dev.reese.project1/LoginPage.do");
//				else 
//					response.sendRedirect("TRMSHub.html");
//					break;
//			}
//			case "/dev.reese.project1/Messages.do": {
//				if(!isLoggedin(request, response))
//					response.sendError(401, "not loggin in  for to /dev.reese.project1/LoginPage.do to login");
//				else
//					response.sendRedirect("Messages.html");
//				break;
//			}
//			case "/dev.reese.project1/SubmitTRRForm.do": {
//				if(!isLoggedin(request, response))
//					response.sendError(401, "not loggin in  for to /dev.reese.project1/LoginPage.do to login");
//				else
//					response.sendRedirect("TuitionReimbursementRequestSubmittionForm.html");
//				break;
//			}
//			case "/dev.reese.project1/ReimbursementPayouts.do": {
//				if(!isLoggedin(request, response))
//					response.sendError(401, "not loggin in  for to /dev.reese.project1/LoginPage.do to login");
//				else
//					response.sendRedirect("ReimbursementPayouts.html");
//				break;
//			}
//			case "/dev.reese.project1/SubmittedRequests.do": {
//				if(!isLoggedin(request, response))
//					response.sendError(401, "not loggin in  for to /dev.reese.project1/LoginPage.do to login");
//				else
//					response.sendRedirect("SubmittedRequests.html");
//				break;
//			}
//			case "/dev.reese.project1/ThisEmployeeInfo.do": {
//				if(!isLoggedin(request, response))
//					response.sendError(401, "not loggin in  for to /dev.reese.project1/LoginPage.do to login");
//				else
//					UserInfoController.getThisEmployeeInfo(request, response);
//				break;
//			} 
//			case "/dev.reese.project1/erequests.do": {
//				if(!isLoggedin(request, response))
//					response.sendError(401, "not loggin in  for to /dev.reese.project1/LoginPage.do to login");
//				else
//					UserInfoController.getThisEmployeeRequests(request, response);
//				break;
//			}
//			case "/dev.reese.project1/epayouts.do": {
//				if(!isLoggedin(request, response))
//					response.sendError(401, "not loggin in  for to /dev.reese.project1/LoginPage.do to login");
//				else
//					UserInfoController.getThisEmployeePayouts(request, response);
//				break;
//			}
//			case "/dev.reese.project1/emessages.do": {
//				if(!isLoggedin(request, response))
//					response.sendError(401, "not loggin in  for to /dev.reese.project1/LoginPage.do to login");
//				else
//					UserInfoController.GetThisEmployeeMessages(request, response);
//				break;
//			}
//			case "/dev.reese.project1/erequest.do": {
//				if(!isLoggedin(request, response))
//					response.sendError(401, "not loggin in  for to /dev.reese.project1/LoginPage.do to login");
//				else
//					UserInfoController.getTheRequest(request, response);
//				break;
//			}
//			case "/dev.reese.project1/erequestgo.do": {
//				if(!isLoggedin(request, response))
//					response.sendError(401, "not loggin in  for to /dev.reese.project1/LoginPage.do to login");
//				else
//					response.sendRedirect("TuitionReimbursementRequestSubmittionForm.html");
//				break;
//			}
//			//dev.reese.project1/Employee.do
//			
//			default: {
//				response.sendError(418, "Default case hit. Cannot brew coffee, is teapot.");
//				break;
//			}
		}
		
	}

}
