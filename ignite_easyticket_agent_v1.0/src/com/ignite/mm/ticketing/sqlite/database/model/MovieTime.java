package com.ignite.mm.ticketing.sqlite.database.model;

public class MovieTime {

	private String MovieId;
	private String MovieTime;
	
	public MovieTime() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MovieTime(String movieId, String movieTime) {
		super();
		setMovieId(movieId);
		setMovieTime(movieTime);
	}

	public String getMovieId() {
		return MovieId;
	}

	public void setMovieId(String movieId) {
		MovieId = movieId;
	}

	public String getMovieTime() {
		return MovieTime;
	}

	public void setMovieTime(String movieTime) {
		MovieTime = movieTime;
	}

	

}
