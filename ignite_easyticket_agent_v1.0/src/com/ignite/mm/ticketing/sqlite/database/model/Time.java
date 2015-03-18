package com.ignite.mm.ticketing.sqlite.database.model;

	public class Time {
	
	private String tripid;
	private String class_id;
	private String bus_class;
	private Integer total_seat;
	private Integer total_sold_seat;
	private String time;
	private String no_trip;
	
	
	public Time() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Time(String tripid, String bus_class, Integer total_seat,
			Integer total_sold_seat, String time) {
		super();
		this.tripid = tripid;
		this.bus_class = bus_class;
		this.total_seat = total_seat;
		this.total_sold_seat = total_sold_seat;
		this.time = time;
	}

	public String getTripid() {
	return tripid;
	}
	
	public void setTripid(String tripid) {
	this.tripid = tripid;
	}
	
	public String getTime() {
	return time;
	}
	
	public void setTime(String time) {
	this.time = time;
	}

	public String getClass_id() {
		return class_id;
	}

	public void setClass_id(String class_id) {
		this.class_id = class_id;
	}

	public String getBus_class() {
		return bus_class;
	}

	public void setBus_class(String bus_class) {
		this.bus_class = bus_class;
	}

	public Integer getTotal_seat() {
		return total_seat;
	}

	public void setTotal_seat(Integer total_seat) {
		this.total_seat = total_seat;
	}

	public Integer getTotal_sold_seat() {
		return total_sold_seat;
	}

	public void setTotal_sold_seat(Integer total_sold_seat) {
		this.total_sold_seat = total_sold_seat;
	}

	public String getNo_trip() {
		return no_trip;
	}

	public void setNo_trip(String no_trip) {
		this.no_trip = no_trip;
	}

	@Override
	public String toString() {
		return "Time [tripid=" + tripid + ", class_id=" + class_id
				+ ", bus_class=" + bus_class + ", total_seat=" + total_seat
				+ ", total_sold_seat=" + total_sold_seat + ", time=" + time
				+ ", no_trip=" + no_trip + "]";
	}
	
}