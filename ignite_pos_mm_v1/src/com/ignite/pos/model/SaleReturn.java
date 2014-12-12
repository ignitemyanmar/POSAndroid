package com.ignite.pos.model;

public class SaleReturn {

	private String vid;
	private String itemid;
	private String itemName;
	private Integer oldQty;
	private Integer returnQty;
	private Integer salePrice;
	private Integer itemTotal;
	private String returnDate;
	private String updatePerson;
	private String saleDate;
	private String saleVouId;
	
	public SaleReturn() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	public SaleReturn(String vid) {
		super();
		this.vid = vid;
	}



	public SaleReturn(String vid, String itemid, Integer oldQty, Integer returnQty,
			Integer salePrice, Integer itemTotal, String returnDate,
			String updatePerson, String itemName, String saleDate, String saleVouId) {
		super();
		this.vid = vid;
		this.itemid = itemid;
		this.oldQty = oldQty;
		this.returnQty = returnQty;
		this.salePrice = salePrice;
		this.itemTotal = itemTotal;
		this.returnDate = returnDate;
		this.updatePerson = updatePerson;
		this.itemName = itemName;
		this.saleDate = saleDate;
		this.saleVouId = saleVouId;
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

	public Integer getReturnQty() {
		return returnQty;
	}

	public void setReturnQty(Integer returnQty) {
		this.returnQty = returnQty;
	}

	public Integer getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Integer salePrice) {
		this.salePrice = salePrice;
	}

	public Integer getItemTotal() {
		return itemTotal;
	}

	public void setItemTotal(Integer itemTotal) {
		this.itemTotal = itemTotal;
	}

	public String getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}

	public String getUpdatePerson() {
		return updatePerson;
	}

	public void setUpdatePerson(String updatePerson) {
		this.updatePerson = updatePerson;
	}

	
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	
	public String getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(String saleDate) {
		this.saleDate = saleDate;
	}

	
	public String getSaleVouId() {
		return saleVouId;
	}

	public void setSaleVouId(String saleVouId) {
		this.saleVouId = saleVouId;
	}

	@Override
	public String toString() {
		return "SaleReturn [vid=" + vid + ", itemid=" + itemid + ", itemName="
				+ itemName + ", oldQty=" + oldQty + ", returnQty=" + returnQty
				+ ", salePrice=" + salePrice + ", itemTotal=" + itemTotal
				+ ", returnDate=" + returnDate + ", updatePerson="
				+ updatePerson + ", saleDate=" + saleDate + ", saleVouId="
				+ saleVouId + "]";
	}



	
	
}
