package com.ignite.mm.ticketing.sqlite.database.model;

public class BusClass {
	private String ID;
	private String BusClass;

	public BusClass() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public BusClass(String id ,String busClass) {
		super();
		ID = id ;
		BusClass = busClass;
	}

	public String getBusClass() {
		return BusClass;
	}
	
	public void setBusClass(String busClass) {
		BusClass = busClass;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

}
