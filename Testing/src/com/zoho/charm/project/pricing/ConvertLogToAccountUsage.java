package com.zoho.charm.project.pricing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

import com.zoho.charm.project.utils.CommonUtils;

public class ConvertLogToAccountUsage {
	private static String delimiter = ",";
	private static String month = "September";
	private static String year = "2019";

	public static void main(String[] args) throws Exception {

		StringBuilder builder = new StringBuilder();

		try (BufferedReader reader = new BufferedReader(
				new FileReader(CommonUtils.PRICING_HOME_DIR.concat("StoreTaskLogs_October.txt")))) {
			String line = reader.readLine();

			while (line != null) {
				builder.append(line);
				line = reader.readLine();
			}
		}
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(CommonUtils.PRICING_HOME_DIR.concat("Usage_sept_2019_New.csv")));

			writer.write(
					"Practice ID, Month, Year, Encounter Count, Encounter Charge, SMS Counts, SMS Charge, FAX Pages, FAX Charge, Scan Charge, Video Mins, Video Charge, Eclaims Usage, Eclaims Charge, eRx Count, eRx Charge,Ecommerce Count,Ecommerce Charge,Provider Count,Provider Charge,Facility Count,Facility Charge,Provider Based Encounter Count, PBE Charge,Plan Charge,External Devices Count,External Devices Cost,Direct Provider Messaging Count,Direct Provider Messaging Cost, Total");

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

				handleAttribute(practiceLogs, writer, "Count for Module ECOMMERCE is ",
						"Calculating cost for Module ECOMMERCE.", "Total cost of module ECOMMERCE is ",
						"Final Cost of Module ECOMMERCE", null);

				handleAttribute(practiceLogs, writer, "Count for Module Provider is ",
						"Calculating cost for Module Provider.", "Total cost of module Provider is ",
						"Final Cost of Module Provider", null);

				handleAttribute(practiceLogs, writer, "Count for Module Facility is ",
						"Calculating cost for Module Facility.", "Total cost of module Facility is ",
						"Final Cost of Module Facility", null);

				handleAttribute(practiceLogs, writer, "Count for Module ProviderBasedEncounter is ",
						"Calculating cost for Module ProviderBasedEncounter.",
						"Total cost of module ProviderBasedEncounter is ",
						"Final Cost of Module ProviderBasedEncounter", null);

				String planCost = getFixedChargeString(practiceLogs, "Module PlanCharge has a fixed charge of ", ",");
				writer.write(planCost);
				writer.write(delimiter);

				handleAttribute(practiceLogs, writer, "Count for Module ExternalDevices is ",
						"Calculating cost for Module ExternalDevices.", "Total cost of module ExternalDevices is ",
						"Final Cost of Module ExternalDevices", null);

				handleAttribute(practiceLogs, writer, "Count for Module DirectProviderMessaging is ",
						"Calculating cost for Module DirectProviderMessaging.",
						"Total cost of module DirectProviderMessaging is ",
						"Final Cost of Module DirectProviderMessaging", null);

				String totalCost = getFixedChargeString(practiceLogs,
						" after applying Plan cost and order discount is ", ".Practice ");
				writer.write(totalCost);
				writer.write(delimiter);

				file = file.substring(endIndex);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
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
		String cost;
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
