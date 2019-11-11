package com.zoho.charm.project.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class Testing {

	public static void main(String[] args) {
		Map<String, String> test = new LinkedHashMap<>();

		test.put("qewr", "rewq");
		test.put("zxcv", "vcxz");
		test.put("123", "321");
		test.put("asdf", "fdsa");

		test.entrySet().forEach(entry -> {
			System.out.println(entry.getKey() + " : " + entry.getValue());

		});
	}
}
