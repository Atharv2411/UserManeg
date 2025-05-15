package Com.UserManegment_system.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Com.UserManegment_system.Databases.EstamblishConnection;
import Com.UserManegment_system.Methods.RegMethods;
import Com.UserManegment_system.Pojo.UserRegister;

public class UserLoginImplement implements RegMethods {

    EstamblishConnection e = new EstamblishConnection();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    int check;

    @Override
    public int adduser(UserRegister ur) throws SQLException, ClassNotFoundException {
         con = e.DBConnection();
         ps = con.prepareStatement(
            "INSERT INTO Users(name, emailaddress, password, gender) VALUES (?, ?, ?, ?)"
        );


        ps.setString(1, ur.getName());
        ps.setString(2, ur.getEmail());
        ps.setString(3, ur.getPassword());
        ps.setString(4, ur.getGender());

       check = ps.executeUpdate();
        return check;
    }

    @Override
    public boolean login(UserRegister ur) throws SQLException, ClassNotFoundException {
        Connection con = e.DBConnection();  
        String query = "SELECT * FROM Users WHERE emailaddress = ? AND password = ?";
        PreparedStatement ps = con.prepareStatement(query);
        
        ps.setString(1, ur.getEmail());
        ps.setString(2, ur.getPassword());
        
        ResultSet rs = ps.executeQuery();
        boolean isValid = rs.next(); 

        
        return isValid;
    }


}
