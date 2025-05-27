package com.EMS.Operetion;

import java.sql.SQLException;
import java.util.List;

import com.EMS.pojo.Expencess;


public interface ExpencessOperetion {
	public int AddExpencess(Expencess e) throws ClassNotFoundException, SQLException;
	public List<Expencess> ViewAllExpences() throws ClassNotFoundException, SQLException; 
	public boolean EditExpences(Expencess e) throws ClassNotFoundException, SQLException;
	public Expencess viewedByID(int id) throws ClassNotFoundException, SQLException;
	public boolean DeleteExpences(Expencess e) throws ClassNotFoundException, SQLException;
}
