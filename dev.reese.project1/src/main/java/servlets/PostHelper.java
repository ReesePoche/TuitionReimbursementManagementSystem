package servlets;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controllers.FormSubmittingController;
import controllers.LoginController;



public class PostHelper {
	
	private static boolean isLoggedin(HttpServletRequest request, HttpServletResponse response) throws IOException{
		HttpSession sess = request.getSession();
		if(sess.getAttribute("loggedInEmployeeID") == null) 
			return false;
		return true;
	}

	
	public static void process(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String uri = request.getRequestURI();
		
		System.out.println(uri + "in the post helper");
		
		switch (uri) {
		
			case "/dev.reese.project1/LoginPage.do": {
				LoginController.attemptLogin(request, response);
				break;
			}
			case "/dev.reese.project1/SubmitForm.do": { 
				FormSubmittingController.SubmitTRRForm(request, response);
				break;
			}
			
			default: {
				response.sendError(418, "Default case hit. Cannot brew coffee, is teapot.");
				break;
			}
		}
		
	}
}
