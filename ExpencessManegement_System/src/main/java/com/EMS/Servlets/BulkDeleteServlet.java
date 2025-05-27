package com.EMS.Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.EMS.Data_other.DatabaseIN;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/BulkDeleteServlet")
public class BulkDeleteServlet extends HttpServlet {
	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        
        PrintWriter out = resp.getWriter();
		String[] selectedIds = req.getParameterValues("selectedIds");

        if (selectedIds != null && selectedIds.length > 0) {
            try (Connection conn = DatabaseIN.DBConnection()) {
                String sql = "DELETE FROM expenses WHERE id = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    for (String idStr : selectedIds) {
                        int id = Integer.parseInt(idStr);
                        pstmt.setInt(1, id);
                        pstmt.executeUpdate();
                    }
                }
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                resp.setContentType("text/html");
                out.println("<h3 style='color:red; text-align:center;'>Failed to delete selected items. Please try again.</h3>");
                return;
            }
        }
        else
        {
            out.println("<h3 style='color:red; text-align:center;'>Failed to delete selected items. Please try again.</h3>");
        	resp.sendRedirect("ViewExpencesServlet");
        }

    }
}
