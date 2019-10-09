package com.medicalmine.aravind.chase.bean;

import javax.xml.bind.annotation.XmlElement;

public class CommonElements {

	protected String orbitalConnectionUserName = "UserName";
	protected String orbitalConnectionPassword = "Password";
	protected String industryType = "RC";
	protected String bin = "000001";
	protected String merchantID = "371074";
	protected String terminalID = "001";
	protected String currencyCode = "840";
	protected String currencyExponent = "2";
	protected String messageType;

	protected String line11;
	protected String line22;
	protected String city;
	protected String state;
	protected String postalCode;
	protected String phoneNumber;

	protected String name;

	protected Long orderId;
	protected Double amount;

	public CommonElements() {
		super();
	}

	public CommonElements(String messageType, Long orderId, Double amount) {
		super();
		setOrderId(orderId);
		setAmount(amount);
	}

	@XmlElement(name = "OrbitalConnectionUserName", required = true)
	public String getOrbitalConnectionUserName() {
		return orbitalConnectionUserName;
	}

	@XmlElement(name = "OrbitalConnectionPassword", required = true)
	public String getOrbitalConnectionPassword() {
		return orbitalConnectionPassword;
	}

	@XmlElement(name = "IndustryType", required = true)
	public String getIndustryType() {
		return industryType;
	}

	@XmlElement(name = "BIN", required = true)
	public String getBin() {
		return bin;
	}

	@XmlElement(name = "MerchantID", required = true)
	public String getMerchantID() {
		return merchantID;
	}

	@XmlElement(name = "TerminalID", required = true)
	public String getTerminalID() {
		return terminalID;
	}

	@XmlElement(name = "CurrencyCode", required = true)
	public String getCurrencyCode() {
		return currencyCode;
	}

	@XmlElement(name = "CurrencyExponent", required = true)
	public String getCurrencyExponent() {
		return currencyExponent;
	}

	@XmlElement(name = "OrderID")
	public Long getOrderId() {
		return orderId;
	}

	@XmlElement(name = "Amount")
	public Double getAmount() {
		return amount;
	}

	@XmlElement(name = "MessageType")
	public String getMessageType() {
		return messageType;
	}

	public void setOrbitalConnectionUserName(String orbitalConnectionUserName) {
		this.orbitalConnectionUserName = orbitalConnectionUserName;
	}

	public void setOrbitalConnectionPassword(String orbitalConnectionPassword) {
		this.orbitalConnectionPassword = orbitalConnectionPassword;
	}

	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}

	public void setBin(String bin) {
		this.bin = bin;
	}

	public void setMerchantID(String merchantID) {
		this.merchantID = merchantID;
	}

	public void setTerminalID(String terminalID) {
		this.terminalID = terminalID;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public void setCurrencyExponent(String currencyExponent) {
		this.currencyExponent = currencyExponent;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public void setLine11(String line11) {
		this.line11 = line11;
	}

	public void setLine22(String line22) {
		this.line22 = line22;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setName(String name) {
		this.name = name;
	}

}
