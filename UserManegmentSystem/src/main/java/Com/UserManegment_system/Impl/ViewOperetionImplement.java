package Com.UserManegment_system.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Com.UserManegment_system.Databases.EstamblishConnection;
import Com.UserManegment_system.Databases.Timing;
import Com.UserManegment_system.Methods.Operetion;
import Com.UserManegment_system.Pojo.Category;
import Com.UserManegment_system.Pojo.ProductItem;
import Com.UserManegment_system.Pojo.ViewOperetion;

public class ViewOperetionImplement implements Operetion {
    EstamblishConnection e = new EstamblishConnection();
    ViewOperetion a1 = new ViewOperetion();
	 Connection con;
	    PreparedStatement ps;
	    ResultSet rs;
	    int check;
    	List<ViewOperetion> aa = new ArrayList<>();
	@Override
	public List<ViewOperetion> viewItemList() throws ClassNotFoundException, SQLException {


		con=e.DBConnection();
    	ps = con.prepareStatement("Select * from Item");
    	rs = ps.executeQuery();
    
    	aa.clear();
    	while (rs.next()) {
        	ViewOperetion a1 = new ViewOperetion();
        	a1.setId(rs.getInt("id"));
        	a1.setProductname(rs.getString("productname"));
        	a1.setDescription(rs.getString("description"));
        	a1.setCategory(rs.getString("category"));
        	a1.setTime(rs.getTimestamp("date"));
        	a1.setStatus(rs.getString("status"));
        	
        	
        	aa.add(a1);
    	}
    	return aa;

	}

	@Override
	public boolean deleteList(int id) throws ClassNotFoundException, SQLException {
	    boolean rowDeleted = false;
	    try (Connection con = new EstamblishConnection().DBConnection()) {
	        String sql = "DELETE FROM Item WHERE id = ?";
	        PreparedStatement ps = con.prepareStatement(sql);
	        ps.setInt(1, id);
	        rowDeleted = ps.executeUpdate() > 0;
	    }
	    return rowDeleted;
	}

	public boolean updateProduct(ViewOperetion item) throws ClassNotFoundException, SQLException {
	    Connection conn = EstamblishConnection.DBConnection();
	    String sql = "UPDATE Item SET productname=?, description=?, category=?, status=?, date=? WHERE id=?";
	    PreparedStatement ps = conn.prepareStatement(sql);

	    ps.setString(1, item.getProductname());
	    ps.setString(2, item.getDescription());

	    ps.setString(3, item.getCategory()); 

	    ps.setString(4, item.getStatus());
	    ps.setTimestamp(5, Timing.getTime());
	    ps.setInt(6, item.getId());

	    int rowsUpdated = ps.executeUpdate();

	    ps.close();
	    conn.close();

	    return rowsUpdated > 0;
	}


	public ViewOperetion getProductById(int id) throws ClassNotFoundException, SQLException {
	    ViewOperetion item = null;

	    Connection conn = EstamblishConnection.DBConnection();
	    String sql = "SELECT * FROM Item WHERE id = ?";
	    PreparedStatement ps = conn.prepareStatement(sql);
	    ps.setInt(1, id);

	    ResultSet rs = ps.executeQuery();

	    if (rs.next()) {
	        item = new ViewOperetion();
	        item.setId(rs.getInt("id"));
	        item.setProductname(rs.getString("productname"));
	        item.setDescription(rs.getString("description"));
	        item.setCategory(rs.getString("category"));
	        item.setStatus(rs.getString("status"));
	        item.setTime(rs.getTimestamp("date")); 
	    }

	    rs.close();
	    ps.close();
	    conn.close();

	    return item;
	}

	public List<Category> getAllCategories() throws SQLException, ClassNotFoundException {
	    List<Category> categories = new ArrayList<>();
	    Connection conn = EstamblishConnection.DBConnection();
	    String sql = "SELECT * FROM category";
	    PreparedStatement ps = conn.prepareStatement(sql);
	    ResultSet rs = ps.executeQuery();

	    while (rs.next()) {
	        Category cat = new Category();
	        cat.setId(rs.getInt("id"));
	        cat.setName(rs.getString("category")); 
	        categories.add(cat);
	    }
	    return categories;
	}



}
	

