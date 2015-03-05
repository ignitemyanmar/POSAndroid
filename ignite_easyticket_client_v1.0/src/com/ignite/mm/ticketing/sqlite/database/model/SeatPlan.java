package com.ignite.mm.ticketing.sqlite.database.model;

import java.util.ArrayList;
import java.util.List;
public class SeatPlan {

private Integer id;
private Integer from;
private Integer to;
private String bus_no;
private Integer seat_plan_id;
private String classes;
private String departure_time;
private String arrival_time;
private Integer price;
private Integer operator_id;
private Integer seat_layout_id;
private Integer row;
private Integer column;
private List<SeatList> seat_list = new ArrayList<SeatList>();
	
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
	
	public List<SeatList> getSeat_list() {
	return seat_list;
	}
	
	public void setSeat_list(List<SeatList> seat_list) {
	this.seat_list = seat_list;
	}

}