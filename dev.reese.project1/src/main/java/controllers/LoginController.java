package controllers;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import dev.reese.project1.entities.Employee;
import serviceexceptions.EmailNotFoundException;
import serviceexceptions.PasswordIncorrectException;
import services.LoginService;

public class LoginController {
	
	private static LoginService ls = new LoginService();
	
	
	public static void attemptLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
	    String email = jobj.get("Eemail").getAsString();
	    System.out.println(email);
	    Employee e;
	    String password = jobj.get("Epassword").getAsString();
	    try{
	    	e = ls.attemptLogin(email, password);
	    	System.out.println(e.getId());
	    	HttpSession sess = request.getSession();
		    sess.setAttribute("loggedInEmployeeID", e.getId());
		    response.getWriter().append("LS");
	    }
	    catch(EmailNotFoundException ENFE) {
	    	response.getWriter().append("EDNE");
	    	return;
	    }
	    catch(PasswordIncorrectException ENFE) {
	    	response.getWriter().append("IP");
	    	return;
	    }
	}
	
	public static void Logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession sess = request.getSession();
		sess.setAttribute("loggedInEmployeeID", null);
		response.getWriter().append("LOGOUTED");
	}
	
	

}
