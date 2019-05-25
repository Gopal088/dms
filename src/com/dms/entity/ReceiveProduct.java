package com.dms.entity;

import java.sql.Date;

public class ReceiveProduct {
	int prodId, provId;
	float prodRate, quantity, amount;
	String prodName;
	Date todayDate;
	
	public ReceiveProduct() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getProdId() {
		return prodId;
	}

	public void setProdId(int prodId) {
		this.prodId = prodId;
	}

	public int getProvId() {
		return provId;
	}

	public void setProvId(int provId) {
		this.provId = provId;
	}

	public float getProdRate() {
		return prodRate;
	}

	public void setProdRate(float prodRate) {
		this.prodRate = prodRate;
	}

	public float getQuantity() {
		return quantity;
	}

	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public Date getTodayDate() {
		return todayDate;
	}

	public void setTodayDate(Date todayDate) {
		this.todayDate = todayDate;
	}
	
	
}
