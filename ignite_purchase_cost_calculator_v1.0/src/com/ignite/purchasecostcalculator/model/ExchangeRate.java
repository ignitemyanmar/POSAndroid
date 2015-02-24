package com.ignite.purchasecostcalculator.model;

public class ExchangeRate {

	Integer rateId;
	Double exchangeRateChina;
	Double exchangeRateThai;
	
	
	public ExchangeRate() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public ExchangeRate(Double exchangeRateChina, Double exchangeRateThai) {
		super();
		this.exchangeRateChina = exchangeRateChina;
		this.exchangeRateThai = exchangeRateThai;
	}


	public ExchangeRate(Integer rateId, Double exchangeRateChina,
			Double exchangeRateThai) {
		super();
		this.rateId = rateId;
		this.exchangeRateChina = exchangeRateChina;
		this.exchangeRateThai = exchangeRateThai;
	}

	public Integer getRateId() {
		return rateId;
	}

	public void setRateId(Integer rateId) {
		this.rateId = rateId;
	}

	public Double getExchangeRateChina() {
		return exchangeRateChina;
	}

	public void setExchangeRateChina(Double exchangeRateChina) {
		this.exchangeRateChina = exchangeRateChina;
	}

	public Double getExchangeRateThai() {
		return exchangeRateThai;
	}

	public void setExchangeRateThai(Double exchangeRateThai) {
		this.exchangeRateThai = exchangeRateThai;
	}

	@Override
	public String toString() {
		return "ExchangeRate [rateId=" + rateId + ", exchangeRateChina="
				+ exchangeRateChina + ", exchangeRateThai=" + exchangeRateThai
				+ "]";
	}
}
