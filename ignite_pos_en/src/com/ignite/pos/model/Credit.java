package com.ignite.pos.model;

public class Credit {
	private String creditCusName;
	private String creditDate;
	private String creditTotal;
	private String creditPayAmt;
	private String creditLeftToPayAmt;
	
	public Credit() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Credit(String creditCusName, String creditDate, String creditTotal,
			String creditPayAmt, String creditLeftToPayAmt) {
		super();
		this.setCreditCusName(creditCusName);
		this.setCreditDate(creditDate);
		this.setCreditTotal(creditTotal);
		this.setCreditPayAmt(creditPayAmt);
		this.setCreditLeftToPayAmt(creditLeftToPayAmt);
	}

	public String getCreditCusName() {
		return creditCusName;
	}

	public void setCreditCusName(String creditCusName) {
		this.creditCusName = creditCusName;
	}

	public String getCreditDate() {
		return creditDate;
	}

	public void setCreditDate(String creditDate) {
		this.creditDate = creditDate;
	}

	public String getCreditTotal() {
		return creditTotal;
	}

	public void setCreditTotal(String creditTotal) {
		this.creditTotal = creditTotal;
	}

	public String getCreditPayAmt() {
		return creditPayAmt;
	}

	public void setCreditPayAmt(String creditPayAmt) {
		this.creditPayAmt = creditPayAmt;
	}

	public String getCreditLeftToPayAmt() {
		return creditLeftToPayAmt;
	}

	public void setCreditLeftToPayAmt(String creditLeftToPayAmt) {
		this.creditLeftToPayAmt = creditLeftToPayAmt;
	}
	
}
