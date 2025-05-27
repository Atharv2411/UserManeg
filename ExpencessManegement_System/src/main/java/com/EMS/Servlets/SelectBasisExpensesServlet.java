package com.EMS.Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import com.EMS.Implements.BasisExpencesSelectImplement;
import com.EMS.pojo.Ammount;
import com.EMS.pojo.Expencess;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/SelectBasisExpensesServlet")
public class SelectBasisExpensesServlet extends HttpServlet {
	 private final BasisExpencesSelectImplement dao = new BasisExpencesSelectImplement();

	    @Override
	    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {

	        String basis = request.getParameter("basis");
	        String dateStr = request.getParameter("date");

	        response.setContentType("text/html");
	        PrintWriter out = response.getWriter();

	        try {
	            List<Ammount> expensesList = null;
	            List<Expencess> detailedList = null;

	            switch (basis) {
	                case "daily":
	                    LocalDate localDate = LocalDate.parse(dateStr);
	                    Date sqlDate = Date.valueOf(localDate);
	                    expensesList = dao.calculateTotalForDay(sqlDate);
	                    detailedList = dao.getExpensesForDay(sqlDate);
	                    break;

	                case "monthly":
	                    LocalDate firstDayOfMonth = LocalDate.parse(dateStr + "-01");
	                    int year = firstDayOfMonth.getYear();
	                    int month = firstDayOfMonth.getMonthValue();
	                    expensesList = dao.calculateTotalForMonth(year, month);
	                    detailedList = dao.getExpensesForMonth(year, month);
	                    break;

	                case "yearly":
	                    int y = Integer.parseInt(dateStr);
	                    expensesList = dao.calculateTotalForYear(y);
	                    detailedList = dao.getExpensesForYear(y);
	                    break;

	                default:
	                    out.println("<h3>Invalid basis selected</h3>");
	                    return;
	            }

	            out.println("<html><head><title>Expense Summary</title></head><body>");
	            out.println("<style>");
	            out.println("body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background: #f9f9f9; margin: 20px; }");
	            out.println("h2, h3 { color: #2c3e50; }");
	            out.println("table { border-collapse: collapse; width: 100%; max-width: 1200px; margin-bottom: 20px; box-shadow: 0 2px 8px rgba(0,0,0,0.1); background: white; }");
	            out.println("th, td { padding: 12px 15px; text-align: left; border-bottom: 1px solid #ddd; }");
	            out.println("th { background-color: #2980b9; color: white; font-weight: 600; }");
	            out.println("tr:hover { background-color: #f1f7fc; }");
	            out.println("a { display: inline-block; margin-top: 10px; color: #2980b9; text-decoration: none; font-weight: 600; }");
	            out.println("a:hover { text-decoration: underline; }");
	            out.println("p { font-size: 1rem; color: #7f8c8d; }");
	            out.println("</style>");
	            out.println("<h2>Expense Summary for Basis: " + basis + ", Date: " + dateStr + "</h2>");

	            if (detailedList != null && !detailedList.isEmpty()) {
	                out.println("<h3>Detailed Expenses</h3>");
	                out.println("<table>");
	                out.println("<tr><th>Name</th><th>Description</th><th>Amount</th><th>Category</th><th>Type</th><th>Basis</th><th>Date</th><th>Time</th></tr>");
	                for (Expencess e : detailedList) {
	                    out.println("<tr>");
	                    out.println("<td>" + e.getName() + "</td>");
	                    out.println("<td>" + e.getDescription() + "</td>");
	                    out.println("<td>" + e.getAmount() + "</td>");
	                    out.println("<td>" + e.getCategory() + "</td>");
	                    out.println("<td>" + e.getType() + "</td>");
	                    out.println("<td>" + e.getBasis() + "</td>");
	                    out.println("<td>" + e.getDate() + "</td>");
	                    out.println("<td>" + e.getTime() + "</td>");
	                    out.println("</tr>");
	                }
	                out.println("</table>");
	            } else {
	                out.println("<p>No detailed expenses found for the selected period.</p>");
	            }

	            if (expensesList != null && !expensesList.isEmpty()) {
	                out.println("<h3>Total Summary</h3>");
	                out.println("<table>");
	                out.println("<tr><th>Period</th><th>Total Amount</th></tr>");
	                for (Ammount summary : expensesList) {
	                    out.println("<tr><td>" + summary.getPeriod() + "</td><td>" + summary.getTotalAmount() + "</td></tr>");
	                }
	                out.println("</table>");
	            }

	            out.println("<a href='SelectBasis.html'>Go Back</a>");
	            out.println("</body></html>");

	        } catch (Exception e) {
	            e.printStackTrace(out);
	            out.println("<p>Error: " + e.getMessage() + "</p>");
	            out.println("<br><a href='SelectBasis.html'>Go Back</a>");
	        }
	    }
	}





