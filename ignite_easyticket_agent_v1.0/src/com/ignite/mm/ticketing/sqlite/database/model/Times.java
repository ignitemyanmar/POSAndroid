package com.ignite.mm.ticketing.sqlite.database.model;

public class Times {

	private String time;
	private String departure_time;
	private String time_unit;
	public Times() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Times(String time, String departure_time, String time_unit) {
		super();
		this.time = time;
		this.departure_time = departure_time;
		this.time_unit = time_unit;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getDeparture_time() {
		return departure_time;
	}
	public void setDeparture_time(String departure_time) {
		this.departure_time = departure_time;
	}
	public String getTime_unit() {
		return time_unit;
	}
	public void setTime_unit(String time_unit) {
		this.time_unit = time_unit;
	}
	@Override
	public String toString() {
		return "Times [time=" + time + ", departure_time=" + departure_time
				+ ", time_unit=" + time_unit + "]";
	}
	
	
}
