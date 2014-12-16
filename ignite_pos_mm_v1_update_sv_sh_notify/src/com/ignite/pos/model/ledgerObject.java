package com.ignite.pos.model;

public class ledgerObject {

	private String itemId;
	private String itemName;
	private String date;
	private String oldStkQty;
	private String purchaseQty;
	private String saleQty;
	private String newStkQty;
	
	public ledgerObject(String itemId, String itemName, String date,
			String oldStkQty, String purchaseQty, String saleQty,
			String newStkQty) {
		super();
		this.itemId = itemId;
		this.itemName = itemName;
		this.date = date;
		this.oldStkQty = oldStkQty;
		this.purchaseQty = purchaseQty;
		this.saleQty = saleQty;
		this.newStkQty = newStkQty;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getOldStkQty() {
		return oldStkQty;
	}

	public void setOldStkQty(String oldStkQty) {
		this.oldStkQty = oldStkQty;
	}

	public String getPurchaseQty() {
		return purchaseQty;
	}

	public void setPurchaseQty(String purchaseQty) {
		this.purchaseQty = purchaseQty;
	}

	public String getSaleQty() {
		return saleQty;
	}

	public void setSaleQty(String saleQty) {
		this.saleQty = saleQty;
	}

	public String getNewStkQty() {
		return newStkQty;
	}

	public void setNewStkQty(String newStkQty) {
		this.newStkQty = newStkQty;
	}

	@Override
	public String toString() {
		return "ledgerObject [itemId=" + itemId + ", itemName=" + itemName
				+ ", date=" + date + ", oldStkQty=" + oldStkQty
				+ ", purchaseQty=" + purchaseQty + ", saleQty=" + saleQty
				+ ", newStkQty=" + newStkQty + "]";
	}
	
	
}
