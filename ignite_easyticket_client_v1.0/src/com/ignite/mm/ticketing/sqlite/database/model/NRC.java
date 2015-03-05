package com.ignite.mm.ticketing.sqlite.database.model;

public class NRC {

	private String nrcid;
	private String nrc;
	
	public NRC() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NRC(String nrcid, String nrc) {
		super();
		this.setNrcid(nrcid);
		this.setNrc(nrc);
	}

	public String getNrcid() {
		return nrcid;
	}

	public void setNrcid(String nrcid) {
		this.nrcid = nrcid;
	}

	public String getNrc() {
		return nrc;
	}

	public void setNrc(String nrc) {
		this.nrc = nrc;
	}

}
