package Com.UserManegment_system.Pojo;

import java.sql.Timestamp;

public class ViewOperetion {
	private int id;
	private String productname,description,category;
	private Timestamp time;
    private String status;
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	  public String getStatus() {
	        return status;
	    }

	    public void setStatus(String status) {
	        this.status = status;  // Setter for status
	    }
	
}
