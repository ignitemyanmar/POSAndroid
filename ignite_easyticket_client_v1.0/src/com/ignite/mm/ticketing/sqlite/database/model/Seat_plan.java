package com.ignite.mm.ticketing.sqlite.database.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class Seat_plan {

	@Expose
	private Integer id;
	@Expose
	private Integer from;
	@Expose
	private Integer to;
	@Expose
	private String bus_no;
	@Expose
	private Integer seat_plan_id;
	@Expose
	private String classes;
	@Expose
	private String departure_time;
	@Expose
	private String arrival_time;
	@Expose
	private Integer price;
	@Expose
	private Integer operator_id;
	@Expose
	private Integer seat_layout_id;
	@Expose
	private Integer row;
	@Expose
	private Integer column;
	@Expose
	private List<Seat_list> seat_list = new ArrayList<Seat_list>();
	
	public Seat_plan(Integer id, Integer from, Integer to, String bus_no,
			Integer seat_plan_id, String classes, String departure_time,
			String arrival_time, Integer price, Integer operator_id,
			Integer seat_layout_id, Integer row, Integer column,
			List<Seat_list> seat_list) {
		super();
		this.id = id;
		this.from = from;
		this.to = to;
		this.bus_no = bus_no;
		this.seat_plan_id = seat_plan_id;
		this.classes = classes;
		this.departure_time = departure_time;
		this.arrival_time = arrival_time;
		this.price = price;
		this.operator_id = operator_id;
		this.seat_layout_id = seat_layout_id;
		this.row = row;
		this.column = column;
		this.seat_list = seat_list;
	}

	public Integer getId() {
	return id;
	}
	
	public void setId(Integer id) {
	this.id = id;
	}
	
	public Integer getFrom() {
	return from;
	}
	
	public void setFrom(Integer from) {
	this.from = from;
	}
	
	public Integer getTo() {
	return to;
	}
	
	public void setTo(Integer to) {
	this.to = to;
	}
	
	public String getBus_no() {
	return bus_no;
	}
	
	public void setBus_no(String bus_no) {
	this.bus_no = bus_no;
	}
	
	public Integer getSeat_plan_id() {
	return seat_plan_id;
	}
	
	public void setSeat_plan_id(Integer seat_plan_id) {
	this.seat_plan_id = seat_plan_id;
	}
	
	public String getClasses() {
	return classes;
	}
	
	public void setClasses(String classes) {
	this.classes = classes;
	}
	
	public String getDeparture_time() {
	return departure_time;
	}
	
	public void setDeparture_time(String departure_time) {
	this.departure_time = departure_time;
	}
	
	public String getArrival_time() {
	return arrival_time;
	}
	
	public void setArrival_time(String arrival_time) {
	this.arrival_time = arrival_time;
	}
	
	public Integer getPrice() {
	return price;
	}
	
	public void setPrice(Integer price) {
	this.price = price;
	}
	
	public Integer getOperator_id() {
	return operator_id;
	}
	
	public void setOperator_id(Integer operator_id) {
	this.operator_id = operator_id;
	}
	
	public Integer getSeat_layout_id() {
	return seat_layout_id;
	}
	
	public void setSeat_layout_id(Integer seat_layout_id) {
	this.seat_layout_id = seat_layout_id;
	}
	
	public Integer getRow() {
	return row;
	}
	
	public void setRow(Integer row) {
	this.row = row;
	}
	
	public Integer getColumn() {
	return column;
	}
	
	public void setColumn(Integer column) {
	this.column = column;
	}
	
	public List<Seat_list> getSeat_list() {
	return seat_list;
	}
	
	public void setSeat_list(List<Seat_list> seat_list) {
	this.seat_list = seat_list;
	}

	@Override
	public String toString() {
		return "Seat_plan [id=" + id + ", from=" + from + ", to=" + to
				+ ", bus_no=" + bus_no + ", seat_plan_id=" + seat_plan_id
				+ ", classes=" + classes + ", departure_time=" + departure_time
				+ ", arrival_time=" + arrival_time + ", price=" + price
				+ ", operator_id=" + operator_id + ", seat_layout_id="
				+ seat_layout_id + ", row=" + row + ", column=" + column
				+ ", seat_list=" + seat_list + "]";
	}

	
}