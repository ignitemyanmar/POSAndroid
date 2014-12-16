package com.ignite.pos.model;

public class SaleVouncher {

	private String vid;
	private String cusname;
	private String itemid;
	private String itemname;
	private String qty;
	private String oldQty;
	private String price;
	private String categoryid;
	private String subcategoryid;
	private String itemtotal;
	private String vdate;
	private String total;
	private String salePerson;
	private String discount;
	private String qtyTotal;
	private String totalQtyMaxi;
	private String updatePerson;
	private String updateDate;
	private Integer marginalPrice;
	private Integer returnableQty;
	private Integer updated;
	
	public SaleVouncher() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public SaleVouncher(String vid) {
		super();
		this.vid = vid;
	}



	public SaleVouncher(String vid, String itemid, Integer returnableQty) {
		super();
		this.vid = vid;
		this.itemid = itemid;
		this.returnableQty = returnableQty;
	}



	public SaleVouncher(String vid, String cusname, String itemid,
			String itemname, String qty, String oldQty, String price,
			String categoryid, String subcategoryid, String itemtotal,
			String vdate, String total, String salePerson, String discount,
			String qtyTotal, String totalQtyMaxi, String updatePerson,
			String updateDate, Integer marginalPrice, Integer returnableQty) {
		super();
		this.vid = vid;
		this.cusname = cusname;
		this.itemid = itemid;
		this.itemname = itemname;
		this.qty = qty;
		this.oldQty = oldQty;
		this.price = price;
		this.categoryid = categoryid;
		this.subcategoryid = subcategoryid;
		this.itemtotal = itemtotal;
		this.vdate = vdate;
		this.total = total;
		this.salePerson = salePerson;
		this.discount = discount;
		this.qtyTotal = qtyTotal;
		this.totalQtyMaxi = totalQtyMaxi;
		this.updatePerson = updatePerson;
		this.updateDate = updateDate;
		this.marginalPrice = marginalPrice;
		this.returnableQty = returnableQty;
	}



	public SaleVouncher(String vid, String itemid, String itemname, String qty,
			String itemtotal, String total, String salePerson, String discount, Integer updated) {
		super();
		this.vid = vid;
		this.itemid = itemid;
		this.itemname = itemname;
		this.qty = qty;
		this.itemtotal = itemtotal;
		this.total = total;
		this.salePerson = salePerson;
		this.discount = discount;
		this.updated = updated;
	}


	public SaleVouncher(String vid, String cusname, String itemid,
			String itemname, String qty,String price,String categoryid, String subcategoryid, String itemtotal, String vdate,
			String total, String salePerson, String discount) {
		super();
		this.vid = vid;
		this.cusname = cusname;
		this.itemid = itemid;
		this.itemname = itemname;
		this.qty = qty;
		this.price = price;
		this.categoryid = categoryid;
		this.subcategoryid = subcategoryid;
		this.itemtotal = itemtotal;
		this.vdate = vdate;
		this.total = total;
		this.salePerson = salePerson;
		this.discount = discount;
	}
	
	

	public SaleVouncher(String itemid, String itemName, String VouID, String qty, String price,
			String itemtotal, String total, Integer updated) {
		super();
		this.itemid = itemid;
		this.itemname = itemName;
		this.vid = VouID;
		this.qty = qty;
		this.price = price;
		this.itemtotal = itemtotal;
		this.total = total;
		this.updated = updated;
	}

	public SaleVouncher(String vid,String cusname, String itemid, String itemname,
			String qty, String price,  String itemtotal, String vdate, String total,
			String salePerson) {
		super();
		this.vid = vid;
		this.cusname = cusname;
		this.itemid = itemid;
		this.itemname = itemname;
		this.qty = qty;
		this.price = price;
		/*this.categoryid = categoryid;
		this.subcategoryid = subcategoryid;*/
		this.itemtotal = itemtotal;
		this.vdate = vdate;
		this.total = total;
		this.salePerson = salePerson;
	}

	public SaleVouncher(String itemid, String itemname, String qty,
			String price , String categoryid, String subcategoryid) {
		super();
		this.itemid = itemid;
		this.itemname = itemname;
		this.qty = qty;
		this.price = price;
		this.categoryid = categoryid;
		this.subcategoryid = subcategoryid;
	}

	public String getVid() {
		return vid;
	}

	public void setVid(String vid) {
		this.vid = vid;
	}

	public String getCusname() {
		return cusname;
	}

	public void setCusname(String cusname) {
		this.cusname = cusname;
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
	
	public String getOldQty() {
		return oldQty;
	}


	public void setOldQty(String oldQty) {
		this.oldQty = oldQty;
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

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getSalePerson() {
		return salePerson;
	}

	public void setSalePerson(String salePerson) {
		this.salePerson = salePerson;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	
	public String getQtyTotal() {
		return qtyTotal;
	}

	public void setQtyTotal(String qtyTotal) {
		this.qtyTotal = qtyTotal;
	}
	
	

	public String getTotalQtyMaxi() {
		return totalQtyMaxi;
	}

	public void setTotalQtyMaxi(String totalQtyMaxi) {
		this.totalQtyMaxi = totalQtyMaxi;
	}
	

	public String getUpdatePerson() {
		return updatePerson;
	}



	public void setUpdatePerson(String updatePerson) {
		this.updatePerson = updatePerson;
	}



	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	
	

	public Integer getMarginalPrice() {
		return marginalPrice;
	}



	public void setMarginalPrice(Integer marginalPrice) {
		this.marginalPrice = marginalPrice;
	}



	public Integer getReturnableQty() {
		return returnableQty;
	}



	public void setReturnableQty(Integer returnableQty) {
		this.returnableQty = returnableQty;
	}



	public Integer getUpdated() {
		return updated;
	}



	public void setUpdated(Integer updated) {
		this.updated = updated;
	}



	@Override
	public String toString() {
		return "SaleVouncher [vid=" + vid + ", cusname=" + cusname
				+ ", itemid=" + itemid + ", itemname=" + itemname + ", qty="
				+ qty + ", oldQty=" + oldQty + ", price=" + price
				+ ", categoryid=" + categoryid + ", subcategoryid="
				+ subcategoryid + ", itemtotal=" + itemtotal + ", vdate="
				+ vdate + ", total=" + total + ", salePerson=" + salePerson
				+ ", discount=" + discount + ", qtyTotal=" + qtyTotal
				+ ", totalQtyMaxi=" + totalQtyMaxi + ", updatePerson="
				+ updatePerson + ", updateDate=" + updateDate
				+ ", marginalPrice=" + marginalPrice + ", returnableQty="
				+ returnableQty + ", updated=" + updated + "]";
	}

}
