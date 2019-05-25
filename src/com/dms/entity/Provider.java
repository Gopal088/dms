package com.dms.entity;

public class Provider {
	int providerID;
	String providerName;
	String prim_mobile,	alt_mobile,	aadhar,	bank_name,ifsc,	account_no1;
	
	public String getPrim_mobile() {
		return prim_mobile;
	}

	public void setPrim_mobile(String prim_mobile) {
		this.prim_mobile = prim_mobile;
	}

	public String getAlt_mobile() {
		return alt_mobile;
	}

	public void setAlt_mobile(String alt_mobile) {
		this.alt_mobile = alt_mobile;
	}

	public String getAadhar() {
		return aadhar;
	}

	public void setAadhar(String aadhar) {
		this.aadhar = aadhar;
	}

	public String getBank_name() {
		return bank_name;
	}

	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}

	public String getIfsc() {
		return ifsc;
	}

	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
	}

	public String getAccount_no1() {
		return account_no1;
	}

	public void setAccount_no1(String account_no1) {
		this.account_no1 = account_no1;
	}

	public Provider() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Provider(int providerID, String providerName) {
		super();
		this.providerID = providerID;
		this.providerName = providerName;
	}

	public int getProviderID() {
		return providerID;
	}
	public void setProviderID(int providerID) {
		this.providerID = providerID;
	}
	public String getProviderName() {
		return providerName;
	}
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
}
