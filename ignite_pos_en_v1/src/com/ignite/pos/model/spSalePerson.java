package com.ignite.pos.model;

public class spSalePerson {
	private String spusername;
	private String sppassword;
		
	public spSalePerson() {
		super();
		// TODO Auto-generated constructor stub
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
	@Override
	public String toString() {
		return "spSalePerson [spusername=" + spusername + ", sppassword="
				+ sppassword + "]";
	}
	
}
