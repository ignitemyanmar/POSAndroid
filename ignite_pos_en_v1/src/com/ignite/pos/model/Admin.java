package com.ignite.pos.model;

public class Admin {

	//private String currUsername;
	private String ID;
	private String Oldname;
	private String Oldpassword;
	private String Adminname;
	private String Adminpassword;
	
	public Admin() {
		// TODO Auto-generated constructor stub
	}

	public Admin(String adminname, String adminpassword) {
		super();
		Adminname = adminname;
		Adminpassword = adminpassword;
	}

	public Admin(String iD, String adminname, String adminpassword) {
		super();
		ID = iD;
		Adminname = adminname;
		Adminpassword = adminpassword;
	}

	public Admin(String oldname, String oldpassword,
			String adminname, String adminpassword) {
		super();
		Oldname = oldname;
		Oldpassword = oldpassword;
		Adminname = adminname;
		Adminpassword = adminpassword;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getAdminname() {
		return Adminname;
	}

	public void setAdminname(String adminname) {
		Adminname = adminname;
	}

	public String getAdminpassword() {
		return Adminpassword;
	}

	public void setAdminpassword(String adminpassword) {
		Adminpassword = adminpassword;
	}

	public String getOldname() {
		return Oldname;
	}

	public void setOldname(String oldname) {
		Oldname = oldname;
	}

	public String getOldpassword() {
		return Oldpassword;
	}

	public void setOldpassword(String oldpassword) {
		Oldpassword = oldpassword;
	}
	
	@Override
	public String toString() {
		return "Admin [ID=" + ID + ", Oldname=" + Oldname + ", Oldpassword="
				+ Oldpassword + ", Adminname=" + Adminname + ", Adminpassword="
				+ Adminpassword + "]";
	}
}
