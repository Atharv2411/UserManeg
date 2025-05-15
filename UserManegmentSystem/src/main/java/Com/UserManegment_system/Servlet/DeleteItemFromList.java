package Com.UserManegment_system.Servlet;

import Com.UserManegment_system.Databases.EstamblishConnection;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/DeleteItemFromList")
public class DeleteItemFromList extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idParam = request.getParameter("productId");
        PrintWriter out = response.getWriter();

        if (idParam != null && !idParam.isEmpty()) {
            int id = Integer.parseInt(idParam);

            
            System.out.println(id);
            try (Connection con = EstamblishConnection.DBConnection()) {
                PreparedStatement ps = con.prepareStatement("DELETE FROM Item WHERE id = ?");
                ps.setInt(1, id);
                int rowsAffected = ps.executeUpdate();
                
                System.out.println(rowsAffected);
                

                if (rowsAffected > 0) {
                    response.sendRedirect("ListOfProductServlet"); 
                } else {
                    out.println("<p style='color:red;'>item not found.</p>");
                    response.sendRedirect("ListOfProductServlet"); 
                }
            } catch (Exception e) {
                e.printStackTrace();
                out.println("<p style='color:red;'>Internal error occurred. Please try again later.</p>");

                response.sendRedirect("home.html");
            }
        } else {
            out.println("<p style='color:red;'> No productId</p>");

            response.sendRedirect("home.html"); 
        }
    }
}
