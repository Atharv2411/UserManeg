package com.EMS.Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import com.EMS.Implements.AuthenticateImplementation;
import com.EMS.pojo.Authenticate;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/Login")
public class Login extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        
        PrintWriter out = resp.getWriter();

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        Authenticate user = new Authenticate();
        user.setEmail(email);
        user.setPassword(password);

        AuthenticateImplementation authImpl = new AuthenticateImplementation();

        try {
            boolean isValid = authImpl.login(user);

            if (isValid) {
                RequestDispatcher rd = req.getRequestDispatcher("dashboard.html");
                rd.forward(req, resp);
            } else {
                out.println("<p style='color:red;'>Login failed! Invalid email or password.</p>");
                RequestDispatcher rd = req.getRequestDispatcher("login.html");
                rd.include(req, resp);
            }

        } catch (ClassNotFoundException | SQLException e) {
            out.println("<p style='color:red;'>Internal error occurred. Please try again later.</p>");
            RequestDispatcher rd = req.getRequestDispatcher("login.html");
            rd.forward(req, resp);
        }
    }
}
