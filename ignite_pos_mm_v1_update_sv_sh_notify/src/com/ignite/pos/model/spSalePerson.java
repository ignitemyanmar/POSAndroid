package com.ignite.pos.model;

public class spSalePerson {
	private String spusername;
	private String sppassword;
	private String Oldname;
	private String Oldpassword;
		
	public spSalePerson() {
		super();
		// TODO Auto-generated constructor stub
	}
	


	public spSalePerson(String oldname, String oldpassword, String spusername,
			String sppassword) {
		super();
		Oldname = oldname;
		Oldpassword = oldpassword;
		this.spusername = spusername;
		this.sppassword = sppassword;
	}



	public spSalePerson(String spusername, String sppassword) {
		super();
		this.setSpusername(spusername);
		this.setSppassword(sppassword);
	}
	public String getSpusername() {
		return spusername;
	}
	public void setSpusername(String spusername) {
		this.spusername = spusername;
	}
	public String getSppassword() {
		return sppassword;
	}
	public void setSppassword(String sppassword) {
		this.sppassword = sppassword;
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
		return "spSalePerson [spusername=" + spusername + ", sppassword="
				+ sppassword + ", Oldname=" + Oldname + ", Oldpassword="
				+ Oldpassword + "]";
	}


	
}
