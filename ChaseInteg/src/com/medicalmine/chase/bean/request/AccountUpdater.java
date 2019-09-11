package com.medicalmine.chase.bean.request;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AccountUpdater extends CommonElements {

	protected String customerRefNum;
	protected String customerProfileAction;
	protected String scheduledDate;

	public AccountUpdater() {
		super();
	}

	public AccountUpdater(String customerRefNum, String customerProfileAction, String scheduledDate) {
		super();
		this.customerRefNum = customerRefNum;
		this.customerProfileAction = customerProfileAction;
		this.scheduledDate = scheduledDate;
	}

	public String getCustomerRefNum() {
		return customerRefNum;
	}

	public String getCustomerProfileAction() {
		return customerProfileAction;
	}

	public String getScheduledDate() {
		return scheduledDate;
	}

	public void setCustomerRefNum(String customerRefNum) {
		this.customerRefNum = customerRefNum;
	}

	public void setCustomerProfileAction(String customerProfileAction) {
		this.customerProfileAction = customerProfileAction;
	}

	/**
	 * Another setter has been defined to accept the java.util.Date object which
	 * will be parsed internally
	 * 
	 * @param scheduledDate : The data should be in the format of MMDDYYYY
	 */
	public void setScheduledDate(String scheduledDate) {
		this.scheduledDate = scheduledDate;
	}

	public void setScheduledDate(Date scheduledDate) {
		SimpleDateFormat format = new SimpleDateFormat("MMDDYYYY");
		setScheduledDate(format.format(scheduledDate));
	}

}
