package com.ignite.pos.model;

public class Profit {

	private Integer profitId;
	private String itemId;
	private String date;
	private Integer marginalPrice;
	private Integer salePrice;
	private Integer saleQty;
	private Integer profit;
	private Integer totalSalePrice;
	private Integer totalmarginalPrice;
	private Integer totalSaleQty;
	private Integer totalProfit;
	private Integer purchasePrice;
	private String vid;
	private String itemName;
	private Integer discount;
	private Integer totalDiscount;
	
	public Profit() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Profit(String itemId, String date, Integer marginalPrice,
			Integer salePrice, Integer saleQty, Integer profit, String vid, String itemName, Integer discount) {
		super();
		this.itemId = itemId;
		this.date = date;
		this.marginalPrice = marginalPrice;
		this.salePrice = salePrice;
		this.saleQty = saleQty;
		this.profit = profit;
		this.vid = vid;
		this.itemName = itemName;
		this.discount = discount;
	}

	public Profit(Integer profitId, String itemId, String date,
			Integer marginalPrice, Integer salePrice, Integer saleQty,
			Integer profit, String itemName, Integer discount) {
		super();
		this.profitId = profitId;
		this.itemId = itemId;
		this.date = date;
		this.marginalPrice = marginalPrice;
		this.salePrice = salePrice;
		this.saleQty = saleQty;
		this.profit = profit;
		this.itemName = itemName;
		this.discount = discount;
	}
	
	
	
	public Profit(String itemId, String date, Integer totalSalePrice,
			Integer totalProfit, Integer purchasePrice, String itemName, Integer discount) {
		super();
		this.itemId = itemId;
		this.date = date;
		this.totalSalePrice = totalSalePrice;
		this.totalProfit = totalProfit;
		this.purchasePrice = purchasePrice;
		this.itemName = itemName;
		this.discount = discount;
	}

	public Profit(String itemId, String date, Integer marginalPrice,
			Integer salePrice, Integer saleQty, Integer profit, String itemName, Integer discount) {
		super();
		this.itemId = itemId;
		this.date = date;
		this.marginalPrice = marginalPrice;
		this.salePrice = salePrice;
		this.saleQty = saleQty;
		this.profit = profit;
		this.itemName = itemName;
		this.discount = discount;
	}

	public Integer getProfitId() {
		return profitId;
	}
	public void setProfitId(Integer profitId) {
		this.profitId = profitId;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Integer getMarginalPrice() {
		return marginalPrice;
	}
	public void setMarginalPrice(Integer marginalPrice) {
		this.marginalPrice = marginalPrice;
	}
	public Integer getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(Integer salePrice) {
		this.salePrice = salePrice;
	}
	public Integer getSaleQty() {
		return saleQty;
	}
	public void setSaleQty(Integer saleQty) {
		this.saleQty = saleQty;
	}
	public Integer getProfit() {
		return profit;
	}
	public void setProfit(Integer profit) {
		this.profit = profit;
	}

	public Integer getTotalSalePrice() {
		return totalSalePrice;
	}

	public void setTotalSalePrice(Integer totalSalePrice) {
		this.totalSalePrice = totalSalePrice;
	}

	public Integer getTotalmarginalPrice() {
		return totalmarginalPrice;
	}

	public void setTotalmarginalPrice(Integer totalmarginalPrice) {
		this.totalmarginalPrice = totalmarginalPrice;
	}

	public Integer getTotalSaleQty() {
		return totalSaleQty;
	}

	public void setTotalSaleQty(Integer totalSaleQty) {
		this.totalSaleQty = totalSaleQty;
	}

	public Integer getTotalProfit() {
		return totalProfit;
	}

	public void setTotalProfit(Integer totalProfit) {
		this.totalProfit = totalProfit;
	}

	
	public Integer getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(Integer purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	
	public String getVid() {
		return vid;
	}


	public void setVid(String vid) {
		this.vid = vid;
	}

	

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	
	public Integer getTotalDiscount() {
		return totalDiscount;
	}

	public void setTotalDiscount(Integer totalDiscount) {
		this.totalDiscount = totalDiscount;
	}

	@Override
	public String toString() {
		return "Profit [profitId=" + profitId + ", itemId=" + itemId
				+ ", date=" + date + ", marginalPrice=" + marginalPrice
				+ ", salePrice=" + salePrice + ", saleQty=" + saleQty
				+ ", profit=" + profit + ", totalSalePrice=" + totalSalePrice
				+ ", totalmarginalPrice=" + totalmarginalPrice
				+ ", totalSaleQty=" + totalSaleQty + ", totalProfit="
				+ totalProfit + ", purchasePrice=" + purchasePrice + ", vid="
				+ vid + ", itemName=" + itemName + ", discount=" + discount
				+ ", totalDiscount=" + totalDiscount + "]";
	}


}
