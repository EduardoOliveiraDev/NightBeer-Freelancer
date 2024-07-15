package com.nightbeer.model;

import java.time.LocalDateTime;

public class historic {

	private int id;
	private LocalDateTime date;
	private String hashmapJSON;
	private double total;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	public String getHashmapJSON() {
		return hashmapJSON;
	}
	public void setHashmapJSON(String hashmapJSON) {
		this.hashmapJSON = hashmapJSON;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	} 
	
}
