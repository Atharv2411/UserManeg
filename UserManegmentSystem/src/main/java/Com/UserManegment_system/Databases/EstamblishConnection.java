package Com.UserManegment_system.Databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class EstamblishConnection {
	public static Connection DBConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/UserManegmentSystem","root","atharv@2411");
		return con;
	}

}
