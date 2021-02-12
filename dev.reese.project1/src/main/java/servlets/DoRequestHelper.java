package servlets;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controllers.FormSubmittingController;
import controllers.LoginController;

public class DoRequestHelper {
	
	
	private static boolean isLoggedin(HttpServletRequest request, HttpServletResponse response) throws IOException{
		HttpSession sess = request.getSession();
		if(sess.getAttribute("loggedInEmployeeID") == null) 
			return false;
		return true;
	}

	
	public static void process(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String uri = request.getRequestURI();
		
		System.out.println(uri + "in the do request ....  helper");
		
		switch (uri) {
		
			case "/dev.reese.project1/LoginPage.do": {
				LoginController.attemptLogin(request, response);
				break;
			}
			case "/dev.reese.project1/SubmitForm.do": { 
				FormSubmittingController.SubmitTRRForm(request, response);
				break;
			}
			case "/dev.reese.project1/ReplyToNotice.do": { 
				FormSubmittingController.replyToNotice(request, response);
				response.getWriter().append("SS");
				break;
			}
			case "/dev.reese.project1/ReplyToApprovalRequest.do": { 
				FormSubmittingController.approvalRequestResponse(request, response);
				response.getWriter().append("SS");
				break;
			}
			case "/dev.reese.project1/ReplyToFBRequest.do": { 
				FormSubmittingController.replyToFBRequest(request, response);
				response.getWriter().append("SS");
				break;
			}
			//handleGradeSubmittion
			case "/dev.reese.project1/ReplyToGSRequest.do": { 
				FormSubmittingController.handleGradeSubmittion(request, response);
				response.getWriter().append("SS");
				break;
			}
			//handleGradeConfirmation
			case "/dev.reese.project1/ReplyToGCRequest.do": { 
				FormSubmittingController.handleGradeConfirmation(request, response);
				response.getWriter().append("SS");
				break;
			}
			default: {
				response.sendError(418, "Default case hit. Cannot brew coffee, is teapot.");
				break;
			}
		}
		
	}

}
