package com.zoho.charm.project.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.zoho.charm.project.utils.encoder.EncodingConstants;

public class CommonUtils {
	
	public static final String APOLLO_HOME_DIR = "/home/local/ZOHOCORP/aravind-5939/Desktop/Apollo Integration/CSV/";
	public static final String PRICING_HOME_DIR = "/home/local/ZOHOCORP/aravind-5939/Desktop/Pricing/";
	public static final String INVOICE_HOME_DIR = "/home/local/ZOHOCORP/aravind-5939/Desktop/ZInvoice/";
	
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
	
	public static List<String> getPracticeIdsToList(List<String>file) throws Exception{
		List<String>practiceIds = new ArrayList<>();
		file.forEach(line ->{
			if(StringUtils.isNotEmpty(line)) {
				String[] values = line.split(",");
				practiceIds.add(values[0]);
			}
		});
		return practiceIds;
	}
}
