package com.ignite.mm.ticketing.sqlite.database.model;

public class AllMovieObject {

	private String MovieID;
	private String Movietitle;
	private String DateId;
	private String Date;
	private String cinemaid;
	private String cinemaName;
	private String TimeId;
	private String movieTime;
	private String SeatNo;
	private String SelectedSeat;
	
	public AllMovieObject() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AllMovieObject(String movieID, String movietitle, String dateId,
			String date, String cinemaid, String cinemaName, String timeId,
			String movieTime,String seatNo, String selectedSeat) {
		super();
		this.MovieID = movieID;
		this.Movietitle = movietitle;
		this.DateId = dateId;
		this.Date = date;
		this.cinemaid = cinemaid;
		this.cinemaName = cinemaName;
		this.TimeId = timeId;
		this.movieTime = movieTime;
		this.SeatNo = seatNo;
		this.setSelectedSeat(selectedSeat);
	}

	public String getMovieID() {
		return MovieID;
	}

	public void setMovieID(String movieID) {
		MovieID = movieID;
	}

	public String getMovietitle() {
		return Movietitle;
	}

	public void setMovietitle(String movietitle) {
		Movietitle = movietitle;
	}

	public String getDateId() {
		return DateId;
	}

	public void setDateId(String dateId) {
		DateId = dateId;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public String getCinemaid() {
		return cinemaid;
	}

	public void setCinemaid(String cinemaid) {
		this.cinemaid = cinemaid;
	}

	public String getCinemaName() {
		return cinemaName;
	}

	public void setCinemaName(String cinemaName) {
		this.cinemaName = cinemaName;
	}

	public String getTimeId() {
		return TimeId;
	}

	public void setTimeId(String timeId) {
		TimeId = timeId;
	}

	public String getMovieTime() {
		return movieTime;
	}

	public void setMovieTime(String movieTime) {
		this.movieTime = movieTime;
	}
	
	public String getSelectedSeat() {
		return SelectedSeat;
	}

	public void setSelectedSeat(String selectedSeat) {
		SelectedSeat = selectedSeat;
	}

	public String getSeatNo() {
		return SeatNo;
	}

	public void setSeatNo(String seatNo) {
		SeatNo = seatNo;
	}
	

}
