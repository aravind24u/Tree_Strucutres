package com.zoho.charm.project.pricing;

import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zoho.charm.project.utils.CommonUtils;

public class Customers {
public static void main(String[] args) throws Exception{
	new Customers().addTestCustomersFromFile();
}

	public static JSONObject customers = null;
	public static JSONArray testCustomers = null;
	static {
		try {
			String content = new String(
					Files.readAllBytes(Paths.get(CommonUtils.INVOICE_HOME_DIR.concat("ZCustomers.txt"))));
			JSONObject jsonObject = new JSONObject(content);

			customers = jsonObject.getJSONObject("CUSTOMERS");
			testCustomers = (JSONArray) jsonObject.getJSONArray("TEST_CUSTOMERS");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addTestCustomersFromFile() throws Exception{
		
		List<String> practiceIds = CommonUtils.loadFile(CommonUtils.INVOICE_HOME_DIR.concat("testCustomers.txt"));
		System.out.println("Length Before Adding : " + testCustomers.length());
		practiceIds.forEach(practiceId ->{
			try {
				if(isTestCustomer(practiceId)) {
					System.out.println("Duplicate practice Id : " + practiceId);
				}else{
					addTestCustomer(practiceId);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		System.out.println("Length After Adding : " + testCustomers.length());
		writeCustomersToFile();
	}
	
	public static String getCustomerId(String practiceId) throws JSONException {
		//
		String customerId = "";

		if (customers.has(practiceId)) {
			//
			customerId = customers.getString(practiceId);

		}

		return customerId;
	}

	public void writeCustomersToFile() throws Exception {
		// try-with-resources statement based on post comment below :)
		JSONObject json = new JSONObject();
		System.out.println("No of Test Cust: " + testCustomers.length());
		json.put("TEST_CUSTOMERS", testCustomers.toString());
		json.put("CUSTOMERS", customers);
		try (FileWriter file = new FileWriter(CommonUtils.INVOICE_HOME_DIR.concat("ZCustomers.txt"))) {
			file.write(json.toString());
			System.out.println("Successfully Copied JSON Object to File...");
		}
	}

	public int getSize() {
		//
		return customers.names().length();
	}

	public void addCustomer(String practiceId, String customerId) throws JSONException {
		customers.put(practiceId, customerId);
	}

	public void addTestCustomer(String practiceId) {
		testCustomers.put(practiceId);
	}

	public static boolean isTestCustomer(String practiceId) throws Exception {
		//
		int size = testCustomers.length();
		for (int i = 0; i < size; i++) {
			if (testCustomers.getString(i).equals(practiceId)) {
				return true;
			}
		}
		return false;
	}

	public void printTestCustomers() throws Exception {
		//
		System.out.println("\n---\nTest Customers\n---\n" + testCustomers.toString() + "\n---\n");

	}

	public void printCustomers() throws Exception {
		//
		System.out.println("\n---\nCustomers\n---\n" + customers.names().toString() + "\n---\n");
	}

	public static String getPracticeId(String customerId) throws Exception {

		Iterator<String> keys = customers.keys();

		while (keys.hasNext()) {
			String key = keys.next();
			if (customers.getString(key).equals(customerId)) {
				return key;

			}
		}
		return null;

	}

}
