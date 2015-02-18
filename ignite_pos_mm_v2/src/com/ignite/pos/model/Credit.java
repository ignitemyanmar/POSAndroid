package com.ignite.pos.model;

public class Credit {
	private Integer credit_id;
	private Integer buyer_id;
	private String buyer_name;
	private String salevoucher_id;
	private String date;
	private Integer creditTotal;
	private Integer creditPaidAmount;
	private Integer creditLeftAmount;
	
	public Credit() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public Credit(Integer credit_id, Integer buyer_id, String salevoucher_id,
			String date, Integer creditTotal, Integer creditPaidAmount,
			Integer creditLeftAmount) {
		super();
		this.credit_id = credit_id;
		this.buyer_id = buyer_id;
		this.salevoucher_id = salevoucher_id;
		this.date = date;
		this.creditTotal = creditTotal;
		this.creditPaidAmount = creditPaidAmount;
		this.creditLeftAmount = creditLeftAmount;
	}
	
	

	



	public Credit(Integer buyer_id, String buyer_name, String salevoucher_id,
			String date, Integer creditTotal, Integer creditPaidAmount,
			Integer creditLeftAmount) {
		super();
		this.buyer_id = buyer_id;
		this.buyer_name = buyer_name;
		this.salevoucher_id = salevoucher_id;
		this.date = date;
		this.creditTotal = creditTotal;
		this.creditPaidAmount = creditPaidAmount;
		this.creditLeftAmount = creditLeftAmount;
	}



	public Integer getCredit_id() {
		return credit_id;
	}

	public void setCredit_id(Integer credit_id) {
		this.credit_id = credit_id;
	}

	public Integer getBuyer_id() {
		return buyer_id;
	}

	

	public String getBuyer_name() {
		return buyer_name;
	}



	public void setBuyer_name(String buyer_name) {
		this.buyer_name = buyer_name;
	}



	public void setBuyer_id(Integer buyer_id) {
		this.buyer_id = buyer_id;
	}

	public String getSalevoucher_id() {
		return salevoucher_id;
	}

	public void setSalevoucher_id(String salevoucher_id) {
		this.salevoucher_id = salevoucher_id;
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
		return "Credit [credit_id=" + credit_id + ", buyer_id=" + buyer_id
				+ ", buyer_name=" + buyer_name + ", salevoucher_id="
				+ salevoucher_id + ", date=" + date + ", creditTotal="
				+ creditTotal + ", creditPaidAmount=" + creditPaidAmount
				+ ", creditLeftAmount=" + creditLeftAmount + "]";
	}


	
	
}
