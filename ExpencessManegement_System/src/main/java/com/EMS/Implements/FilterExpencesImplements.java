package com.EMS.Implements;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.EMS.Data_other.DatabaseIN;
import com.EMS.pojo.Expencess;

public class FilterExpencesImplements {
	  public List<String> getDistinctCategories() throws ClassNotFoundException{
	        List<String> categories = new ArrayList<>();
	        String query = "SELECT DISTINCT category FROM expenses WHERE category IS NOT NULL AND category <> 'none'";

	        try (Connection conn = DatabaseIN.DBConnection();
	             PreparedStatement ps = conn.prepareStatement(query);
	             ResultSet rs = ps.executeQuery()) {

	            while (rs.next()) {
	                categories.add(rs.getString("category"));
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return categories;
	    }

	    public List<String> getDistinctBasis()throws ClassNotFoundException {
	        List<String> basisList = new ArrayList<>();
	        String query = "SELECT DISTINCT basis FROM expenses WHERE basis IS NOT NULL AND basis <> 'none'";

	        try (Connection conn =DatabaseIN.DBConnection();
	             PreparedStatement ps = conn.prepareStatement(query);
	             ResultSet rs = ps.executeQuery()) {

	            while (rs.next()) {
	                basisList.add(rs.getString("basis"));
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return basisList;
	    }

	    public List<Expencess> filterExpenses(String category, String basis, String startDate, String endDate) throws ClassNotFoundException {
	        List<Expencess> expenses = new ArrayList<>();
	        StringBuilder query = new StringBuilder("SELECT * FROM expenses WHERE 1=1");
	        List<Object> params = new ArrayList<>();

	        if (category != null && !category.isEmpty()) {
	            query.append(" AND category = ?");
	            params.add(category);
	        }
	        if (basis != null && !basis.isEmpty()) {
	            query.append(" AND basis = ?");
	            params.add(basis);
	        }
	        if (startDate != null && !startDate.isEmpty()) {
	            query.append(" AND date >= ?");
	            params.add(Date.valueOf(startDate));
	        }
	        if (endDate != null && !endDate.isEmpty()) {
	            query.append(" AND date <= ?");
	            params.add(Date.valueOf(endDate));
	        }

	        try (Connection conn = DatabaseIN.DBConnection();
	             PreparedStatement ps = conn.prepareStatement(query.toString())) {

	            for (int i = 0; i < params.size(); i++) {
	                ps.setObject(i + 1, params.get(i));
	            }

	            try (ResultSet rs = ps.executeQuery()) {
	                while (rs.next()) {
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
	                    expenses.add(e);
	                }
	            }

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return expenses;
	    }
	}

