package com.ignite.pos.model;

public class Ledger {

	private Integer ledgerId;
	private String itemId;
	private String itemName;
	private String date;
	private Integer oldStockQty;
	private Integer purchaseQty;
	private Integer saleQty;
	private Integer newStockQty;
	private Integer returnQty;
	
	
	public Ledger() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Ledger(Integer ledgerId, String itemId, String itemName,
			String date, Integer oldStockQty, Integer purchaseQty,
			Integer saleQty, Integer newStockQty) {
		super();
		this.ledgerId = ledgerId;
		this.itemId = itemId;
		this.itemName = itemName;
		this.date = date;
		this.oldStockQty = oldStockQty;
		this.purchaseQty = purchaseQty;
		this.saleQty = saleQty;
		this.newStockQty = newStockQty;
	}
	
	


	public Ledger(String itemId, String itemName, String date,
			Integer oldStockQty, Integer purchaseQty, Integer saleQty,
			Integer newStockQty, Integer returnQty) {
		super();
		this.itemId = itemId;
		this.itemName = itemName;
		this.date = date;
		this.oldStockQty = oldStockQty;
		this.purchaseQty = purchaseQty;
		this.saleQty = saleQty;
		this.newStockQty = newStockQty;
		this.returnQty = returnQty;
	}


	public Integer getLedgerId() {
		return ledgerId;
	}


	public void setLedgerId(Integer ledgerId) {
		this.ledgerId = ledgerId;
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


	public Integer getOldStockQty() {
		return oldStockQty;
	}


	public void setOldStockQty(Integer oldStockQty) {
		this.oldStockQty = oldStockQty;
	}


	public Integer getPurchaseQty() {
		return purchaseQty;
	}


	public void setPurchaseQty(Integer purchaseQty) {
		this.purchaseQty = purchaseQty;
	}


	public Integer getSaleQty() {
		return saleQty;
	}


	public void setSaleQty(Integer saleQty) {
		this.saleQty = saleQty;
	}


	public Integer getNewStockQty() {
		return newStockQty;
	}


	public void setNewStockQty(Integer newStockQty) {
		this.newStockQty = newStockQty;
	}

	

	public Integer getReturnQty() {
		return returnQty;
	}


	public void setReturnQty(Integer returnQty) {
		this.returnQty = returnQty;
	}


	@Override
	public String toString() {
		return "Ledger [ledgerId=" + ledgerId + ", itemId=" + itemId
				+ ", itemName=" + itemName + ", date=" + date
				+ ", oldStockQty=" + oldStockQty + ", purchaseQty="
				+ purchaseQty + ", saleQty=" + saleQty + ", newStockQty="
				+ newStockQty + ", returnQty=" + returnQty + "]";
	}
	
}
