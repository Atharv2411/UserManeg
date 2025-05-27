package Com.UserManegment_system.Servlet;

import Com.UserManegment_system.Databases.EstamblishConnection;
import Com.UserManegment_system.Databases.Timing;
import Com.UserManegment_system.Impl.ViewOperetionImplement;
import Com.UserManegment_system.Pojo.Category;
import Com.UserManegment_system.Pojo.ProductItem;
import Com.UserManegment_system.Pojo.ViewOperetion;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/EditProductServlet")
public class EditProductServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("productId"));
        ViewOperetionImplement dao = new ViewOperetionImplement();

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            ViewOperetion product = dao.getProductById(id);
            List<Category> categories = dao.getAllCategories();

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Edit Product</title>");
            out.println("<style>");
            out.println("body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background: linear-gradient(to bottom, #64b5f6, #bbdefb); margin: 0; padding: 0; }");
            out.println(".container { width: 450px; margin: 50px auto; background-color: #ffffff; padding: 30px;");
            out.println("border-radius: 12px; box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1); }");
            out.println("h2 { text-align: center; color: #1565c0; font-size: 24px; margin-bottom: 20px; }");
            out.println("form input[type='text'], form input[type='hidden'], form select { width: 100%; padding: 12px;");
            out.println("margin: 10px 0; border: 1px solid #1565c0; border-radius: 8px; font-size: 16px; background-color: #e3f2fd; }");
            out.println("textarea { width: 100%; height: 120px; padding: 12px; margin: 10px 0; border: 1px solid #1565c0;");
            out.println("border-radius: 8px; font-size: 16px; background-color: #e3f2fd; resize: vertical; font-family: inherit; }");
            out.println(".char-count { font-size: 12px; color: #666; text-align: right; margin-top: -10px; margin-bottom: 10px; }");
            out.println(".char-count.warning { color: #ff9800; }");
            out.println(".char-count.error { color: #f44336; }");
            out.println("form input[type='submit'] { width: 100%; background-color: #1565c0; color: white;");
            out.println("padding: 12px; border: none; border-radius: 8px; cursor: pointer; font-size: 16px; transition: all 0.3s ease; }");
            out.println("form input[type='submit']:hover { background-color: #0d47a1; box-shadow: 0 4px 8px rgba(13, 71, 161, 0.3); transform: translateY(-2px); }");
            out.println(".switch { position: relative; display: inline-block; width: 50px; height: 24px; margin-left: 10px; }");
            out.println(".switch input { opacity: 0; width: 0; height: 0; }");
            out.println(".slider { position: absolute; cursor: pointer; top: 0; left: 0; right: 0; bottom: 0;");
            out.println("background-color: #bbdefb; transition: .4s; border-radius: 24px; box-shadow: inset 0 2px 4px rgba(0, 0, 0, 0.1); }");
            out.println(".slider:before { position: absolute; content: \"\"; height: 18px; width: 18px; left: 3px; bottom: 3px;");
            out.println("background-color: white; transition: .4s; border-radius: 50%; }");
            out.println(".switch input:checked + .slider { background-color: #1565c0; }");
            out.println(".switch input:checked + .slider:before { transform: translateX(26px); }");
            out.println("</style>");
            out.println("<script>");
            out.println("function updateCharCount() {");
            out.println("  const textarea = document.getElementById('description');");
            out.println("  const charCount = document.getElementById('charCount');");
            out.println("  const remaining = 400 - textarea.value.length;");
            out.println("  charCount.textContent = remaining + ' characters remaining';");
            out.println("  charCount.className = 'char-count';");
            out.println("  if (remaining < 50) charCount.className += ' warning';");
            out.println("  if (remaining < 0) charCount.className += ' error';");
            out.println("}");
            out.println("</script>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='container'>");
            out.println("<h2>Edit Product</h2>");
            out.println("<form action='EditProductServlet' method='post'>");
            out.println("<input type='hidden' name='productId' value='" + product.getId() + "' />");
            out.println("Product Name: <input type='text' name='productname' value='" + product.getProductname() + "' required/><br/>");

            out.println("Description: <textarea id='description' name='description' maxlength='400' oninput='updateCharCount()' required>" + product.getDescription() + "</textarea>");
            out.println("<div id='charCount' class='char-count'>" + (400 - (product.getDescription() != null ? product.getDescription().length() : 0)) + " characters remaining</div>");


            out.println("Category: <select name='category' required>");
            for (Category cat : categories) {
                String selected = cat.getName().equalsIgnoreCase(product.getCategory()) ? "selected" : "";
                out.println("<option value='" + cat.getName() + "' " + selected + ">" + cat.getName() + "</option>");
            }
            out.println("</select><br/>");


            out.println("<label for='status'>Status:</label>");
            out.println("<label class='switch'>");
            out.println("<input type='checkbox' name='status' id='status' " +
                    ("Active".equalsIgnoreCase(product.getStatus()) ? "checked" : "") + ">");
            out.println("<span class='slider round'></span>");
            out.println("</label><br/><br/>");

            out.println("<input type='submit' value='Update'/>");
            out.println("</form>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<p style='color:red; text-align:center;'>Product not found or error occurred.</p>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("productId"));
        String name = request.getParameter("productname");
        String description = request.getParameter("description");
        String category = request.getParameter("category");
        String status = request.getParameter("status") != null ? "Active" : "Inactive";
        
        ViewOperetion item = new ViewOperetion();
        item.setId(id);
        item.setProductname(name);
        item.setDescription(description);
        item.setCategory(category);
        item.setStatus(status);
        item.setTime(Timing.getTime());

        ViewOperetionImplement dao = new ViewOperetionImplement();
        try {
            dao.updateProduct(item);
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("ListOfProductServlet");
    }
}