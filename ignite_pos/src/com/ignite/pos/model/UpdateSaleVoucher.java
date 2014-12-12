package com.ignite.pos.model;

public class UpdateSaleVoucher {

	private String vid;
	private String cusname;
	private String itemid;
	private String itemname;
	private String qty;
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
	private String admin;
	private String oldQty;
	
	public UpdateSaleVoucher() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public UpdateSaleVoucher(String vid, String itemid, String itemname, String qty,
			String itemtotal, String total, String salePerson, String discount) {
		super();
		this.vid = vid;
		this.itemid = itemid;
		this.itemname = itemname;
		this.qty = qty;
		this.itemtotal = itemtotal;
		this.total = total;
		this.salePerson = salePerson;
		this.discount = discount;
	}


	public UpdateSaleVoucher(String vid, String cusname, String itemid,
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
	
	

	public UpdateSaleVoucher(String itemid, String itemName, String VouID, String qty, String price,
			String itemtotal, String total) {
		super();
		this.itemid = itemid;
		this.itemname = itemName;
		this.vid = VouID;
		this.qty = qty;
		this.price = price;
		this.itemtotal = itemtotal;
		this.total = total;
	}

	public UpdateSaleVoucher(String vid,String cusname, String itemid, String itemname,
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

	public UpdateSaleVoucher(String itemid, String itemname, String qty,
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
	
	

	public String getAdmin() {
		return admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	

	public String getOldQty() {
		return oldQty;
	}


	public void setOldQty(String oldQty) {
		this.oldQty = oldQty;
	}


	@Override
	public String toString() {
		return "SaleVouncher [vid=" + vid + ", cusname=" + cusname
				+ ", itemid=" + itemid + ", itemname=" + itemname + ", qty="
				+ qty + ", price=" + price + ", categoryid=" + categoryid
				+ ", subcategoryid=" + subcategoryid + ", itemtotal="
				+ itemtotal + ", vdate=" + vdate + ", total=" + total
				+ ", salePerson=" + salePerson + ", discount=" + discount
				+ ", qtyTotal=" + qtyTotal + ", totalQtyMaxi=" + totalQtyMaxi
				+ ", admin=" + admin + ", oldQty=" + oldQty + "]";
	}

	

}

