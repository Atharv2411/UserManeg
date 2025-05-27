package com.EMS.Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;

import com.EMS.Data_other.Timestamps;
import com.EMS.Implements.ExpencessImplements;
import com.EMS.pojo.Expencess;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AddExpencessServlet")
public class AddExpencessServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
        
        PrintWriter out = resp.getWriter();
        RequestDispatcher rd;
        
        Expencess e  = new Expencess();
        ExpencessImplements eimpl = new ExpencessImplements();
        
        int check;
        
        double amount = Double.parseDouble(req.getParameter("amount"));
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String basis = req.getParameter("basis");
        String category = req.getParameter("category");
        String type = req.getParameter("type");
        Date date = Date.valueOf(req.getParameter("date"));
        
        String timeString = req.getParameter("time");
        if (timeString != null && timeString.length() == 5) {
            timeString += ":00";
        }
        Time time = Time.valueOf(timeString);

        
        
        

 	   String namePattern = "^[A-Za-z]+$";
        if (!name.matches(namePattern)) {
            showError(req, resp, "Name must contain only letters, no numbers allowed.");
            return;
        }
        
        
        
        e.setName(name);
        e.setDescription(description);
        e.setBasis(basis);
        e.setAmount(amount);
        e.setDate(date);
        e.setTime(time);
        e.setCategory(category);
        e.setType(type);
        e.setCreated_date(Timestamps.getTime());
    	
    	try {
			check=eimpl.AddExpencess(e);
			if(check > 0)
			{
				rd=req.getRequestDispatcher("ViewExpencesServlet");
				rd.forward(req, resp);
			}
			else
			{
		        out.println("<p style='color:red; text-align:center; font-weight:bold; margin-top:20px;'>something went wrong expencess not addes!!!</p>");
				rd=req.getRequestDispatcher("AddExpencess.html");
				rd.include(req, resp);
			}
			
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
    	
       

	}
	 private void showError(HttpServletRequest req, HttpServletResponse resp, String message) 
	            throws ServletException, IOException {
	        PrintWriter out = resp.getWriter();
	        out.println("<p style='color:red; text-align:center; font-weight:bold; margin-top:20px;'>" + message + "</p>");
	        RequestDispatcher rd = req.getRequestDispatcher("AddExpencec.html");
	        rd.include(req, resp);
	    }
}
