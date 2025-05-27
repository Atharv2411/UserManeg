package com.EMS.Implements;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.EMS.Data_other.DatabaseIN;
import com.EMS.pojo.Ammount;
import com.EMS.pojo.Expencess;

public class BasisExpencesSelectImplement {
	
	
	public List<Expencess> getExpensesForDay(Date day) throws SQLException, ClassNotFoundException {
	    List<Expencess> result = new ArrayList<>();
	    String sql = "SELECT * FROM expenses WHERE date = ?";
	    try (Connection con = DatabaseIN.DBConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        ps.setDate(1, day);
	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                Expencess e = mapResultSetToExpense(rs);
	                result.add(e);
	            }
	        }
	    }
	    return result;
	}

	public List<Expencess> getExpensesForMonth(int year, int month) throws SQLException, ClassNotFoundException {
	    List<Expencess> result = new ArrayList<>();
	    String sql = "SELECT * FROM expenses WHERE YEAR(date) = ? AND MONTH(date) = ?";
	    try (Connection con = DatabaseIN.DBConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        ps.setInt(1, year);
	        ps.setInt(2, month);
	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                Expencess e = mapResultSetToExpense(rs);
	                result.add(e);
	            }
	        }
	    }
	    return result;
	}

	public List<Expencess> getExpensesForYear(int year) throws SQLException, ClassNotFoundException {
	    List<Expencess> result = new ArrayList<>();
	    String sql = "SELECT * FROM expenses WHERE YEAR(date) = ?";
	    try (Connection con = DatabaseIN.DBConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {
	        ps.setInt(1, year);
	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                Expencess e = mapResultSetToExpense(rs);
	                result.add(e);
	            }
	        }
	    }
	    return result;
	}

	private Expencess mapResultSetToExpense(ResultSet rs) throws SQLException {
	    Expencess e = new Expencess();
	    e.setId(rs.getInt("id"));
	    e.setName(rs.getString("name"));
	    e.setDescription(rs.getString("description"));
	    e.setBasis(rs.getString("basis"));
	    e.setAmount(rs.getDouble("amount"));
	    e.setDate(rs.getDate("date"));
	    e.setTime(rs.getTime("time"));
	    e.setCategory(rs.getString("category"));
	    e.setType(rs.getString("type"));
	    e.setCreated_date(rs.getTimestamp("created_at"));
	    return e;
	}
	
	
	public List<Ammount> calculateTotalForDay(Date day) throws SQLException, ClassNotFoundException {
	        List<Ammount> result = new ArrayList<>();
	        String sql = "SELECT DATE_FORMAT(date, '%Y-%m-%d') AS period, SUM(amount) AS total " +
	                     "FROM expenses WHERE date = ? GROUP BY period";

	        try (Connection con = DatabaseIN.DBConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.setDate(1, day);
	            try (ResultSet rs = ps.executeQuery()) {
	                while (rs.next()) {
	                    Ammount summary = new Ammount();
	                    summary.setPeriod(rs.getString("period"));
	                    summary.setTotalAmount(rs.getDouble("total"));
	                    result.add(summary);
	                }
	            }
	        }
	        return result;
	    }

	    public List<Ammount> calculateTotalForMonth(int year, int month) throws SQLException, ClassNotFoundException {
	        List<Ammount> result = new ArrayList<>();
	        String sql = "SELECT DATE_FORMAT(date, '%Y-%m') AS period, SUM(amount) AS total " +
	                     "FROM expenses WHERE YEAR(date) = ? AND MONTH(date) = ? GROUP BY period";

	        try (Connection con = DatabaseIN.DBConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.setInt(1, year);
	            ps.setInt(2, month);

	            try (ResultSet rs = ps.executeQuery()) {
	                while (rs.next()) {
	                    Ammount summary = new Ammount();
	                    summary.setPeriod(rs.getString("period"));
	                    summary.setTotalAmount(rs.getDouble("total"));
	                    result.add(summary);
	                }
	            }
	        }
	        return result;
	    }

	    public List<Ammount> calculateTotalForYear(int year) throws SQLException, ClassNotFoundException {
	        List<Ammount> result = new ArrayList<>();
	        String sql = "SELECT YEAR(date) AS period, SUM(amount) AS total " +
	                     "FROM expenses WHERE YEAR(date) = ? GROUP BY period";

	        try (Connection con = DatabaseIN.DBConnection();
	             PreparedStatement ps = con.prepareStatement(sql)) {

	            ps.setInt(1, year);
	            try (ResultSet rs = ps.executeQuery()) {
	                while (rs.next()) {
	                    Ammount summary = new Ammount();
	                    summary.setPeriod(String.valueOf(rs.getInt("period")));
	                    summary.setTotalAmount(rs.getDouble("total"));
	                    result.add(summary);
	                }
	            }
	        }
	        return result;
	    }
	}