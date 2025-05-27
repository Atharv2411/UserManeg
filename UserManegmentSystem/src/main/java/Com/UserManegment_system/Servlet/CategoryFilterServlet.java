package Com.UserManegment_system.Servlet;

import Com.UserManegment_system.Databases.EstamblishConnection;
import Com.UserManegment_system.Pojo.Category;
import Com.UserManegment_system.Pojo.ViewOperetion;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;

@WebServlet("/CategoryFilterServlet")
public class CategoryFilterServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String selectedCategory = request.getParameter("category");
        String selectedStatus = request.getParameter("status");
        String selectedProduct = request.getParameter("productname");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        ArrayList<Category> categoryList = new ArrayList<>();
        ArrayList<ViewOperetion> productList = new ArrayList<>();
        Set<String> productNames = new TreeSet<>(); 

        try {
            Connection conn = EstamblishConnection.DBConnection();


            String catQuery = "SELECT * FROM Category";
            PreparedStatement catPs = conn.prepareStatement(catQuery);
            ResultSet catRs = catPs.executeQuery();

            while (catRs.next()) {
                Category cat = new Category();
                cat.setId(catRs.getInt("id"));
                cat.setName(catRs.getString("category"));
                categoryList.add(cat);
            }

            String productQuery = "SELECT DISTINCT productname FROM Item";
            PreparedStatement productPs = conn.prepareStatement(productQuery);
            ResultSet productRs = productPs.executeQuery();
            while (productRs.next()) {
                productNames.add(productRs.getString("productname"));
            }
            productRs.close();
            productPs.close();

            StringBuilder prodQuery = new StringBuilder("SELECT * FROM Item WHERE 1=1");
            List<String> params = new ArrayList<>();
            
            if (selectedCategory != null && !selectedCategory.isEmpty()) {
                prodQuery.append(" AND category = ?");
                params.add(selectedCategory);
            }
            
            if (selectedStatus != null && !selectedStatus.isEmpty() && !selectedStatus.equalsIgnoreCase("all")) {
                prodQuery.append(" AND status = ?");
                params.add(selectedStatus);
            }
            
            if (selectedProduct != null && !selectedProduct.isEmpty() && !selectedProduct.equalsIgnoreCase("all")) {
                prodQuery.append(" AND productname = ?");
                params.add(selectedProduct);
            }

            PreparedStatement prodPs = conn.prepareStatement(prodQuery.toString());
            for (int i = 0; i < params.size(); i++) {
                prodPs.setString(i + 1, params.get(i));
            }

            ResultSet prodRs = prodPs.executeQuery();
            while (prodRs.next()) {
                ViewOperetion vo = new ViewOperetion();
                vo.setId(prodRs.getInt("id"));
                vo.setProductname(prodRs.getString("productname"));
                vo.setDescription(prodRs.getString("description"));
                vo.setCategory(prodRs.getString("category"));
                vo.setStatus(prodRs.getString("status"));
                vo.setTime(prodRs.getTimestamp("date"));
                productList.add(vo);
            }

            prodRs.close();
            prodPs.close();
            catRs.close();
            catPs.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<p>Error: " + e.getMessage() + "</p>");
        }

        out.println("<html><head><title>Product Filter</title>");
        out.println("<style>");
        out.println("body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background: #f0f8ff; margin: 0; }");
        out.println(".navbar { background-color: #4682b4; padding: 15px; display: flex; justify-content: space-between; align-items: center; }");
        out.println(".navbar a { color: white; margin: 0 15px; text-decoration: none; font-weight: bold; }");
        out.println(".navbar a:hover { text-decoration: underline; }");
        out.println(".container { padding: 40px; max-width: 1000px; margin: auto; color: #333; }");
        out.println(".filter-form { background: #ffffff; padding: 20px; border-radius: 10px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); margin-bottom: 20px; }");
        out.println(".filter-row { display: flex; gap: 15px; margin-bottom: 15px; }");
        out.println(".filter-group { flex: 1; }");
        out.println("label { display: block; margin-bottom: 5px; font-size: 1rem; color: #4682b4; font-weight: 500; }");
        out.println("select, button { width: 100%; padding: 10px; font-size: 1rem; border: 1px solid #ccc; border-radius: 5px; }");
        out.println("button { background-color: #5f9ea0; color: white; border: none; cursor: pointer; transition: background-color 0.3s ease; }");
        out.println("button:hover { background-color: #3b7d7d; }");
        
        out.println(".table-container { margin: 30px 0; overflow-x: auto; box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08); border-radius: 8px; }");
        out.println(".data-table { width: 100%; border-collapse: separate; border-spacing: 0; font-family: 'Segoe UI', 'Helvetica Neue', Arial, sans-serif; font-size: 0.875rem; color: #333; background: #fff; border-radius: 8px; overflow: hidden; }");
        out.println(".data-table thead { background: linear-gradient(135deg, #4682b4 0%, #3a6d99 100%); color: white; text-transform: uppercase; font-size: 0.75rem; letter-spacing: 0.5px; }");
        out.println(".data-table th { padding: 16px 12px; text-align: left; font-weight: 600; position: relative; }");
        out.println(".data-table th:not(:last-child)::after { content: ''; position: absolute; right: 0; top: 50%; transform: translateY(-50%); height: 60%; width: 1px; background: rgba(255, 255, 255, 0.2); }");
        out.println(".data-table tbody tr { transition: all 0.2s ease; border-bottom: 1px solid #f0f0f0; }");
        out.println(".data-table tbody tr:last-child { border-bottom: none; }");
        out.println(".data-table tbody tr:hover { background-color: #f8fafc; box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05); }");
        out.println(".data-table td { padding: 14px 12px; vertical-align: middle; line-height: 1.4; border-bottom: 1px solid #f0f0f0; }");
        out.println(".data-table td:first-child { text-align: center; }");
        out.println(".data-table tr:last-child td { border-bottom: none; }");
        out.println(".data-table tbody tr:nth-child(even) { background-color: #f9f9f9; }");
        out.println(".data-table tbody tr:nth-child(even):hover { background-color: #f8fafc; }");
        out.println(".action-btn { display: inline-block; padding: 6px 12px; border-radius: 4px; text-decoration: none; font-size: 0.75rem; font-weight: 500; transition: all 0.2s ease; margin: 0 2px; }");
        out.println(".edit-btn { color: #2c7be5; border: 1px solid #2c7be5; background: transparent; }");
        out.println(".edit-btn:hover { background-color: rgba(44, 123, 229, 0.1); }");
        out.println(".delete-btn { color: #e63757; border: 1px solid #e63757; background: transparent; }");
        out.println(".delete-btn:hover { background-color: rgba(230, 55, 87, 0.1); }");
        out.println(".data-table input[type='checkbox'] { width: 16px; height: 16px; cursor: pointer; accent-color: #4682b4; }");
        out.println(".status-active { color: #00a854; font-weight: 500; }");
        out.println(".status-inactive { color: #f5222d; font-weight: 500; }");
        out.println("@media (max-width: 768px) { .filter-row { flex-direction: column; } .data-table { font-size: 0.8125rem; } .data-table th, .data-table td { padding: 12px 8px; } .action-btn { padding: 4px 8px; margin: 0 1px; } }");
        
        out.println("p { text-align: center; margin-top: 20px; font-size: 1.1rem; color: #4682b4; }");
        out.println("</style></head><body>");

        out.println("<div class='navbar'>");
        out.println("<div><a href='enter.html'>&larr; Back</a></div>");
        out.println("<div>");
        out.println("<a href='ProductFormServlet'>Create Record</a>");
        out.println("<a href='ListOfProductServlet'>View All Records</a>");
        out.println("</div>");
        out.println("</div>");

        out.println("<div class='container'>");

        out.println("<form class='filter-form' action='CategoryFilterServlet' method='get'>");
        out.println("<div class='filter-row'>");
        
        out.println("<div class='filter-group'>");
        out.println("<label for='category'>Category</label>");
        out.println("<select id='category' name='category'>");
        out.println("<option value=''>-- All Categories --</option>");
        for (Category cat : categoryList) {
            String selected = cat.getName().equals(selectedCategory) ? "selected" : "";
            out.println("<option value='" + cat.getName() + "' " + selected + ">" + cat.getName() + "</option>");
        }
        out.println("</select>");
        out.println("</div>");
        
        out.println("<div class='filter-group'>");
        out.println("<label for='status'>Status</label>");
        out.println("<select id='status' name='status'>");
        out.println("<option value='all'" + ("all".equalsIgnoreCase(selectedStatus) ? " selected" : "") + ">-- All Statuses --</option>");
        out.println("<option value='active'" + ("active".equalsIgnoreCase(selectedStatus) ? " selected" : "") + ">Active</option>");
        out.println("<option value='inactive'" + ("inactive".equalsIgnoreCase(selectedStatus) ? " selected" : "") + ">Inactive</option>");
        out.println("</select>");
        out.println("</div>");
        
        out.println("<div class='filter-group'>");
        out.println("<label for='productname'>Product Name</label>");
        out.println("<select id='productname' name='productname'>");
        out.println("<option value='all'>-- All Products --</option>");
        for (String product : productNames) {
            String selected = product.equalsIgnoreCase(selectedProduct) ? "selected" : "";
            out.println("<option value='" + product + "' " + selected + ">" + product + "</option>");
        }
        out.println("</select>");
        out.println("</div>");
        out.println("</div>");
        
        out.println("<button type='submit'>Filter Products</button>");
        out.println("</form>");

        if (selectedCategory != null || selectedStatus != null || selectedProduct != null) {
            if (productList.isEmpty()) {
                out.println("<p>No products found matching the selected filters.</p>");
            } else {
                out.println("<form action='BulkDeleteServlet' method='post'>");
                out.println("<div class='table-container'>");
                out.println("<table class='data-table'>");
                out.println("<thead>");
                out.println("<tr>");
                out.println("<th style='width: 5%;'>Select</th>");
                out.println("<th style='width: 20%;'>Product Name</th>");
                out.println("<th style='width: 25%;'>Description</th>");
                out.println("<th style='width: 15%;'>Category</th>");
                out.println("<th style='width: 10%;'>Status</th>");
                out.println("<th style='width: 15%;'>Date</th>");
                out.println("<th style='width: 10%;'>Actions</th>");
                out.println("</tr>");
                out.println("</thead>");
                out.println("<tbody>");

                for (ViewOperetion p : productList) {
                    out.println("<tr>");
                    out.println("<td><input type='checkbox' name='selectedIds' value='" + p.getId() + "'></td>");
                    out.println("<td>" + p.getProductname() + "</td>");
                    out.println("<td>" + p.getDescription() + "</td>");
                    out.println("<td>" + p.getCategory() + "</td>");
                    out.println("<td class='" + (p.getStatus().equalsIgnoreCase("active") ? "status-active" : "status-inactive") + "'>" + p.getStatus() + "</td>");
                    out.println("<td>" + p.getTime() + "</td>");
                    out.println("<td>");
                    out.println("<a href='EditProductServlet?productId=" + p.getId() + "' class='action-btn edit-btn'>Edit</a> | ");
                    out.println("<a href='DeleteItemFromList?productId=" + p.getId() + "' class='action-btn delete-btn' onclick=\"return confirm('Are you sure you want to delete this item?');\">Delete</a>");
                    out.println("</td>");
                    out.println("</tr>");
                }

                out.println("</tbody>");
                out.println("</table>");
                out.println("</div>");
                out.println("<div style='text-align: center; margin: 20px;'>");
                out.println("<button type='submit' class='action-btn delete-btn' onclick=\"return confirm('Are you sure you want to delete selected items?');\">Delete Selected</button>");
                out.println("</div>");
                out.println("</form>");
            }
        }

        out.println("</div>"); 
        out.println("</body></html>");
    }
}