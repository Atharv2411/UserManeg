package Com.UserManegment_system.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import Com.UserManegment_system.Databases.EstamblishConnection;
import Com.UserManegment_system.Pojo.Category;

@WebServlet("/ProductFormServlet")
public class ProductFormServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        List<Category> categories = new ArrayList<>();

        try (Connection conn = EstamblishConnection.DBConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT id, category FROM category");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Category cat = new Category();
                cat.setId(rs.getInt("id"));
                cat.setName(rs.getString("category"));
                categories.add(cat);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

 
        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Add Product</title>");
        out.println("<style>");
        out.println("body { font-family: 'Segoe UI', sans-serif; background: linear-gradient(to bottom, #e0f7fa, #b3e5fc); display: flex; justify-content: center; align-items: center; min-height: 100vh; margin: 0; padding: 20px; }");
        out.println(".form-container { background: #ffffff; padding: 2rem; border-radius: 1rem; box-shadow: 0 12px 24px rgba(0,0,0,0.1); width: 500px; max-width: 95%; }");
        out.println(".form-container h2 { text-align: center; color: #01579b; margin-bottom: 2rem; }");
        out.println(".form-group { margin-bottom: 1.5rem; }");
        out.println(".form-group label { display: block; margin-bottom: 0.5rem; font-weight: 600; color: #0277bd; }");
        out.println(".form-group input, .form-group textarea, .form-group select { width: 100%; padding: 0.75rem; border: 1px solid #b3e5fc; border-radius: 0.5rem; background-color: #e1f5fe; color: #01579b; font-size: 1rem; box-sizing: border-box; }");
        out.println(".form-group textarea { resize: vertical; min-height: 100px; font-family: inherit; line-height: 1.5; }");
        out.println(".form-group select:invalid { color: #90a4ae; }");
        out.println(".form-group button { width: 100%; padding: 0.75rem; background-color: #0288d1; color: white; font-size: 1rem; border: none; border-radius: 0.5rem; cursor: pointer; transition: background-color 0.3s ease; }");
        out.println(".form-group button:hover { background-color: #01579b; }");
        out.println(".toggle-container { display: flex; justify-content: space-between; align-items: center; }");
        out.println(".switch { position: relative; display: inline-block; width: 50px; height: 24px; }");
        out.println(".switch input { opacity: 0; width: 0; height: 0; }");
        out.println(".slider { position: absolute; cursor: pointer; top: 0; left: 0; right: 0; bottom: 0; background-color: #b3e5fc; transition: 0.4s; border-radius: 24px; }");
        out.println(".slider:before { position: absolute; content: ''; height: 18px; width: 18px; left: 3px; bottom: 3px; background-color: white; transition: 0.4s; border-radius: 50%; }");
        out.println("input:checked + .slider { background-color: #0288d1; }");
        out.println("input:checked + .slider:before { transform: translateX(26px); }");
        out.println(".char-count { font-size: 0.8rem; color: #607d8b; text-align: right; margin-top: 0.25rem; }");
        out.println(".char-count.warning { color: #ff9800; }");
        out.println(".char-count.error { color: #f44336; }");
        out.println(".error-message { color: red; text-align: center; font-weight: bold; margin-top: 20px; }");
        out.println("</style>");
        out.println("<script>");
        out.println("function updateCharCount(textarea) {");
        out.println("  const maxChars = 400;");
        out.println("  const currentChars = textarea.value.length;");
        out.println("  const charCount = document.getElementById('charCount');");
        out.println("  charCount.textContent = currentChars + '/' + maxChars + ' characters';");
        out.println("  ");
        out.println("  // Remove all classes first");
        out.println("  charCount.classList.remove('warning', 'error');");
        out.println("  ");
        out.println("  if (currentChars > maxChars) {");
        out.println("    charCount.classList.add('error');");
        out.println("    textarea.style.borderColor = '#f44336';");
        out.println("  } else if (currentChars > maxChars * 0.8) {");
        out.println("    charCount.classList.add('warning');");
        out.println("    textarea.style.borderColor = '#ff9800';");
        out.println("  } else {");
        out.println("    textarea.style.borderColor = '#b3e5fc';");
        out.println("  }");
        out.println("}");
        out.println("</script>");
        out.println("</head>");
        out.println("<body>");

        out.println("<form class='form-container' action='ProductServlet' method='get'>");
        out.println("<h2>Add Product</h2>");

        String error = request.getParameter("error");
        if (error != null && !error.isEmpty()) {
            out.println("<p class='error-message'>" + error + "</p>");
        }

        out.println("<div class='form-group'>");
        out.println("<label for='productname'>Product Name</label>");
        out.println("<input type='text' id='productname' name='name' placeholder='Enter product name' required value='" + 
                   (request.getParameter("name") != null ? request.getParameter("name") : "") + "'>");
        out.println("</div>");

        out.println("<div class='form-group'>");
        out.println("<label for='description'>Description</label>");
        out.println("<textarea id='description' name='desc' placeholder='Enter product description (max 400 characters)' ");
        out.println("          required oninput='updateCharCount(this)' maxlength='400'>" + 
                   (request.getParameter("desc") != null ? request.getParameter("desc") : "") + "</textarea>");
        out.println("<div id='charCount' class='char-count'>0/400 characters</div>");
        out.println("</div>");

        out.println("<div class='form-group'>");
        out.println("<label for='category'>Category</label>");
        out.println("<select id='category' name='category' required>");
        out.println("<option value='' disabled selected>-- Select Category --</option>");
        for (Category cat : categories) {
            String selected = (request.getParameter("category") != null && 
                             request.getParameter("category").equals(cat.getName())) ? "selected" : "";
            out.println("<option value='" + cat.getName() + "' " + selected + ">" + cat.getName() + "</option>");
        }
        out.println("</select>");
        out.println("</div>");

        out.println("<div class='form-group'>");
        out.println("<label class='toggle-container'>");
        out.println("<span>Status</span>");
        out.println("<label class='switch'>");
        String statusChecked = (request.getParameter("status") == null || 
                              request.getParameter("status").equals("active")) ? "checked" : "";
        out.println("<input type='checkbox' name='status' value='active' " + statusChecked + ">");
        out.println("<span class='slider'></span>");
        out.println("</label>");
        out.println("</label>");
        out.println("</div>");

        out.println("<div class='form-group'>");
        out.println("<button type='submit'>Submit</button>");
        out.println("</div>");

        out.println("</form>");
        out.println("</body>");
        out.println("</html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String desc = request.getParameter("desc");
        String category = request.getParameter("category");
        String status = request.getParameter("status");

        String namepattern = "^[A-Za-z\\s]+$";
        if (!name.matches(namepattern)) {
            request.setAttribute("error", "Product name must contain only letters and spaces.");
            RequestDispatcher rd = request.getRequestDispatcher("/ProductFormServlet");
            rd.forward(request, response);
            return;
        }

        response.sendRedirect("ListOfProductServlet");     }
}