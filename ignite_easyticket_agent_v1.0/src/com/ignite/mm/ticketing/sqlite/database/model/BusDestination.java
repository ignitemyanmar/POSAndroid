package com.ignite.mm.ticketing.sqlite.database.model;

public class BusDestination {

	private String DesID;
	private String DesName;

	public BusDestination() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BusDestination(String iD, String destination) {
		super();
		DesID = iD;
		DesName = destination;
	}

	public String getDesID() {
		return DesID;
	}

	public void setDesID(String iD) {
		DesID = iD;
	}

	public String getDesName() {
		return DesName;
	}

	public void setDesName(String destination) {
		DesName = destination;
	}

}
