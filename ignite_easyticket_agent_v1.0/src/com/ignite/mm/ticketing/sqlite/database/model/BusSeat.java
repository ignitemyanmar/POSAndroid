package com.ignite.mm.ticketing.sqlite.database.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class BusSeat {

	@Expose
	private String trip;
	@Expose
	private String operator_id;
	@Expose
	private String operator;
	@Expose
	private List<Seat_plan> seat_plan = new ArrayList<Seat_plan>();

	public BusSeat(String trip, String operator_id, String operator,
			List<Seat_plan> seat_plan) {
		super();
		this.trip = trip;
		this.operator_id = operator_id;
		this.operator = operator;
		this.seat_plan = seat_plan;
	}

	public String getTrip() {
	return trip;
	}
	
	public void setTrip(String trip) {
	this.trip = trip;
	}
	
	public String getOperator_id() {
		return operator_id;
	}

	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}

	public String getOperator() {
	return operator;
	}
	
	public void setOperator(String operator) {
	this.operator = operator;
	}
	
	public List<Seat_plan> getSeat_plan() {
	return seat_plan;
	}
	
	public void setSeat_plan(List<Seat_plan> seat_plan) {
	this.seat_plan = seat_plan;
	}

	@Override
	public String toString() {
		return "BusSeat [trip=" + trip + ", operator_id=" + operator_id
				+ ", operator=" + operator + ", seat_plan=" + seat_plan + "]";
	}
	
	

}
