package com.ignite.pos.model;

public class ItemList {
	private String itemId;
	private String itemName;
	private String purchasePrice;
	private String salePrice;
	private String marginalPrice;
	private String qty;
	private String categoryId;
	private String categoryName;
	private String subCategoryId;
	private String brandId;
	private Integer status;
	private Integer notifyQty;
	private Integer notifyStatus;
	private Integer registerNotify;
	
	
	public ItemList() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ItemList(String itemName) {
		super();
		this.itemName = itemName;
	}

	public ItemList(String itemId, String itemName, String purchasePrice,
			String salePrice, String marginalPrice, String qty,
			String categoryId, String subCategoryId, String brandId,
			Integer status, Integer notifyQty, Integer notifyStatus,
			Integer registerNotify) {
		super();
		this.itemId = itemId;
		this.itemName = itemName;
		this.purchasePrice = purchasePrice;
		this.salePrice = salePrice;
		this.marginalPrice = marginalPrice;
		this.qty = qty;
		this.categoryId = categoryId;
		this.subCategoryId = subCategoryId;
		this.brandId = brandId;
		this.status = status;
		this.notifyQty = notifyQty;
		this.notifyStatus = notifyStatus;
		this.registerNotify = registerNotify;
	}

	public ItemList(String itemId, String qty) {
		super();
		this.itemId = itemId;
		this.qty = qty;
	}

	public ItemList(String itemId, String itemName, String purchasePrice,
			String salePrice, String marginalPrice, String qty, Integer notifyStatus) {
		super();
		this.itemId = itemId;
		this.itemName = itemName;
		this.purchasePrice = purchasePrice;
		this.salePrice = salePrice;
		this.marginalPrice = marginalPrice;
		this.qty = qty;
		this.notifyStatus = notifyStatus;
	}

	public ItemList(String itemId, String itemName, String salePrice) {
		super();
		this.itemId = itemId;
		this.itemName = itemName;
		this.salePrice = salePrice;
	}
	
	public ItemList(String itemId, String itemName, String purchasePrice,
			String salePrice, String marginalPrice, String qty,
			String categoryId, String subCategoryId, String brandId, Integer status) {
		super();
		this.itemId = itemId;
		this.itemName = itemName;
		this.purchasePrice = purchasePrice;
		this.salePrice = salePrice;
		this.marginalPrice = marginalPrice;
		this.qty = qty;
		this.categoryId = categoryId;
		this.subCategoryId = subCategoryId;
		this.brandId = brandId;
		this.status = status;
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

	public String getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}


	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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
	
	

	public String getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(String purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	
	public String getMarginalPrice() {
		return marginalPrice;
	}

	public void setMarginalPrice(String marginalPrice) {
		this.marginalPrice = marginalPrice;
	}
	
	

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	

	public Integer getNotifyQty() {
		return notifyQty;
	}

	public void setNotifyQty(Integer notifyQty) {
		this.notifyQty = notifyQty;
	}

	public Integer getNotifyStatus() {
		return notifyStatus;
	}

	public void setNotifyStatus(Integer notifyStatus) {
		this.notifyStatus = notifyStatus;
	}

	
	public Integer getRegisterNotify() {
		return registerNotify;
	}

	public void setRegisterNotify(Integer registerNotify) {
		this.registerNotify = registerNotify;
	}

	@Override
	public String toString() {
		return "ItemList [itemId=" + itemId + ", itemName=" + itemName
				+ ", purchasePrice=" + purchasePrice + ", salePrice="
				+ salePrice + ", marginalPrice=" + marginalPrice + ", qty="
				+ qty + ", categoryId=" + categoryId + ", subCategoryId="
				+ subCategoryId + ", brandId=" + brandId + ", status=" + status
				+ ", notifyQty=" + notifyQty + ", notifyStatus=" + notifyStatus
				+ ", registerNotify=" + registerNotify + "]";
	}

	
}
