package com.ignite.mm.ticketing.sqlite.database.model;

public class MovieList {

	private String ID;
	private String Movietitle;

	public MovieList() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MovieList(String iD, String movietitle) {
		super();
		ID = iD;
		Movietitle = movietitle;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getMovietitle() {
		return Movietitle;
	}

	public void setMovietitle(String movietitle) {
		Movietitle = movietitle;
	}

}
