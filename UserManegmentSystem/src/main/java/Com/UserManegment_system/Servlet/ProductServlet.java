package Com.UserManegment_system.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import Com.UserManegment_system.Databases.Timing;
import Com.UserManegment_system.Impl.ProductItemImplement;
import Com.UserManegment_system.Pojo.ProductItem;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ProductServlet")
public class ProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductItem pi = new ProductItem();
        ProductItemImplement pimp = new ProductItemImplement();
        Timing t = new Timing();

        PrintWriter out = resp.getWriter();
        String name, cat, desc, status;
        int result;

        name = req.getParameter("name");
        cat = req.getParameter("category");
        desc = req.getParameter("desc");
        status = req.getParameter("status") != null ? "active" : "inactive";
        
        System.out.println(cat);

        pi.setProductname(name);
        pi.setCategory(cat);
        pi.setDescription(desc);
        pi.setTime(t.getTime());
        pi.setStatus(status); 

        try {
            result = pimp.addProduct(pi);
            if (result > 0) {
                out.println("<p style='color:green;'>Product added successfully.</p>");
                RequestDispatcher rd = req.getRequestDispatcher("ListOfProductServlet");
                rd.forward(req, resp);
            } else {
                out.println("<p style='color:red;'>Something went wrong.</p>");
                RequestDispatcher rd = req.getRequestDispatcher("ProductFormServlet");
                rd.include(req, resp);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            out.println("<p style='color:red;'>An error occurred: " + e.getMessage() + "</p>");
        }
    }
}
