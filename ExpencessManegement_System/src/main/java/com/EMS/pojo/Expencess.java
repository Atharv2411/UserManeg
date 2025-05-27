	package com.EMS.pojo;
	
	import java.sql.*;
	
	public class Expencess {
		private int id;
		private double amount;
		private String name ,description,basis,category,type;
		private Date date ;
		private Time time;
		private Timestamp created_date;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public double getAmount() {
			return amount;
		}
		public void setAmount(double amount) {
			this.amount = amount;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getBasis() {
			return basis;
		}
		public void setBasis(String basis) {
			this.basis = basis;
		}
		public String getCategory() {
			return category;
		}
		public void setCategory(String category) {
			this.category = category;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public Date getDate() {
			return date;
		}
		public void setDate(Date date) {
			this.date = date;
		}
		public Time getTime() {
			return time;
		}
		public void setTime(Time time) {
			this.time = time;
		}
		public Timestamp getCreated_date() {
			return created_date;
		}
		public void setCreated_date(Timestamp created_date) {
			this.created_date = created_date;
		}
	}
