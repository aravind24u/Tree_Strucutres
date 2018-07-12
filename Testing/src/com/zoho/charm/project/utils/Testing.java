package com.zoho.charm.project.utils;

import com.adventnet.iam.xss.IAMEncoder;

public class Testing {

	public static void main(String[] args) throws Exception {
		String a = "36.5%";
		
		System.out.println(IAMEncoder.encodeCSS(a));
		System.out.println(IAMEncoder.encodeHTMLAttribute(a));
	}

}
