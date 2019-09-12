package com.medicalmine.chase.bean.request;

import javax.xml.bind.annotation.XmlElement;

import com.medicalmine.chase.bean.CommonElements;

public class Reversal extends CommonElements {

	protected String txReferenceNumber;
	protected String txReferenceIdx;

	public Reversal(Long orderId, Double amount, String txReferenceNumber, String txReferenceIdx) {
		super(null, orderId, amount);
		this.txReferenceNumber = txReferenceNumber;
		this.txReferenceIdx = txReferenceIdx;
	}

	public Reversal() {
		super();
	}

	@XmlElement(name = "AdjustedAmt")
	public Double getAmount() {
		return amount;
	}

	@XmlElement(name = "TxRefNum")
	public String getTxReferenceNumber() {
		return txReferenceNumber;
	}

	@XmlElement(name = "TxRefIdx")
	public String getTxReferenceIdx() {
		return txReferenceIdx;
	}

	public void setTxReferenceNumber(String txReferenceNumber) {
		this.txReferenceNumber = txReferenceNumber;
	}

	public void setTxReferenceIdx(String txReferenceIdx) {
		this.txReferenceIdx = txReferenceIdx;
	}

}
