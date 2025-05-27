package com.EMS.Implements;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.EMS.Data_other.DatabaseIN;
import com.EMS.Operetion.ExpencessOperetion;
import com.EMS.pojo.Expencess;


public class ExpencessImplements implements ExpencessOperetion{
	Expencess e = new Expencess();
	Connection con;
	    PreparedStatement ps;
	    ResultSet rs;
	    int check;
	    boolean valid;
    	List<Expencess> aa = new ArrayList<>();

	@Override
	public int AddExpencess(Expencess e) throws ClassNotFoundException, SQLException {
		con = DatabaseIN.DBConnection();
		ps = con.prepareStatement("insert into expenses(name,description,basis,amount,date,time,category,type,created_at)values(?,?,?,?,?,?,?,?,?)");
		ps.setString(1, e.getName());
		ps.setString(2, e.getDescription());
		ps.setString(3, e.getBasis());
		ps.setDouble(4, e.getAmount());
		ps.setDate(5,e.getDate());
		ps.setTime(6, e.getTime());
		ps.setString(7, e.getCategory());
		ps.setString(8, e.getType());
		ps.setTimestamp(9, e.getCreated_date());
		
		check = ps.executeUpdate();
		



		
		return check;
	}
	@Override
	public List<Expencess> ViewAllExpences() throws ClassNotFoundException, SQLException {

	    con = DatabaseIN.DBConnection();
	    ps = con.prepareStatement("SELECT * FROM expenses");
	    rs = ps.executeQuery();

	    aa.clear();

	    while (rs.next()) {
	        Expencess a1 = new Expencess();

	        a1.setId(rs.getInt("id"));
	        a1.setName(rs.getString("name"));
	        a1.setDescription(rs.getString("description"));
	        a1.setBasis(rs.getString("basis"));
	        a1.setAmount(rs.getDouble("amount"));
	        a1.setDate(rs.getDate("date"));
	        a1.setTime(rs.getTime("time"));
	        a1.setCategory(rs.getString("category"));
	        a1.setType(rs.getString("type"));

	        aa.add(a1);
	    }

	    return aa;
	}
	
	
	@Override
	public Expencess viewedByID(int id) throws ClassNotFoundException, SQLException {
		  con = DatabaseIN.DBConnection();
		   ps = con.prepareStatement("SELECT * FROM expenses WHERE id=?");
		    ps.setInt(1, id);
		    ResultSet rs = ps.executeQuery();


		    if (rs.next()) {
		        e.setId(id);
		        e.setName(rs.getString("name"));
		        e.setDescription(rs.getString("description"));
		        e.setBasis(rs.getString("basis"));
		        e.setAmount(rs.getDouble("amount"));
		        e.setDate(rs.getDate("date"));
		        e.setTime(rs.getTime("time"));
		        e.setCategory(rs.getString("category"));
		        e.setType(rs.getString("type"));
		        e.setCreated_date(rs.getTimestamp("created_at"));
		    }

		    rs.close();
		    ps.close();
		    con.close();

		    return e;
	}
	
	
	@Override
	public boolean EditExpences(Expencess e) throws ClassNotFoundException, SQLException {
		con = DatabaseIN.DBConnection();
	    ps = con.prepareStatement(
	        "UPDATE expenses SET name = ?, description = ?, basis = ?, amount = ?, date = ?, time = ?, category = ?, type = ?, created_at = ? WHERE id = ?");

	    ps.setString(1, e.getName());
	    ps.setString(2, e.getDescription());
	    ps.setString(3, e.getBasis());
	    ps.setDouble(4, e.getAmount());
	    ps.setDate(5, e.getDate());
	    ps.setTime(6, e.getTime());
	    ps.setString(7, e.getCategory());
	    ps.setString(8, e.getType());
	    ps.setTimestamp(9, e.getCreated_date());
	    ps.setInt(10, e.getId());

	    int check = ps.executeUpdate();

	    ps.close();
	    con.close();

	    return check > 0;
	}
	@Override
	public boolean DeleteExpences(Expencess e) throws ClassNotFoundException, SQLException {
		con = DatabaseIN.DBConnection();
	    ps = con.prepareStatement("delete from expenses where id=?");
	    ps.setInt(1, e.getId());

	    int check = ps.executeUpdate();

	    ps.close();
	    con.close();

	    return check > 0;
	}
	


}
