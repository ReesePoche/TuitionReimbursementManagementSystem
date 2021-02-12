package servlets;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class SetUpRequestHandler {
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
	
	
	
	public static void process(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String uri = request.getRequestURI();
		
		System.out.println(uri + " in the goto request helper");
		
		switch (uri) {
			case "/dev.reese.project1/requestViewer.setup": {
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
			    JsonObject jobj = new Gson().fromJson(sb.toString(), JsonObject.class);
			    String requestId = jobj.get("requestId").getAsString();
			    HttpSession sess = request.getSession();
				sess.setAttribute("selectedRequestId", requestId);
				response.getWriter().append("SSU");
				break;
			}
			case "/dev.reese.project1/messageViewer.setup": {
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
			    JsonObject jobj = new Gson().fromJson(sb.toString(), JsonObject.class);
			    String MessageId = jobj.get("messageId").getAsString();
			    HttpSession sess = request.getSession();
				sess.setAttribute("selectedMessageId", MessageId);
				response.getWriter().append("SSU");
				break;
			}
			default: {
				response.sendError(418, "Default case hit. Cannot brew coffee, is teapot.");
				break;
			}
		}
	}
	
	
	
	
	
	

}
