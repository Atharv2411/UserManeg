package Com.UserManegment_system.Methods;

import java.sql.SQLException;

import Com.UserManegment_system.Pojo.ProductItem;

public interface ProductsMethod {
	int addProduct(ProductItem p) throws SQLException, ClassNotFoundException;;
}
