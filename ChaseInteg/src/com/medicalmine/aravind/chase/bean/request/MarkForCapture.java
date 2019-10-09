package com.medicalmine.aravind.chase.bean.request;

import com.medicalmine.aravind.chase.bean.CommonElements;

public class MarkForCapture extends CommonElements {

	public MarkForCapture() {
		super();
	}

	public MarkForCapture(Long orderId, Double amount) {
		super(null, orderId, amount);
	}
}
