package com.EMS.Implements;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.EMS.Data_other.DatabaseIN;
import com.EMS.Operetion.AuthenticationOperetion;
import com.EMS.pojo.Authenticate;

public class AuthenticateImplementation implements AuthenticationOperetion {
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    int check;
    boolean valid;

    @Override
    public int register(Authenticate a) throws ClassNotFoundException, SQLException {
        con = DatabaseIN.DBConnection();
        ps = con.prepareStatement("INSERT INTO Auth(username, password, email, gender) VALUES (?, ?, ?, ?)");

        ps.setString(1, a.getName());
        ps.setString(2, a.getPassword());
        ps.setString(3, a.getEmail());
        ps.setString(4, a.getGender());

        check = ps.executeUpdate();



        return check;
    }

    @Override
    public boolean login(Authenticate a) throws ClassNotFoundException, SQLException {
        con=DatabaseIN.DBConnection();
        ps = con.prepareStatement("select * from Auth where email=? and password=?");
        
        ps.setString(1, a.getEmail());
        ps.setString(2, a.getPassword());
        
        rs = ps.executeQuery();
        valid= rs.next();
        
        return valid;
        
    }
}
