package com.zoho.charm.project.pricing.invoice;

import java.io.FileReader;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVStrategy;
import org.apache.commons.lang3.StringUtils;

import com.zoho.charm.project.pricing.Customers;
import com.zoho.charm.project.utils.CommonUtils;

public class CompareInvoiceAndMPH {
	public static void main(String[] args) throws Exception {

		HashMap<String, HashMap<String, Double>> invoices = getInvoiceData(
				CommonUtils.INVOICE_HOME_DIR.concat("September_Consultation_Count_2019.csv"));

		List<String> usageCSVData = CommonUtils.loadFile(CommonUtils.USAGE_CSV);

		StringBuilder builder = new StringBuilder();

		usageCSVData.forEach(line -> {
			try {
				if (!line.startsWith("#")) {
					String[] values = line.split(",");

					String practiceId = values[0];

					Double encounterCount = Double.parseDouble(values[22]) > 0 ? Double.parseDouble(values[22])
							: Double.parseDouble(values[3]);
					Double encounterCost = Double.parseDouble(new Float(values[23]) == 0f ? values[4] : values[23]);
					Double totalCost = Double.parseDouble(values[25]);

					String customerId = Customers.getCustomerId(practiceId);
					if (customerId != null && !StringUtils.isEmpty(customerId)) {
						System.out.println(MessageFormat.format("Comparing for Practice Id : {0}\tCustomer Id : {1}",
								practiceId, customerId));
						HashMap<String, Double> invoiceData = invoices.get(customerId);
						if (invoiceData != null) {
							if (!invoiceData.get("EncounterCost").equals(encounterCost)
									|| !invoiceData.get("EncounterQuantity").equals(encounterCount)
									|| !invoiceData.get("InvoiceCost").equals(totalCost)) {

								builder.append(MessageFormat.format("\n\n Practice Id : {0}\tCustomer Id : {1}",
										practiceId, customerId));

								if (!invoiceData.get("EncounterCost").equals(encounterCost)) {
									builder.append(MessageFormat.format(
											"\n Encounter Cost : {0}\tInvoice Encounter Cost : {1}", encounterCost,
											invoiceData.get("EncounterCost")));
								}

								if (!invoiceData.get("EncounterQuantity").equals(encounterCount)) {
									builder.append(MessageFormat.format(
											"\n Encounter Count : {0}\tInvoice Encounter Count : {1}", encounterCount,
											invoiceData.get("EncounterQuantity")));
								}

								if (!invoiceData.get("InvoiceCost").equals(totalCost)) {
									builder.append(MessageFormat.format("\n Total Cost : {0}\tInvoice Total Cost : {1}",
											totalCost, invoiceData.get("InvoiceCost")));
								}
							}
						} /*
							 * else if(!Customers.isTestCustomer(practiceId) && totalCost > 0){
							 * builder.append(MessageFormat.
							 * format("\n\n  Invoice data is not present for practice {0} , Total Cost : {1}"
							 * ,practiceId,totalCost )); }
							 */

					}
				}
			} catch (Exception e) {
				System.out.println(line);
				e.printStackTrace();
			}
		});

		System.out.println(builder.toString());

		System.out.println("\n\n checking for Customers without practiceId");

		invoices.keySet().forEach(customerId -> {
			try {
				String practiceId = Customers.getPracticeId(customerId);
				if (practiceId == null) {
					System.out.println("\n" + customerId);
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

	}

	public static HashMap<String, HashMap<String, Double>> getInvoiceData(String fileName) throws Exception {
		FileReader reader = new FileReader(fileName);

		CSVStrategy csvStrategy = new CSVStrategy(',', '"', '#');

		CSVParser parser = new CSVParser(reader, csvStrategy);

		String[][] values = parser.getAllValues();

		HashMap<String, HashMap<String, Double>> invoices = new HashMap<>();

		for (String[] invoice : values) {
			String customerId = invoice[1];

			Double encounterQuantity = Double.parseDouble(invoice[6]);
			Double encounterCost = Double.parseDouble(invoice[7].replace("USD", "").replace(",", ""));
			Double invoiceCost = Double.parseDouble(invoice[4].replace("USD", "").replace(",", ""));

			HashMap<String, Double> invoiceData = new HashMap<>();

			invoiceData.put("EncounterCost", encounterCost);
			invoiceData.put("EncounterQuantity", encounterQuantity);
			invoiceData.put("InvoiceCost", invoiceCost);

			invoices.put(customerId, invoiceData);
		}
		return invoices;
	}
}
