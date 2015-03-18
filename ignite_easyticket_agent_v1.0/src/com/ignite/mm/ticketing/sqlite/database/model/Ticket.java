package com.ignite.mm.ticketing.sqlite.database.model;

import com.google.gson.annotations.Expose;

public class Ticket {
	
	@Expose
	private String seat_id;
	@Expose
	private String seat_no;
	@Expose
	private Boolean can_buy;
	@Expose
	private Integer bar_code;
	
	public String getSeat_id() {
	return seat_id;
	}
	
	public void setSeat_id(String seat_id) {
	this.seat_id = seat_id;
	}
	
	public String getSeat_no() {
	return seat_no;
	}
	
	public void setSeat_no(String seat_no) {
	this.seat_no = seat_no;
	}
	
	public Boolean getCan_buy() {
	return can_buy;
	}
	
	public void setCan_buy(Boolean can_buy) {
	this.can_buy = can_buy;
	}
	
	public Integer getBar_code() {
	return bar_code;
	}
	
	public void setBar_code(Integer bar_code) {
	this.bar_code = bar_code;
	}

}