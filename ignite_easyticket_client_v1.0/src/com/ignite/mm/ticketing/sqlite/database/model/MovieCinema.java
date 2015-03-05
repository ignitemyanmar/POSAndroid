package com.ignite.mm.ticketing.sqlite.database.model;

public class MovieCinema {

	private String id;
	private String cinemaName;
	
	public MovieCinema() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MovieCinema(String id,String cinemaName) {
		super();
		this.setId(id);
		this.setCinemaName(cinemaName);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCinemaName() {
		return cinemaName;
	}

	public void setCinemaName(String cinemaName) {
		this.cinemaName = cinemaName;
	}

}
