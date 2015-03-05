package com.ignite.mm.ticketing.sqlite.database.model;

import com.google.gson.annotations.Expose;

public class PurchaseSeat {
	
	@Expose
	private Integer busoccurance_id;
	@Expose
	private String seat_no;
	
	public Integer getBusoccurance_id() {
	return busoccurance_id;
	}
	
	public void setBusoccurance_id(Integer busoccurance_id) {
	this.busoccurance_id = busoccurance_id;
	}
	
	public String getSeat_no() {
	return seat_no;
	}
	
	public void setSeat_no(String seat_no) {
	this.seat_no = seat_no;
	}

}