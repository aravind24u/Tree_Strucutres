package com.medicalmine.chase.bean.request;

import com.medicalmine.chase.bean.CommonElements;

public class MarkForCapture extends CommonElements {

	public MarkForCapture() {
		super();
	}

	public MarkForCapture(Long orderId, Double amount) {
		super(null, orderId, amount);
	}
}
