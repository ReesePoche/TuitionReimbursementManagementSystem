package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class MainServlet
 */
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static int counter = 0;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MainServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String uri = request.getRequestURI();
		System.out.println(uri + " in the main do get");
		RequestHelper.process(request, response);
//		RequestDispatcher view = request.getRequestDispatcher("LoginPage.html");
//		view.forward(request, response);
		//response.sendRedirect("/LoginPage.html");
		
//		System.out.println("received a get message");
//	    PrintWriter out = response.getWriter();
//	    out.println("<script>alert(\"I'm here\");</script>");
//	    request.getRequestDispatcher("LoginPage.html").include(request, response);
//	    out.close();
		
		
		
		
		
//		response.setContentType("html");
//		   PrintWriter out = response.getWriter();
////		   out.println("HTML from an external file:");     
//		   request.getRequestDispatcher("C:\\Users\\Reese\\eclipse-workspace\\dev.reese.project1\\src\\main\\webapp\\LoginPage.html")
//		          .include(request, response); 
//		   out.close();
//		return;
//		ServletContext sc = getServletContext();
//		sc.getRequestDispatcher("dev.reese.project1/NewFile.html").forward(request, response);
		//RequestHelper.process(request, response);
		
		
		
		//RequestHelper.process(request, response);
		
		
		
		//HttpSession sess = request.getSession();
//		System.out.println(sess.getId());
//		System.out.println(sess.getAttribute("loggedInUser"));
//		if(sess.getAttribute("loggedInUser") == null)
//			sess.setAttribute("loggedInUser", counter++);
//		System.out.println(sess.getAttribute("loggedInUser"));
//		
//		//response.getWriter().append("Welcome to the main Servlet you are session number " + sess.getAttribute("loggedInUser"));
//		String uri = request.getRequestURI();
//		System.out.println(uri);
//		switch(uri) {
//		
//			case "dev.reese.project1/Login": {
//				
//				break;
//			} case "/ServletEx/googleredirect": {
//				response.sendRedirect("https://www.google.com/");
//				break;
//			} case "/ServletEx/subredirect": {
//				response.sendRedirect("/ServletEx/SubServlet");
//				break;
//			} case "/ServletEx/subforward": {
//				//Forward needs a RequestDispatcher
//				RequestDispatcher rd = request.getRequestDispatcher("/SubServlet");
//				rd.forward(request, response);
//				break;
//			}
//			default: {
//				response.sendError(451);
//				break;
//			}
//		
//		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PostHelper.process(request, response);
	}

}
