package Com.UserManegment_system.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Com.UserManegment_system.Databases.EstamblishConnection;
import Com.UserManegment_system.Databases.Timing;
import Com.UserManegment_system.Methods.ProductsMethod;
import Com.UserManegment_system.Pojo.ProductItem;

public class ProductItemImplement implements ProductsMethod {

    EstamblishConnection e = new EstamblishConnection();
    Timing t = new Timing();
    ProductItem p = new ProductItem();
    
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    int check;
    
    @Override
    public int addProduct(ProductItem p) throws ClassNotFoundException, SQLException {
        con = e.DBConnection();

        ps = con.prepareStatement("Insert into Item(productname,description,category,status,date) values(?,?,?,?,?)");
        
        ps.setString(1, p.getProductname());
        ps.setString(2, p.getDescription());
        ps.setString(3, p.getCategory());
        
        ps.setString(4, p.getStatus()); 
        
        ps.setTimestamp(5, p.getTime());
        
        check = ps.executeUpdate();
        return check;
    }
}
