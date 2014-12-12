package com.ignite.pos.model;

public class SaleHistory {

	private String vid;
	private String itemid;
	private Integer oldQty;
	private Integer newQty;
	private String updateDate;
	private String updatePerson;
	private String status;
	
	
	
	public SaleHistory() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public SaleHistory(String vid, String itemid, Integer oldQty,
			Integer newQty, String updateDate, String updatePerson,
			String status) {
		super();
		this.vid = vid;
		this.itemid = itemid;
		this.oldQty = oldQty;
		this.newQty = newQty;
		this.updateDate = updateDate;
		this.updatePerson = updatePerson;
		this.status = status;
	}



	public String getVid() {
		return vid;
	}



	public void setVid(String vid) {
		this.vid = vid;
	}



	public String getItemid() {
		return itemid;
	}



	public void setItemid(String itemid) {
		this.itemid = itemid;
	}



	public Integer getOldQty() {
		return oldQty;
	}



	public void setOldQty(Integer oldQty) {
		this.oldQty = oldQty;
	}



	public Integer getNewQty() {
		return newQty;
	}



	public void setNewQty(Integer newQty) {
		this.newQty = newQty;
	}



	public String getUpdateDate() {
		return updateDate;
	}



	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}



	public String getUpdatePerson() {
		return updatePerson;
	}



	public void setUpdatePerson(String updatePerson) {
		this.updatePerson = updatePerson;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	@Override
	public String toString() {
		return "SaleHistory [vid=" + vid + ", itemid=" + itemid + ", oldQty="
				+ oldQty + ", newQty=" + newQty + ", updateDate=" + updateDate
				+ ", updatePerson=" + updatePerson + ", status=" + status + "]";
	}


	
	
}
