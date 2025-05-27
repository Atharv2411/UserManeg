package com.EMS.Data_other;

import java.sql.Time;

public class Times {
	public static Time getTimeing()
	{
		Time currentTime = new Time(System.currentTimeMillis());
		return currentTime;
		
	}
}
