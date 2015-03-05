package com.ignite.mm.ticketing.sqlite.database.model;

public class MovieDate {

	private String Id;
	private String Date;

	public MovieDate() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MovieDate(String id,String date) {
		super();
		setId(id);
		setDate(date);
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

}
