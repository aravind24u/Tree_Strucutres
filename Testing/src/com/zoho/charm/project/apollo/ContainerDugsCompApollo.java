package com.zoho.charm.project.apollo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class ContainerDugsCompApollo {
	private static final String folderLocation = "/home/local/ZOHOCORP/aravind-5939/Desktop/Apollo Integration/CSV/";
	private static String delimiter = "\\|";
	private static String unescapedDelimiter = "|";

	public static void main(String[] args) {
		HashMap<String, String> containerList = getHashMapFromFile("Container Master V1.0.csv");
		HashMap<String, String> drugList = getHashMapFromFile("Andhra Pradesh Item Master for API.csv");
		System.out.println("Item Id|Name in Drug List|Name in Drug List|Is Names Same");
		containerList.keySet().forEach(itemId -> {
			if (drugList.keySet().contains(itemId)) {
				System.out.print(System.lineSeparator());
				System.out.print(itemId);
				System.out.print(unescapedDelimiter);
				System.out.print(drugList.get(itemId));
				System.out.print(unescapedDelimiter);
				System.out.print(containerList.get(itemId));
				System.out.print(unescapedDelimiter);
				System.out.print(containerList.get(itemId).equals(drugList.get(itemId)));
			}
		});
	}

	public static HashMap<String, String> getHashMapFromFile(String fileName) {

		BufferedReader reader = null;
		HashMap<String, String> map = new HashMap<>();
		System.out.println("Loading file : ".concat(folderLocation.concat(fileName)));
		try {
			reader = new BufferedReader(new FileReader(folderLocation.concat(fileName)));

			String line = reader.readLine();

			while ((line = reader.readLine()) != null) {
				String[] values = line.trim().split(delimiter, -1);

				String itemId = values[0];
				String itemName = values[1];
				map.put(itemId, itemName);

			}
			System.out.println("Finished loading file : ".concat(folderLocation.concat(fileName)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
}
