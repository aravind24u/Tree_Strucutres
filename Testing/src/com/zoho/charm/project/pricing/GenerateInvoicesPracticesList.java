package com.zoho.charm.project.pricing;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.zoho.charm.project.utils.CommonUtils;

public class GenerateInvoicesPracticesList {

	public static void main(String[] args) throws Exception{
		
		String file = IOUtils.toString(new FileReader(CommonUtils.INVOICE_HOME_DIR.concat("ZCustomers.txt")));
		List<String> invoicePracticeIds = new ArrayList<>();
		
		JSONObject jsonObject = new JSONObject(file);

		JSONArray testCustomers = jsonObject.getJSONArray("TEST_CUSTOMERS");
		JSONObject customers = jsonObject.getJSONObject("CUSTOMERS");
		
		for(Integer index = 0 ; index < testCustomers.length();index++) {
			invoicePracticeIds.add(testCustomers.get(index).toString());
		}
		
		System.out.println(invoicePracticeIds.size());
		
		List<String> usages = IOUtils.readLines(new FileReader(CommonUtils.PRICING_HOME_DIR.concat("Usage_may_Posted_ramanathan.csv")));

		BufferedWriter writer = new BufferedWriter(new FileWriter(CommonUtils.PRICING_HOME_DIR.concat("NotInTestOrCustomers.csv"))); 
		writer.write(usages.get(0));
		usages.forEach(line -> {
			String[] values = line.split(",");
			String practiceId = values[0];
			if(!invoicePracticeIds.contains(practiceId) && !customers.has(practiceId)) {
				try {
					writer.write(System.lineSeparator());
					writer.write(line);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		writer.flush();
		writer.close();
		
	}
	
	

}
