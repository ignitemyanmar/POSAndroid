package com.ignite.pos.model;

public class CreditSupplier {

	private Integer creditID;
	private Integer supplierID;
	private String supplierName;
	private String purchaseVoucherID;
	private String date;
	private Integer creditTotal;
	private Integer creditPaidAmount;
	private Integer creditLeftAmount;
	
	public CreditSupplier() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CreditSupplier(Integer creditID, Integer supplierID,
			String supplierName, String purchaseVoucherID, String date,
			Integer creditTotal, Integer creditPaidAmount,
			Integer creditLeftAmount) {
		super();
		this.creditID = creditID;
		this.supplierID = supplierID;
		this.supplierName = supplierName;
		this.purchaseVoucherID = purchaseVoucherID;
		this.date = date;
		this.creditTotal = creditTotal;
		this.creditPaidAmount = creditPaidAmount;
		this.creditLeftAmount = creditLeftAmount;
	}

	public CreditSupplier(Integer supplierID, String supplierName,
			String purchaseVoucherID, String date, Integer creditTotal,
			Integer creditPaidAmount, Integer creditLeftAmount) {
		super();
		this.supplierID = supplierID;
		this.supplierName = supplierName;
		this.purchaseVoucherID = purchaseVoucherID;
		this.date = date;
		this.creditTotal = creditTotal;
		this.creditPaidAmount = creditPaidAmount;
		this.creditLeftAmount = creditLeftAmount;
	}

	public Integer getCreditID() {
		return creditID;
	}

	public void setCreditID(Integer creditID) {
		this.creditID = creditID;
	}

	public Integer getSupplierID() {
		return supplierID;
	}

	public void setSupplierID(Integer supplierID) {
		this.supplierID = supplierID;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getPurchaseVoucherID() {
		return purchaseVoucherID;
	}

	public void setPurchaseVoucherID(String purchaseVoucherID) {
		this.purchaseVoucherID = purchaseVoucherID;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getCreditTotal() {
		return creditTotal;
	}

	public void setCreditTotal(Integer creditTotal) {
		this.creditTotal = creditTotal;
	}

	public Integer getCreditPaidAmount() {
		return creditPaidAmount;
	}

	public void setCreditPaidAmount(Integer creditPaidAmount) {
		this.creditPaidAmount = creditPaidAmount;
	}

	public Integer getCreditLeftAmount() {
		return creditLeftAmount;
	}

	public void setCreditLeftAmount(Integer creditLeftAmount) {
		this.creditLeftAmount = creditLeftAmount;
	}

	@Override
	public String toString() {
		return "CreditSupplier [creditID=" + creditID + ", supplierID="
				+ supplierID + ", supplierName=" + supplierName
				+ ", purchaseVoucherID=" + purchaseVoucherID + ", date=" + date
				+ ", creditTotal=" + creditTotal + ", creditPaidAmount="
				+ creditPaidAmount + ", creditLeftAmount=" + creditLeftAmount
				+ "]";
	}
}
