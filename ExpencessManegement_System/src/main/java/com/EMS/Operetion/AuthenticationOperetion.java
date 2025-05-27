package com.EMS.Operetion;

import java.sql.SQLException;

import com.EMS.pojo.Authenticate;

public interface AuthenticationOperetion {
	int register(Authenticate a) throws ClassNotFoundException, SQLException;
	boolean login(Authenticate a)throws ClassNotFoundException, SQLException;
}

