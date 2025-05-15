package Com.UserManegment_system.Methods;

import java.sql.SQLException;
import java.util.List;

import Com.UserManegment_system.Pojo.Category;
import Com.UserManegment_system.Pojo.ProductItem;
import Com.UserManegment_system.Pojo.ViewOperetion;

public interface Operetion {
	
	public List<ViewOperetion> viewItemList() throws ClassNotFoundException, SQLException ;
	public boolean updateProduct(ViewOperetion item)throws ClassNotFoundException, SQLException;
	public boolean deleteList(int id)throws ClassNotFoundException, SQLException;
	public ViewOperetion getProductById(int id) throws ClassNotFoundException, SQLException;
	public List<Category> getAllCategories() throws SQLException, ClassNotFoundException;
}
