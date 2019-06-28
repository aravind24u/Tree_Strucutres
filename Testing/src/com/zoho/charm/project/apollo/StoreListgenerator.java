package com.zoho.charm.project.apollo;

import java.io.FileReader;
import java.util.HashMap;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVStrategy;
import org.apache.commons.lang3.StringUtils;

import com.zoho.charm.project.utils.CommonUtils;

public class StoreListgenerator {
	public static void main(String[] args) throws Exception {
		Long startTime = System.currentTimeMillis();
		String searchTerm = "520007";
		CSVStrategy csvStrategy = new CSVStrategy('|', '"', '#');
		CSVParser csvParser = new CSVParser(
				new FileReader(CommonUtils.APOLLO_HOME_DIR.concat("Stores List 2.csv")),
				csvStrategy);

		String[][] values = csvParser.getAllValues();
		HashMap<String, HashMap<String, String>> storeList = new HashMap<>();
		for (String[] line : values) {
			String shopId = line[1];
			String storeName = line[7];
			String address = line[9];
			String phone1 = line[10];
			String phone2 = line[11];
			String pincode = line[12];

			String key = storeName.concat(",").concat(address).concat(",").concat(pincode);

			HashMap<String, String> params = new HashMap<>();
			params.put("shopId", shopId);
			params.put("storeName", storeName);
			params.put("address", address);
			params.put("phone1", phone1);
			params.put("phone2", phone2);

			storeList.put(key, params);
		}

		storeList.keySet().forEach(key -> {
			if(StringUtils.contains(key.toLowerCase(), searchTerm.toLowerCase())) {
				System.out.println(storeList.get(key));
			}
		});
		System.out.println("Total Time : " + (System.currentTimeMillis() - startTime));
	}
}
