package Com.UserManegment_system.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import Com.UserManegment_system.Impl.UserLoginImplement;
import Com.UserManegment_system.Pojo.UserRegister;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/UserLoginServlet")
public class UserLoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

       
        String email = req.getParameter("emailaddress");
        String password = req.getParameter("pass");

       
        UserRegister user = new UserRegister();
        user.setEmail(email);
        user.setPassword(password);

        
        UserLoginImplement loginService = new UserLoginImplement();

        try {
            boolean isAuthenticated = loginService.login(user);

            if (isAuthenticated) {
                RequestDispatcher rd = req.getRequestDispatcher("enter.html");
                rd.forward(req, resp); 
            } else {
                out.println("<p style='color:red;'>Login failed! Invalid email or password.</p>");
                RequestDispatcher rd = req.getRequestDispatcher("login.html");
                rd.include(req, resp); 
            }

        } catch (ClassNotFoundException | SQLException e) {
            out.println("<p style='color:red;'>Internal error occurred. Please try again later.</p>");
            e.printStackTrace();
        }
    }
}
