package com.zoho.charm.project.pricing;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.zoho.charm.project.utils.CommonUtils;

public class PricingUtil {
	static List<String> testCustomers;
	static JSONObject customers;
	static {
		try {
			String file = IOUtils.toString(new FileReader(CommonUtils.INVOICE_HOME_DIR.concat("ZCustomers.txt")));

			JSONObject jsonObject = new JSONObject(file);

			JSONArray testCustomerArray = jsonObject.getJSONArray("TEST_CUSTOMERS");
			customers = jsonObject.getJSONObject("CUSTOMERS");

			testCustomers = new ArrayList<>();
			for (Integer index = 0; index < testCustomerArray.length(); index++) {
				testCustomers.add(testCustomerArray.getString(index));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Boolean isTestCustomer(String practiceId) {

		return testCustomers.contains(practiceId);
	}
}
