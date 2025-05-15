package Com.UserManegment_system.Methods;

import java.sql.SQLException;

import Com.UserManegment_system.Pojo.UserRegister;

public interface RegMethods {
	 int adduser(UserRegister ur) throws SQLException, ClassNotFoundException;
	 boolean login(UserRegister ur)throws SQLException, ClassNotFoundException;
}
