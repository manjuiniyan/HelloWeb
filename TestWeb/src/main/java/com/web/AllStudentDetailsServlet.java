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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.web.jdbc.DBConnection;

/**
 * Servlet implementation class StudentDetailServlet
 */
@WebServlet( urlPatterns = "/AllStudentDetailsServlet",
			initParams = @WebInitParam(
						name = "defaltTotalDays",
						value = "150"
					)
		)
public class AllStudentDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AllStudentDetailsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void init() throws ServletException {
    	System.out.println("inside the init method");
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//getContext parameter
		ServletConfig config = this.getServletConfig();
		ServletContext context=config.getServletContext();
		String code = context.getInitParameter("WebSiteCode");
		System.out.println("the cotext parameter inside AllStudentDetailsServlet code ="+code);
		
		String firstCust = (String)context.getAttribute("FirstCustomer");
		System.out.println("context attribute from first servlet "+firstCust);
		
		//getConfig parameter
		String defaultDays = config.getInitParameter("defaltTotalDays");
		System.out.println("config init parameter defaultDays= "+defaultDays);
		
	HttpSession session=request.getSession();
		
		HashMap<String, String> userDetailsMap= (HashMap)session.getAttribute("userObject");
		
		
		PrintWriter out=response.getWriter();
		response.setContentType("text/html");
		String outputTable = "<table border=2, align=center><tr><td>Student RollNumber</td><td>Student Name</td><td>Degree</td><td>Mobile Number</td><td>Email</td><td>DOB</td><tr>";
		
		System.out.println("inside the doGet student id ");
		String query = "SELECT * FROM testdb.student ";
		System.out.println("select query ="+query);
		try {
			Connection conn=DBConnection.getConnection();
			PreparedStatement stat=conn.prepareStatement(query);
			ResultSet resultSet = stat.executeQuery();
			while (resultSet.next()) {
				outputTable = outputTable.concat("<tr><td>"+resultSet.getString("rollno")+"</td><td>"+resultSet.getString("studentname")+"</td><td>"+resultSet.getString("degree")+"</td><td>"+resultSet.getString("mobile_no")+"</td><td>"+resultSet.getString("email")+"</td><td>"+resultSet.getString("DOB")+"</tr>");

			
			}

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		outputTable = outputTable.concat("</table>");
		System.out.println(outputTable);
		out.println(outputTable);
	
		Cookie ck[]=request.getCookies();  
		for(int i=0;i<ck.length;i++){  
		 out.print("<br>"+ck[i].getName()+" "+ck[i].getValue());//printing name and value of cookie  
		}  

		
		
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		doGet(request, response);
//	}

}
