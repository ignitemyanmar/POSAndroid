package com.ignite.pos.model;

public class CartItem {
	private String itemId;
	private String itemName;
	private String itemPrice;
	private String categoryId;
	private String subCategoryId;
	private String Qty;
	
	public CartItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CartItem(String itemId) {
		super();
		this.itemId = itemId;
	}

	public CartItem(String itemId, String itemName, String itemPrice) {
		super();
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemPrice = itemPrice;
	}

	public CartItem(String itemId, String itemName, String itemPrice,
			String categoryId, String subCategoryId) {
		super();
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemPrice = itemPrice;
		this.setCategoryId(categoryId);
		this.setSubCategoryId(subCategoryId);
	}
	

	public CartItem(String itemId, String itemName, String itemPrice,
			String categoryId, String subCategoryId, String qty) {
		super();
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemPrice = itemPrice;
		this.categoryId = categoryId;
		this.subCategoryId = subCategoryId;
		Qty = qty;
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

	public String getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(String itemPrice) {
		this.itemPrice = itemPrice;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(String subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public String getQty() {
		return Qty;
	}

	public void setQty(String qty) {
		this.Qty = qty;
	}

	@Override
	public String toString() {
		return "CartItem [itemId=" + itemId + ", itemName=" + itemName
				+ ", itemPrice=" + itemPrice + ", categoryId=" + categoryId
				+ ", subCategoryId=" + subCategoryId + ", Qty=" + Qty + "]";
	}

}
