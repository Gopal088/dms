package com.dms.entity;

import java.util.Date;

public class Product {

	int productID;
	String productName;
	String date;
	float rate;

	public Product() {
		// TODO Auto-generated constructor stub
	}
	
	

	public Product(int productID, String productName,  float rate) {
		super();
		this.productID = productID;
		this.productName = productName;
		this.rate = rate;
	}



	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
}
