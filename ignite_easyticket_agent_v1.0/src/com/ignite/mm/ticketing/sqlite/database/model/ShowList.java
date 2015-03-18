package com.ignite.mm.ticketing.sqlite.database.model;

public class ShowList {

	private String ShowID;
	private String ShowName;
	
	public ShowList() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ShowList(String showiD, String showName) {
		super();
		setShowID(showiD);
		setShowName(showName);
	}

	public String getShowID() {
		return ShowID;
	}

	public void setShowID(String showID) {
		ShowID = showID;
	}

	public String getShowName() {
		return ShowName;
	}

	public void setShowName(String showName) {
		ShowName = showName;
	}

}
