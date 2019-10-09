package com.medicalmine.aravind.chase.bean.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Request")
@XmlAccessorType(XmlAccessType.FIELD)
public class Request {

	@XmlElement(name = "NewOrder")
	protected NewOrder newOrder;

	@XmlElement(name = "MarkForCapture")
	protected MarkForCapture markForCapture;

	@XmlElement(name = "AccountUpdater")
	protected AccountUpdater accountUpdater;

	@XmlElement(name = "Reversal")
	protected Reversal reversal;

	public Request() {
		super();
	}

	public Request(NewOrder order) {
		setNewOrder(order);
	}

	public Request(MarkForCapture order) {
		setMarkForCapture(order);
	}

	public Request(AccountUpdater accountUpdater) {
		setAccountUpdater(accountUpdater);
	}

	public Request(Reversal reversal) {
		setReversal(reversal);
	}

	public NewOrder getNewOrder() {
		return newOrder;
	}

	public void setNewOrder(NewOrder newOrder) {
		this.newOrder = newOrder;
	}

	public MarkForCapture getMarkForCapture() {
		return markForCapture;
	}

	public void setMarkForCapture(MarkForCapture markForCapture) {
		this.markForCapture = markForCapture;
	}

	public AccountUpdater getAccountUpdater() {
		return accountUpdater;
	}

	public void setAccountUpdater(AccountUpdater accountUpdater) {
		this.accountUpdater = accountUpdater;
	}

	public Reversal getReversal() {
		return reversal;
	}

	public void setReversal(Reversal reversal) {
		this.reversal = reversal;
	}

}
