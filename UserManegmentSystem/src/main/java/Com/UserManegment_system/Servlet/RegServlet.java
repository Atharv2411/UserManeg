package Com.UserManegment_system.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Com.UserManegment_system.Databases.EstamblishConnection;
import Com.UserManegment_system.Impl.UserLoginImplement;
import Com.UserManegment_system.Pojo.UserRegister;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/RegServlet")
public class RegServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        String name = req.getParameter("name");
        String email = req.getParameter("emailaddress");
        String password = req.getParameter("pass");
        String gender = req.getParameter("gender");

        String namePattern = "^[A-Za-z]+$";
        if (!name.matches(namePattern)) {
            showError(req, resp, "Name must contain only letters, no numbers allowed.");
            return;
        }

        String passwordPattern = "^(?=.*[0-9])(?=.*[@#$%^&+=!]).{8,}$";
        if (!password.matches(passwordPattern)) {
            showError(req, resp, "Password must be at least 8 characters long, contain at least one number and one special character.");
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

        UserRegister user = new UserRegister();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setGender(gender);

        UserLoginImplement userService = new UserLoginImplement();
        try {
            int result = userService.adduser(user);
            if (result > 0) {
                out.println("<p style='color:green; text-align:center; font-weight:bold; margin-top:20px;'>Registered successfully!</p>");
                RequestDispatcher rd = req.getRequestDispatcher("home.html");
                rd.forward(req, resp);
            } else {
                showError(req, resp, "Registration failed. Please try again.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            showError(req, resp, "An error occurred: " + e.getMessage());
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
            conn = EstamblishConnection.DBConnection();
            String query = "SELECT COUNT(*) FROM users WHERE emailaddress = ?";
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
        RequestDispatcher rd = req.getRequestDispatcher("Register.html");
        rd.include(req, resp);
    }
}