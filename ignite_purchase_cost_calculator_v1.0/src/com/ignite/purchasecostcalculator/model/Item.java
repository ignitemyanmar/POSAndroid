package com.ignite.purchasecostcalculator.model;

public class Item {

	Integer itemId;
	String itemName;
	Double purchasePrice;
	Double transportCost;
	Double otherCost;
	String currencyType;
	
	
	public Item() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public Item(String itemName, Double purchasePrice, Double transportCost,
			Double otherCost, String currencyType) {
		super();
		this.itemName = itemName;
		this.purchasePrice = purchasePrice;
		this.transportCost = transportCost;
		this.otherCost = otherCost;
		this.currencyType = currencyType;
	}



	public Item(Integer itemId, String itemName, Double purchasePrice,
			Double transportCost, Double otherCost, String currencyType) {
		super();
		this.itemId = itemId;
		this.itemName = itemName;
		this.purchasePrice = purchasePrice;
		this.transportCost = transportCost;
		this.otherCost = otherCost;
		this.currencyType = currencyType;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Double getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(Double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public Double getTransportCost() {
		return transportCost;
	}

	public void setTransportCost(Double transportCost) {
		this.transportCost = transportCost;
	}

	public Double getOtherCost() {
		return otherCost;
	}

	public void setOtherCost(Double otherCost) {
		this.otherCost = otherCost;
	}

	public String getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	@Override
	public String toString() {
		return "Item [itemId=" + itemId + ", itemName=" + itemName
				+ ", purchasePrice=" + purchasePrice + ", transportCost="
				+ transportCost + ", otherCost=" + otherCost
				+ ", currencyType=" + currencyType + "]";
	}
	
}
