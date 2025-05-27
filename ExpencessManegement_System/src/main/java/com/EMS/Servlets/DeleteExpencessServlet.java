package com.EMS.Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;

import com.EMS.Data_other.Timestamps;
import com.EMS.Implements.ExpencessImplements;
import com.EMS.pojo.Expencess;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/DeleteExpencessServlet")
public class DeleteExpencessServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    resp.setContentType("text/html");
	    PrintWriter out = resp.getWriter();
	    RequestDispatcher rd;

	    int id = Integer.parseInt(req.getParameter("expenseId"));


	    Expencess e = new Expencess();
	    e.setId(id);

	    try {
	        boolean check = new ExpencessImplements().DeleteExpences(e);
	        if (check) {
	        	rd = req.getRequestDispatcher("ViewExpencesServlet");
	        	rd.forward(req, resp);
	            out.println("<p style='color:green; text-align:center;'>Expense updated successfully.</p>");
	            
	        } else {
	        	rd = req.getRequestDispatcher("ViewExpencesServlet");
	        	rd.forward(req, resp);
	            out.println("<p style='color:red; text-align:center;'>Failed to update expense.</p>");
	        }
	    } catch (ClassNotFoundException | SQLException ex) {
	        ex.printStackTrace();
	        out.println("<p style='color:red; text-align:center;'>An error occurred while updating expense.</p>");
	    }
	}

	private void showError(HttpServletRequest req, HttpServletResponse resp, String message) 
	        throws ServletException, IOException {
	    PrintWriter out = resp.getWriter();
	    out.println("<p style='color:red; text-align:center; font-weight:bold; margin-top:20px;'>" + message + "</p>");
	    RequestDispatcher rd = req.getRequestDispatcher("ViewExpencesServlet");
	    rd.include(req, resp);
	}

	
}

