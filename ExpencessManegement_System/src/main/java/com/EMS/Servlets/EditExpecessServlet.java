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

@WebServlet("/EditExpecessServlet")
public class EditExpecessServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        Expencess e;
        int id = Integer.parseInt(req.getParameter("expenseId"));
        ExpencessImplements eimpl = new ExpencessImplements();

        try {
            e = eimpl.viewedByID(id);

            out.println("<!DOCTYPE html>");
            out.println("<html lang=\"en\">");
            out.println("<head>");
            out.println("  <meta charset=\"UTF-8\">");
            out.println("  <title>Edit Expense</title>");
            out.println("  <style>");
            out.println("    * { box-sizing: border-box; }");
            out.println("    body { margin: 0; font-family: Arial, sans-serif; background: #f0f2f5; display: flex; justify-content: center; align-items: center; height: 100vh; }");
            out.println("    .form-container { background: #fff; padding: 25px 30px; border-radius: 10px; box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1); width: 90%; max-width: 380px; }");
            out.println("    h2 { text-align: center; font-size: 22px; margin-bottom: 20px; }");
            out.println("    .form-group { margin-bottom: 12px; }");
            out.println("    .form-group label { display: block; font-size: 14px; margin-bottom: 5px; }");
            out.println("    .form-group input, .form-group select, .form-group textarea { width: 100%; padding: 8px 10px; font-size: 14px; border: 1px solid #ccc; border-radius: 5px; }");
            out.println("    .form-group textarea { height: 60px; resize: vertical; }");
            out.println("    .char-counter { font-size: 12px; color: #666; text-align: right; margin-top: 2px; }");
            out.println("    .submit-btn { width: 100%; padding: 10px; font-size: 15px; background-color: #28a745; color: white; border: none; border-radius: 6px; cursor: pointer; margin-top: 10px; }");
            out.println("    .submit-btn:hover { background-color: #218838; }");
            out.println("    @media (max-width: 400px) { .form-container { padding: 20px; } }");
            out.println("  </style>");
            out.println("  <script>");
            out.println("    function updateCharCount() {");
            out.println("      const textarea = document.getElementById(\"description\");");
            out.println("      const counter = document.getElementById(\"charCounter\");");
            out.println("      counter.textContent = textarea.value.length + \"/400\";");
            out.println("    }");
            out.println("    window.onload = function() { updateCharCount(); };");
            out.println("  </script>");
            out.println("</head>");
            out.println("<body>");

            out.println("  <div class=\"form-container\">");
            out.println("    <h2>Edit Expense</h2>");
            out.println("    <form action=\"EditExpecessServlet\" method=\"post\">");
            out.println("<input type='hidden' name='expenseId' value='" + e.getId() + "' />");

            out.println("      <div class=\"form-group\">");
            out.println("        <label for=\"name\">Expense Name</label>");
            out.println("        <input type=\"text\" id=\"name\" name=\"name\" value=\"" + (e.getName() != null ? e.getName() : "") + "\" required>");
            out.println("      </div>");

            out.println("      <div class=\"form-group\">");
            out.println("        <label for=\"description\">Description</label>");
            out.println("        <textarea id=\"description\" name=\"description\" maxlength=\"400\" oninput=\"updateCharCount()\" required>" + (e.getDescription() != null ? e.getDescription() : "") + "</textarea>");
            out.println("        <div class=\"char-counter\" id=\"charCounter\">" + (e.getDescription() != null ? e.getDescription().length() : "0") + "/400</div>");
            out.println("      </div>");

            out.println("      <div class=\"form-group\">");
            out.println("        <label for=\"basis\">Basis</label>");
            out.println("        <select id=\"basis\" name=\"basis\" required>");
            out.println("          <option value=\"\">None</option>");
            out.println("          <option value=\"daily\" " + ("daily".equals(e.getBasis()) ? "selected" : "") + ">Daily</option>");
            out.println("          <option value=\"monthly\" " + ("monthly".equals(e.getBasis()) ? "selected" : "") + ">Monthly</option>");
            out.println("        </select>");
            out.println("      </div>");

            out.println("      <div class=\"form-group\">");
            out.println("        <label for=\"amount\">Amount</label>");
            out.println("        <input type=\"text\" id=\"amount\" name=\"amount\" value=\"" + e.getAmount() + "\" required>");
            out.println("      </div>");

            out.println("      <div class=\"form-group\">");
            out.println("        <label for=\"time\">Time</label>");
            out.println("        <input type=\"time\" id=\"time\" name=\"time\" value=\"" + (e.getTime() != null ? e.getTime() : "") + "\" required>");
            out.println("      </div>");

            out.println("      <div class=\"form-group\">");
            out.println("        <label for=\"date\">Date</label>");
            out.println("        <input type=\"date\" id=\"date\" name=\"date\" value=\"" + (e.getDate() != null ? e.getDate() : "") + "\" required>");
            out.println("      </div>");

            out.println("      <div class=\"form-group\">");
            out.println("        <label for=\"category\">Category</label>");
            out.println("        <select id=\"category\" name=\"category\" required>");
            out.println("          <option value=\"\">None</option>");
            out.println("          <option value=\"food\" " + ("food".equals(e.getCategory()) ? "selected" : "") + ">Food</option>");
            out.println("          <option value=\"transport\" " + ("transport".equals(e.getCategory()) ? "selected" : "") + ">Transport</option>");
            out.println("          <option value=\"utilities\" " + ("utilities".equals(e.getCategory()) ? "selected" : "") + ">Utilities</option>");
            out.println("          <option value=\"shopping\" " + ("shopping".equals(e.getCategory()) ? "selected" : "") + ">Shopping</option>");
            out.println("          <option value=\"other\" " + ("other".equals(e.getCategory()) ? "selected" : "") + ">Other</option>");
            out.println("        </select>");
            out.println("      </div>");

            out.println("      <div class=\"form-group\">");
            out.println("        <label for=\"type\">Payment Type</label>");
            out.println("        <select id=\"type\" name=\"type\" required>");
            out.println("          <option value=\"\">None</option>");
            out.println("          <option value=\"upi\" " + ("upi".equals(e.getType()) ? "selected" : "") + ">UPI</option>");
            out.println("          <option value=\"card\" " + ("card".equals(e.getType()) ? "selected" : "") + ">Card</option>");
            out.println("          <option value=\"cash\" " + ("cash".equals(e.getType()) ? "selected" : "") + ">Cash</option>");
            out.println("        </select>");
            out.println("      </div>");

            out.println("      <button type=\"submit\" class=\"submit-btn\">Submit</button>");
            out.println("    </form>");
            out.println("  </div>");

            out.println("</body>");
            out.println("</html>");

        } catch (ClassNotFoundException | SQLException d) {
            d.printStackTrace();
            out.println("<p style='color:red; text-align:center;'>Product not found or error occurred.</p>");
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        int id = Integer.parseInt(req.getParameter("expenseId"));
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String basis = req.getParameter("basis");
        String category = req.getParameter("category");
        String type = req.getParameter("type");
        double amount = Double.parseDouble(req.getParameter("amount"));
        Date date = Date.valueOf(req.getParameter("date"));

        String timeString = req.getParameter("time");
        if (timeString != null && timeString.length() == 5) {
            timeString += ":00";
        }
        Time time = Time.valueOf(timeString);

        // Validation
        if (!name.matches("^[A-Za-z]+$")) {
            showError(req, resp, "Name must contain only letters.");
            return;
        }

        Expencess e = new Expencess();
        e.setId(id);
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
            boolean check = new ExpencessImplements().EditExpences(e);
            RequestDispatcher rd = req.getRequestDispatcher("ViewExpencesServlet");
            rd.forward(req, resp);
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            out.println("<p style='color:red; text-align:center;'>An error occurred while updating the expense.</p>");
        }
    }

    private void showError(HttpServletRequest req, HttpServletResponse resp, String message)
            throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        out.println("<p style='color:red; text-align:center; font-weight:bold; margin-top:20px;'>" + message + "</p>");
        RequestDispatcher rd = req.getRequestDispatcher("EditExpecessServlet");
        rd.include(req, resp);
    }
}
