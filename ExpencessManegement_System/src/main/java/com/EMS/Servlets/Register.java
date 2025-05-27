package com.EMS.Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.EMS.Data_other.DatabaseIN;
import com.EMS.Implements.AuthenticateImplementation;
import com.EMS.pojo.Authenticate;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/Register")
public class Register extends HttpServlet {
@Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	resp.setContentType("text/html");
	
	PrintWriter out = resp.getWriter();
	RequestDispatcher rd;
	
	Authenticate a = new Authenticate();
	AuthenticateImplementation aimp = new AuthenticateImplementation();

	String name,email,gender,password;
	int check;
	
	
	name =req.getParameter("username");
	email = req.getParameter("email");
	gender= req.getParameter("gender");
	password = req.getParameter("password");
	
	a.setName(name);
	a.setEmail(email);
	a.setGender(gender);
	a.setPassword(password);
	
	
	String namePattern = "^(?=.*[a-z])(?=.*[A-Z])[A-Za-z]+$";
	if (!name.matches(namePattern)) {
	    showError(req, resp, "Name must contain at least one uppercase and one lowercase letter, and no numbers or special characters.");
	    return;
	}


	String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$";
	if (!password.matches(passwordPattern)) {
	    showError(req, resp, "Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one number, and one special character.");
	    return;
	}
	
       if (!isValidEmail(email)) {
           showError(req, resp, "Please enter a valid email address.");
           return;
       }

       if (isEmailExists(email)) {
           showError(req, resp, "This email address is already registered. Please use a different email.");
           return;
       }
	
	try {
		check = aimp.register(a);
		if(check > 0)
		{
			out.println("successfully register!!!!");
			rd = req.getRequestDispatcher("dashboard.html");
			rd.forward(req, resp);
		}
		else
		{
			rd = req.getRequestDispatcher("Regitser");
			rd.include(req, resp);
		}
	} catch (ClassNotFoundException | SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
	
	private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email != null && email.matches(emailRegex);
    }

    private boolean isEmailExists(String email) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseIN.DBConnection();
            String query = "SELECT COUNT(*) FROM Auth WHERE email = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, email);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private void showError(HttpServletRequest req, HttpServletResponse resp, String message) 
            throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        out.println("<p style='color:red; text-align:center; font-weight:bold; margin-top:20px;'>" + message + "</p>");
        RequestDispatcher rd = req.getRequestDispatcher("reg.html");
        rd.include(req, resp);
    }
}