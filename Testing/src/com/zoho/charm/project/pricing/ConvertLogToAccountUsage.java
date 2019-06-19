package com.zoho.charm.project.pricing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class ConvertLogToAccountUsage {
	private static final String folderLocation = "/home/local/ZOHOCORP/aravind-5939/Desktop/Pricing/";
	private static String delimiter = ",";

	public static void main(String[] args) throws Exception {
		BufferedReader reader = null;
		BufferedWriter writer = null;
		try {
			String month = "May";
			String year = "2019";

			reader = new BufferedReader(new FileReader(folderLocation.concat("StoreTaskLogs_May.txt")));
			writer = new BufferedWriter(new FileWriter(folderLocation.concat("Usage_May_2019_New.csv")));
			writer.write(
					"Practice ID, Month, Year, Encounter Count, Encounter Charge, SMS Counts, SMS Charge, FAX Pages, FAX Charge, Scan Charge, Video Mins, Video Charge, Eclaims Usage, Eclaims Charge, eRx Count, eRx Charge, Total");

			StringBuilder builder = new StringBuilder();

			String line = reader.readLine();

			while (line != null) {
				builder.append(line);
				line = reader.readLine();
			}
			String file = builder.toString();

			while (true) {

				Integer beginIndex = file.indexOf("Calculating the Charge for Practice : ");
				if (beginIndex == -1) {
					break;
				}

				Integer endIndex = file.indexOf("Finshed Calculating the Charge for Practice : ") + 97;

				String practiceLogs = file.substring(beginIndex, endIndex);
				System.out.println(practiceLogs);

				String practiceId = practiceLogs.substring(38, 53).trim();
				writer.write(System.lineSeparator());
				writer.write(practiceId);
				writer.write(delimiter);
				writer.write(month);
				writer.write(delimiter);
				writer.write(year);
				writer.write(delimiter);

				handleAttribute(practiceLogs, writer, "Count for Module Encounter is ",
						"Calculating cost for Module Encounter.", "Total cost of module Encounter is ",
						"Final Cost of Module Encounter", null);

				handleAttribute(practiceLogs, writer, "Count for Module SMS is ", "Calculating cost for Module SMS.",
						"Total cost of module SMS is ", "Final Cost of Module SMS",
						"Module SMS has a fixed charge of ");

				handleAttribute(practiceLogs, writer, "Count for Module Fax is ", "Calculating cost for Module Fax.",
						"Total cost of module Fax is ", "Final Cost of Module Fax",
						"Module Fax has a fixed charge of ");

				String scanCost = getFixedChargeString(practiceLogs, "Module Scan has a fixed charge of ", ",");
				writer.write(scanCost);
				writer.write(delimiter);

				handleAttribute(practiceLogs, writer, "Count for Module Telehealth is ",
						"Calculating cost for Module Telehealth.", "Total cost of module Telehealth is ",
						"Final Cost of Module Telehealth", null);

				handleAttribute(practiceLogs, writer, "Count for Module Eclaims is ",
						"Calculating cost for Module Eclaims.", "Total cost of module Eclaims is ",
						"Final Cost of Module Eclaims", null);

				handleAttribute(practiceLogs, writer, "Count for Module eRx is ", "Calculating cost for Module eRx.",
						"Total cost of module eRx is ", "Final Cost of Module eRx", null);

				String totalCost = getFixedChargeString(practiceLogs,
						" after applying Plan cost and order discount is ", ".Practice ");
				writer.write(totalCost);
				writer.write(delimiter);

				file = file.substring(endIndex);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
			if (writer != null) {
				writer.flush();
				writer.close();
			}
		}
	}

	public static void handleAttribute(String practiceLogs, BufferedWriter writer, String countStartString,
			String countEndString, String costStartString, String costEndString, String fixedChargeString)
			throws Exception {
		Integer countStartIndex = practiceLogs.indexOf(countStartString);
		Integer countEndIndex = practiceLogs.indexOf(countEndString);
		String count = "0";
		String cost = "0";
		if (countStartIndex != -1 && countEndIndex != -1) {
			countStartIndex += countStartString.length();
			count = practiceLogs.substring(countStartIndex, countEndIndex);
		}
		Integer costStartIndex = practiceLogs.indexOf(costStartString);
		Integer costEndIndex = practiceLogs.indexOf(costEndString);
		if (costStartIndex != -1 && costEndIndex != -1) {
			costStartIndex += costStartString.length();

			cost = practiceLogs.substring(costStartIndex, costEndIndex);
		} else {
			cost = getFixedChargeString(practiceLogs, fixedChargeString, ",");
		}
		writer.write(count);
		writer.write(delimiter);
		writer.write(cost);
		writer.write(delimiter);

	}

	public static String getFixedChargeString(String practiceLogs, String fixedChargeString, String endOfData) {
		String cost = "0";
		if (fixedChargeString != null && practiceLogs.indexOf(fixedChargeString) != -1) {
			Integer fixedChargeStartIndex = practiceLogs.indexOf(fixedChargeString) + fixedChargeString.length();
			Integer fixedChargeEndIndex = practiceLogs.substring(fixedChargeStartIndex).indexOf(endOfData)
					+ fixedChargeStartIndex;
			cost = practiceLogs.substring(fixedChargeStartIndex, fixedChargeEndIndex);
		}
		return cost;
	}
}
