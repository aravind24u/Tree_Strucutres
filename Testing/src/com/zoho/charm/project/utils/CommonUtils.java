package com.zoho.charm.project.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.zoho.charm.project.utils.encoder.EncodingConstants;

public class CommonUtils {

	public static final String APOLLO_HOME_DIR = "/home/local/ZOHOCORP/aravind-5939/Desktop/Apollo Integration/CSV/";
	public static final String PRICING_HOME_DIR = "/home/med/Aravind/Docs/pricing/";
	public static final String INVOICE_HOME_DIR = "/home/med/Aravind/Docs/Invoice/";

	private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.###");;

	public static List<String> loadFile(String fileName) {
		List<String> contents = new ArrayList<>();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(fileName));
			String line = reader.readLine();
			while (line != null) {
				if (StringUtils.isNotEmpty(line.trim())) {
					contents.add(line.trim());
				}
				line = reader.readLine();
			}
		} catch (Exception e) {
			System.out.println("File with the list of file names : " + EncodingConstants.FILE_NAMES_LOCATION
					+ " is not found , so moving on to the next method to get file names\n\n");
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
				}
			}
		}

		return contents;
	}

	public static List<String> getPracticeIdsToList(List<String> file) throws Exception {
		List<String> practiceIds = new ArrayList<>();
		file.forEach(line -> {
			if (StringUtils.isNotEmpty(line)) {
				String[] values = line.split(",");
				practiceIds.add(values[0]);
			}
		});
		return practiceIds;
	}

	public static HashMap<String, Double> getHashMapFromFile(String fileName) {
		return getHashMapFromFile(CommonUtils.PRICING_HOME_DIR.concat(fileName), 0, 16, ",");
	}

	public static HashMap<String, Double> getHashMapFromFile(String fileName, Integer keyIndex, Integer valueIndex,
			String delimiter) {

		BufferedReader reader = null;
		HashMap<String, Double> usage = new HashMap<>();
		System.out.println("Loading file : ".concat(fileName));
		try {
			reader = new BufferedReader(new FileReader(fileName));

			String line = reader.readLine();
			line = reader.readLine();

			while (line != null) {
				line = line.replaceAll("\"", "");
				String[] values = line.split(delimiter);

				String practiceId = values[keyIndex];
				Double monthlyCharge = new Double(DECIMAL_FORMAT.format(new Double(values[valueIndex])));

				usage.put(practiceId, monthlyCharge);

				line = reader.readLine();
			}
			System.out.println("Finished loading file : ".concat(CommonUtils.PRICING_HOME_DIR.concat(fileName)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return usage;
	}
}
