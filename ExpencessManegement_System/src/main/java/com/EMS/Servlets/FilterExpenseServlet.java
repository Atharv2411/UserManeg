package com.EMS.Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.EMS.Implements.FilterExpencesImplements;
import com.EMS.pojo.Expencess;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/FilterExpenseServlet")
public class FilterExpenseServlet extends HttpServlet {

    private final FilterExpencesImplements dao = new FilterExpencesImplements();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<String> categories = new ArrayList<>();
        List<String> basisList = new ArrayList<>();

        try {
            categories = dao.getDistinctCategories();
            basisList = dao.getDistinctBasis();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Filter Expenses</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 20px; }");
        out.println("h2 { color: #333; }");
        out.println("form { background: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1); max-width: 500px; margin: auto; }");
        out.println("select, input[type='date'] { width: 100%; padding: 10px; margin-bottom: 15px; border-radius: 5px; border: 1px solid #ccc; }");
        out.println("input[type='submit'] { background-color: #28a745; color: white; border: none; padding: 10px 20px; border-radius: 5px; cursor: pointer; }");
        out.println("input[type='submit']:hover { background-color: #218838; }");
        out.println("</style>");
        out.println("</head><body>");

        out.println("<h2 style='text-align:center;'>Filter Expenses</h2>");
        out.println("<form method='post' action='FilterExpenseServlet'>");

        out.println("<label>Category:</label>");
        out.println("<select name='category'><option value=''>--All--</option>");
        for (String cat : categories) {
            out.println("<option value='" + cat + "'>" + cat + "</option>");
        }
        out.println("</select>");

        out.println("<label>Basis:</label>");
        out.println("<select name='basis'><option value=''>--All--</option>");
        for (String bas : basisList) {
            out.println("<option value='" + bas + "'>" + bas + "</option>");
        }
        out.println("</select>");

        out.println("<label>Start Date:</label>");
        out.println("<input type='date' name='startDate'>");

        out.println("<label>End Date:</label>");
        out.println("<input type='date' name='endDate'>");

        out.println("<input type='submit' value='Filter'>");
        out.println("</form>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String category = request.getParameter("category");
        String basis = request.getParameter("basis");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        List<Expencess> filteredExpenses = new ArrayList<>();
        try {
            filteredExpenses = dao.filterExpenses(category, basis, startDate, endDate);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Filtered Results</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; background-color: #f9f9f9; padding: 20px; }");
        out.println("h2 { color: #444; text-align: center; }");
        out.println("table { width: 100%; border-collapse: collapse; margin-top: 20px; }");
        out.println("th, td { border: 1px solid #ccc; padding: 10px; text-align: left; }");
        out.println("th { background-color: #007bff; color: white; }");
        out.println("tr:nth-child(even) { background-color: #f2f2f2; }");
        out.println("a { display: inline-block; margin-top: 20px; color: #007bff; text-decoration: none; }");
        out.println("a:hover { text-decoration: underline; }");
        out.println("h3 { text-align: center; color: #333; }");
        out.println("</style>");
        out.println("</head><body>");

        out.println("<h2>Filtered Results</h2>");
        if (filteredExpenses == null || filteredExpenses.isEmpty()) {
            out.println("<p style='text-align:center;'>No records found.</p>");
        } else {
            out.println("<table>");
            out.println("<tr><th>ID</th><th>Name</th><th>Description</th><th>Basis</th><th>Amount</th><th>Date</th><th>Time</th><th>Category</th><th>Type</th></tr>");

            double totalAmount = 0;

            for (Expencess e : filteredExpenses) {
                out.println("<tr>");
                out.println("<td>" + e.getId() + "</td>");
                out.println("<td>" + e.getName() + "</td>");
                out.println("<td>" + e.getDescription() + "</td>");
                out.println("<td>" + e.getBasis() + "</td>");
                out.println("<td>" + e.getAmount() + "</td>");
                out.println("<td>" + e.getDate() + "</td>");
                out.println("<td>" + e.getTime() + "</td>");
                out.println("<td>" + e.getCategory() + "</td>");
                out.println("<td>" + e.getType() + "</td>");
                out.println("</tr>");

                totalAmount += e.getAmount();
            }

            out.println("</table>");
            out.println("<h3>Total Amount: " + totalAmount + "</h3>");
        }

        out.println("<div style='text-align:center;'><a href='FilterExpenseServlet'>Back to Filter</a></div>");
        out.println("</body></html>");
    }
}
