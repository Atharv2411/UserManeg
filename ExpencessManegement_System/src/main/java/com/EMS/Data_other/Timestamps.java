package com.EMS.Data_other;

import java.sql.Timestamp;

public class Timestamps {
	public static Timestamp getTime()
	  {
		  Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		return timestamp;
	  }
}
