package com.ignite.pos.model;

public class PurchaseVoucher {

	private String vid;
	private String supplierName;
	private String itemid;
	private String itemname;
	private String qty;
	private String oldQty;
	private String price;
	private String categoryid;
	private String subcategoryid;
	private String itemtotal;
	private String vdate;
	private String grandtotal;
	private String purchasePrice;
	private String salePrice;
	private String marginalPrice;
	private String qtyTotal;
	private String oldStockQty;
	private Integer status;
	
	public PurchaseVoucher() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public PurchaseVoucher(String vid, String supplierName, String itemid,
			String itemname, String qty, String oldQty, String price,
			String categoryid, String subcategoryid, String itemtotal,
			String vdate, String grandtotal, String purchasePrice,
			String salePrice, String marginalPrice, String qtyTotal,
			String oldStockQty, Integer status) {
		super();
		this.vid = vid;
		this.supplierName = supplierName;
		this.itemid = itemid;
		this.itemname = itemname;
		this.qty = qty;
		this.oldQty = oldQty;
		this.price = price;
		this.categoryid = categoryid;
		this.subcategoryid = subcategoryid;
		this.itemtotal = itemtotal;
		this.vdate = vdate;
		this.grandtotal = grandtotal;
		this.purchasePrice = purchasePrice;
		this.salePrice = salePrice;
		this.marginalPrice = marginalPrice;
		this.qtyTotal = qtyTotal;
		this.oldStockQty = oldStockQty;
		this.status = status;
	}



	public PurchaseVoucher(String vid, String supplierName, String itemid,
			String itemname, String qty, String price, String categoryid,
			String subcategoryid, String itemtotal, String vdate,
			String grandtotal, String purchasePrice, String salePrice,
			String marginalPrice, String oldQty) {
		super();
		this.vid = vid;
		this.supplierName = supplierName;
		this.itemid = itemid;
		this.itemname = itemname;
		this.qty = qty;
		this.price = price;
		this.categoryid = categoryid;
		this.subcategoryid = subcategoryid;
		this.itemtotal = itemtotal;
		this.vdate = vdate;
		this.grandtotal = grandtotal;
		this.purchasePrice = purchasePrice;
		this.salePrice = salePrice;
		this.marginalPrice = marginalPrice;
		this.oldQty = oldQty;
	}



	public PurchaseVoucher(String vid, String supplierName, String itemid,
			String itemname, String qty, String itemtotal,
			String vdate, String grandtotal) {
		super();
		this.vid = vid;
		this.supplierName = supplierName;
		this.itemid = itemid;
		this.itemname = itemname;
		this.qty = qty;
		this.itemtotal = itemtotal;
		this.vdate = vdate;
		this.grandtotal = grandtotal;
	}
	
	

	public PurchaseVoucher(String vid, String itemid, String itemname,
			String qty, String itemtotal, String grandtotal, String purchasePrice) {
		super();
		this.vid = vid;
		this.itemid = itemid;
		this.itemname = itemname;
		this.qty = qty;
		this.itemtotal = itemtotal;
		this.grandtotal = grandtotal;
		this.purchasePrice = purchasePrice;
	}

	
	
	public PurchaseVoucher(String vid, String supplierName, String itemid,
			String qty, String itemtotal, String grandtotal
			, String purchasePrice, String marginalPrice, String salePrice) {
		super();
		this.vid = vid;
		this.supplierName = supplierName;
		this.itemid = itemid;
		this.qty = qty;
		this.itemtotal = itemtotal;
		this.grandtotal = grandtotal;
		this.purchasePrice = purchasePrice;
		this.marginalPrice = marginalPrice;
		this.salePrice = salePrice;
	}

	public String getVid() {
		return vid;
	}

	public void setVid(String vid) {
		this.vid = vid;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getItemid() {
		return itemid;
	}

	public void setItemid(String itemid) {
		this.itemid = itemid;
	}

	public String getItemname() {
		return itemname;
	}

	public void setItemname(String itemname) {
		this.itemname = itemname;
	}

	public String getQty() {
		return qty;
	}

	public void setQty(String qty) {
		this.qty = qty;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(String categoryid) {
		this.categoryid = categoryid;
	}

	public String getSubcategoryid() {
		return subcategoryid;
	}

	public void setSubcategoryid(String subcategoryid) {
		this.subcategoryid = subcategoryid;
	}

	public String getItemtotal() {
		return itemtotal;
	}

	public void setItemtotal(String itemtotal) {
		this.itemtotal = itemtotal;
	}

	public String getVdate() {
		return vdate;
	}

	public void setVdate(String vdate) {
		this.vdate = vdate;
	}

	public String getGrandtotal() {
		return grandtotal;
	}

	public void setGrandtotal(String grandtotal) {
		this.grandtotal = grandtotal;
	}
	
	

	public String getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(String purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public String getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}

	public String getMarginalPrice() {
		return marginalPrice;
	}

	public void setMarginalPrice(String marginalPrice) {
		this.marginalPrice = marginalPrice;
	}

	
	public String getQtyTotal() {
		return qtyTotal;
	}

	public void setQtyTotal(String qtyTotal) {
		this.qtyTotal = qtyTotal;
	}

	
	public String getOldStockQty() {
		return oldStockQty;
	}

	public void setOldStockQty(String oldStockQty) {
		this.oldStockQty = oldStockQty;
	}

	
	public String getOldQty() {
		return oldQty;
	}

	public void setOldQty(String oldQty) {
		this.oldQty = oldQty;
	}

	
	public Integer getStatus() {
		return status;
	}



	public void setStatus(Integer status) {
		this.status = status;
	}



	@Override
	public String toString() {
		return "PurchaseVoucher [vid=" + vid + ", supplierName=" + supplierName
				+ ", itemid=" + itemid + ", itemname=" + itemname + ", qty="
				+ qty + ", oldQty=" + oldQty + ", price=" + price
				+ ", categoryid=" + categoryid + ", subcategoryid="
				+ subcategoryid + ", itemtotal=" + itemtotal + ", vdate="
				+ vdate + ", grandtotal=" + grandtotal + ", purchasePrice="
				+ purchasePrice + ", salePrice=" + salePrice
				+ ", marginalPrice=" + marginalPrice + ", qtyTotal=" + qtyTotal
				+ ", oldStockQty=" + oldStockQty + ", status=" + status + "]";
	}
}
