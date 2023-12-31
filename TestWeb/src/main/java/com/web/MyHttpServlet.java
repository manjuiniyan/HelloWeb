package com.web;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;

import com.web.jdbc.LoginDataAccessObject;

/**
 * Servlet implementation class MyHttpServlet
 */
@WebServlet( urlPatterns =  "/MyHttpServlet",
			initParams = @WebInitParam(
					name = "defaultPassword",
					value = "admin"
					)
		)
public class MyHttpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	int userCount = 0;
	ServletConfig config=null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyHttpServlet() {
    	System.out.println("inside the constructor");
    }

    
    
//    public void init() throws ServletException {
//    	System.out.println("inside the init method");
//    	this.config = getServletConfig();
//    }
    
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		System.out.println("inside the init method  with servlet config "+config.getServletName());
		this.config = config;
		
	}
	

	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LoginDataAccessObject logindbObj=new LoginDataAccessObject();
		
		// name = manju
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		System.out.println(username+"  "+password);
		
		HttpSession session=request.getSession();
		
		//boolean isLoginValid = logindbObj.checkLogin(username, password);
		HashMap<String,String> details=logindbObj.getLoginDeatils(username, password);
		// sendRedirect
		if(!details.isEmpty()) { // jdbc data base check
			System.out.println("valid username and password");
			RequestDispatcher dispatcher = null;
			
			session.setAttribute("userObject", details);

			
			dispatcher = request.getRequestDispatcher("MySchool.jsp");
			dispatcher.forward(request, response);
			
		} else {
			session.setAttribute("errorMessage", "Invalid UserName or Password");
			System.out.println("invalid username password");
			response.sendRedirect("Login.jsp");
		}

	}

}

