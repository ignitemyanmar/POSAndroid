package com.ignite.pos.model;

public class Brand {

	private int brandID;
	private String brandName;
	public Brand() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Brand(String brandName) {
		super();
		this.brandName = brandName;
	}
	
	public Brand(int brandID, String brandName) {
		super();
		this.brandID = brandID;
		this.brandName = brandName;
	}

	public int getBrandID() {
		return brandID;
	}

	public void setBrandID(int brandID) {
		this.brandID = brandID;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	@Override
	public String toString() {
		return "Brand [brandID=" + brandID + ", brandName=" + brandName + "]";
	}
	
	
	
}
