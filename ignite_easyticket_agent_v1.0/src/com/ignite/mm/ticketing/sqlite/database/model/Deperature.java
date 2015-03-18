package com.ignite.mm.ticketing.sqlite.database.model;

public class Deperature {

	private String DepID;
	private String DepTime;
	
	public Deperature() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Deperature(String iD, String deperature) {
		super();
		setDepID(iD);
		setDepTime(deperature);
	}

	public String getDepID() {
		return DepID;
	}

	public void setDepID(String iD) {
		DepID = iD;
	}

	public String getDepTime() {
		return DepTime;
	}

	public void setDepTime(String deperature) {
		DepTime = deperature;
	}

	@Override
	public String toString() {
		return "Deperature [DepID=" + DepID + ", DepTime=" + DepTime + "]";
	}

}
