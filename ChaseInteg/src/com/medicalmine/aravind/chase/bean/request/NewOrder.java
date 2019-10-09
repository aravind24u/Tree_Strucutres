package com.medicalmine.aravind.chase.bean.request;

import javax.xml.bind.annotation.XmlElement;

import com.medicalmine.aravind.chase.bean.CommonElements;

public class NewOrder extends CommonElements {

	protected String cardBrand;
	protected String accountNumber;
	protected String expiryDate;
	protected String customerProfileFromOrderInd;
	protected String customerProfileOrderOverrideInd;

	public NewOrder() {
		super();
	}

	public NewOrder(String messageType, Long orderId, Double amount, String cardBrand, String accountNumber,
			String expiryDate, String name, String line11, String line22, String city, String state, String postalCode,
			String phoneNumber, Boolean createProfile) {
		super(messageType, orderId, amount);

		setCardBrand(cardBrand);
		setAccountNumber(accountNumber);
		setExpiryDate(expiryDate);

		setName(name);
		setLine11(line11);
		setLine22(line22);
		setCity(city);
		setState(state);
		setPostalCode(postalCode);
		setPhoneNumber(phoneNumber);

		if (createProfile) {
			customerProfileFromOrderInd = "A";
			customerProfileOrderOverrideInd = "NO";
		}
	}

	@XmlElement(name = "CardBrand")
	public String getCardBrand() {
		return cardBrand;
	}

	@XmlElement(name = "AccountNum")
	public String getAccountNumber() {
		return accountNumber;
	}

	@XmlElement(name = "Exp")
	public String getExpiryDate() {
		return expiryDate;
	}

	@XmlElement(name = "CustomerProfileFromOrderInd")
	public String getCustomerProfileFromOrderInd() {
		return customerProfileFromOrderInd;
	}

	@XmlElement(name = "CustomerProfileOrderOverrideInd")
	public String getCustomerProfileOrderOverrideInd() {
		return customerProfileOrderOverrideInd;
	}

	@XmlElement(name = "AVSaddress1")
	public String getLine11() {
		return line11;
	}

	@XmlElement(name = "AVSaddress2")
	public String getLine22() {
		return line22;
	}

	@XmlElement(name = "AVScity")
	public String getCity() {
		return city;
	}

	@XmlElement(name = "AVSstate")
	public String getState() {
		return state;
	}

	@XmlElement(name = "AVSzip")
	public String getPostalCode() {
		return postalCode;
	}

	@XmlElement(name = "AVSphoneNum")
	public String getPhoneNumber() {
		return phoneNumber;
	}

	@XmlElement(name = "AVSname")
	public String getName() {
		return name;
	}

	public void setCardBrand(String cardBrand) {
		this.cardBrand = cardBrand;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

}
