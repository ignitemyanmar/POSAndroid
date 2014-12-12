package com.ignite.pos.model;

public class Supplier {

	private Integer supId;
	private String supCoName;
	private String supName;
	private String supCity;
	private String supPh;
	private String supAddr;
	
	
	public Supplier() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Supplier(Integer supId, String supCoName, String supName,
			String supCity, String supPh, String supAddr) {
		super();
		this.supId = supId;
		this.supCoName = supCoName;
		this.supName = supName;
		this.supCity = supCity;
		this.supPh = supPh;
		this.supAddr = supAddr;
	}

	public Supplier(String supCoName, String supName, String supCity,
			String supPh, String supAddr) {
		super();
		this.supCoName = supCoName;
		this.supName = supName;
		this.supCity = supCity;
		this.supPh = supPh;
		this.supAddr = supAddr;
	}

	public Integer getSupId() {
		return supId;
	}

	public void setSupId(Integer supId) {
		this.supId = supId;
	}

	public String getSupCoName() {
		return supCoName;
	}

	public void setSupCoName(String supCoName) {
		this.supCoName = supCoName;
	}

	public String getSupName() {
		return supName;
	}

	public void setSupName(String supName) {
		this.supName = supName;
	}

	public String getSupCity() {
		return supCity;
	}

	public void setSupCity(String supCity) {
		this.supCity = supCity;
	}

	public String getSupPh() {
		return supPh;
	}

	public void setSupPh(String supPh) {
		this.supPh = supPh;
	}

	public String getSupAddr() {
		return supAddr;
	}

	public void setSupAddr(String supAddr) {
		this.supAddr = supAddr;
	}

	@Override
	public String toString() {
		return "Supplier [supId=" + supId + ", supCoName=" + supCoName
				+ ", supName=" + supName + ", supCity=" + supCity + ", supPh="
				+ supPh + ", supAddr=" + supAddr + "]";
	}
}
