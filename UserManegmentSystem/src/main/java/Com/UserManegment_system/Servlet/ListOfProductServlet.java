package Com.UserManegment_system.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import Com.UserManegment_system.Impl.ViewOperetionImplement;
import Com.UserManegment_system.Pojo.ViewOperetion;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ListOfProductServlet")
public class ListOfProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        ViewOperetionImplement vimp = new ViewOperetionImplement();

        try {
            List<ViewOperetion> ItemList = vimp.viewItemList();

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Product List</title>");
            out.println("<style>");

            out.println("body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #e3f2fd; color: #0277bd; }");
            out.println("header { background-color: #0288d1; color: white; padding: 20px; text-align: center; }");
            out.println("h1 { margin: 0; font-size: 2rem; }");
            out.println("nav { background-color: #01579b; padding: 10px; text-align: center; }");
            out.println("nav a { color: white; margin: 0 15px; text-decoration: none; font-weight: bold; }");
            out.println("nav a:hover { text-decoration: underline; }");


            out.println("table { border-collapse: collapse; width: 90%; margin: 30px auto; background-color: #fff; border-radius: 8px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); }");
            out.println("th, td { padding: 15px; text-align: center; border: 1px solid #bbb; }");
            out.println("th { background-color: #0288d1; color: white; font-size: 1.1rem; }");
            out.println("tr:nth-child(even) { background-color: #f1f8e9; }");
            out.println("tr:nth-child(odd) { background-color: #e1f5fe; }");


            out.println(".action-btn { padding: 6px 12px; margin: 5px; border: none; border-radius: 4px; cursor: pointer; font-size: 1rem; }");
            out.println(".edit-btn { background-color: #2196F3; color: white; }");
            out.println(".delete-btn { background-color: #f44336; color: white; }");


            out.println(".action-btn:hover { opacity: 0.9; }");
            out.println(".delete-btn:hover { background-color: #d32f2f; }");


            out.println(".center-message { text-align: center; margin: 20px; font-size: 1.2rem; color: #d32f2f; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");


            out.println("<header>");
            out.println("<h1>Available Product List</h1>");
            out.println("</header>");

         
            out.println("<nav>");
            out.println("<a href='enter.html'>Back</a>");
            out.println("<a href='ProductFormServlet'>Add Product</a>");
            out.println("<a href='home.html'>Logout</a>");
            out.println("</nav>");

            if (ItemList == null || ItemList.isEmpty()) {
                out.println("<div class='center-message'>No Products Available</div>");
            } else {
                out.println("<form action='BulkDeleteServlet' method='post'>");
                out.println("<table>");
                out.println("<thead>");
                out.println("<tr>");
                out.println("<th>Select</th>");
                out.println("<th>Product Name</th>");
                out.println("<th>Description</th>");
                out.println("<th>Category</th>");
                out.println("<th>Date</th>");
                out.println("<th>Status</th>");
                out.println("<th>Actions</th>");
                out.println("</tr>");
                out.println("</thead>");
                out.println("<tbody>");

                for (ViewOperetion item : ItemList) {
                    out.println("<tr>");
                    out.println("<td><input type='checkbox' name='selectedIds' value='" + item.getId() + "'></td>");
                    out.println("<td>" + item.getProductname() + "</td>");
                    out.println("<td>" + item.getDescription() + "</td>");
                    out.println("<td>" + item.getCategory() + "</td>");
                    out.println("<td>" + item.getTime() + "</td>");
                    out.println("<td>" + item.getStatus() + "</td>");
                    out.println("<td>");
                    out.println("<a href='EditProductServlet?productId=" + item.getId() + "' class='action-btn edit-btn'>Edit</a>");
                    out.println("<a href='DeleteItemFromList?productId=" + item.getId() + "' class='action-btn delete-btn' onclick=\"return confirm('Are you sure you want to delete this item?');\">Delete</a>");
                    out.println("</td>");
                    out.println("</tr>");
                }

                out.println("</tbody>");
                out.println("</table>");
                out.println("<div style='text-align: center; margin: 20px;'>");
                out.println("<button type='submit' class='action-btn delete-btn' onclick=\"return confirm('Are you sure you want to delete selected items?');\">Delete Selected</button>");
                out.println("</div>");
                out.println("</form>");
            }

            out.println("</body>");
            out.println("</html>");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            out.println("<p class='center-message'>Internal server error. Please try again later.</p>");
        }
    }
}
