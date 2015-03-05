package com.ignite.mm.ticketing.sqlite.database.model;

public class RemarkType {
	private Integer id;
	private String remark;
	
	public RemarkType(Integer id, String remark) {
		super();
		this.id = id;
		this.remark = remark;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return remark;
	}
	
}
