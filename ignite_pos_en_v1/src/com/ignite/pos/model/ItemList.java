package com.ignite.pos.model;

public class ItemList {
	private String itemId;
	private String itemName;
	private String itemPrice;
	private String qty;
	private String categoryId;
	private String subCategoryId;
	private String brandId;
	
	public ItemList() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ItemList(String itemId) {
		super();
		this.itemId = itemId;
	}

	public ItemList(String itemId, String itemName, String itemPrice) {
		super();
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemPrice = itemPrice;
	}


	public ItemList(String itemId, String itemName, String itemPrice,
			String qty, String categoryId, String subCategoryId, String brandId) {
		super();
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemPrice = itemPrice;
		this.qty = qty;
		this.categoryId = categoryId;
		this.subCategoryId = subCategoryId;
		this.brandId = brandId;
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
	

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
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

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	@Override
	public String toString() {
		return "ItemList [itemId=" + itemId + ", itemName=" + itemName
				+ ", itemPrice=" + itemPrice + ", qty=" + qty + ", categoryId="
				+ categoryId + ", subCategoryId=" + subCategoryId
				+ ", brandId=" + brandId + "]";
	}

}
