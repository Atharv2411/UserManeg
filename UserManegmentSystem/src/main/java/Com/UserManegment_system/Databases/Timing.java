package Com.UserManegment_system.Databases;

import java.sql.Timestamp;

public class Timing {
	  public static Timestamp getTime()
	  {
		  Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		return timestamp;
	  }
}
