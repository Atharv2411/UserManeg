package com.EMS.Data_other;

import java.sql.Date;

public class Dates {
	public static Date getDate() {
		Date currentDate = new Date(System.currentTimeMillis());
		return currentDate;
		
	}
}
