package com.zoho.charm.project.pricing;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.zoho.charm.project.utils.CommonUtils;

public class CompareStatusInInvoiceAndDB {

	public static void main(String[] args) {
		List<String> invoiceFile = CommonUtils.loadFile(CommonUtils.INVOICE_HOME_DIR.concat("InvoicesStatus.txt"));
		List<String> dbFile = CommonUtils.loadFile(CommonUtils.INVOICE_HOME_DIR.concat("InvoiceStatusInDB.csv"));
		Map<String, String> invoiceMap = new HashMap<>();
		invoiceFile.forEach(line -> {
			String[] values = line.split(",");
			String status = values[2];
			if (status.equals("overdue")) {
				status = "UNPAID";
			}
			invoiceMap.put(values[0], status.toLowerCase());
		});

		HashMap<String, String> dbMap = new HashMap<>();
		dbFile.forEach(line -> {
			String[] values = line.split(",");
			dbMap.put(values[0], values[1].toLowerCase());
		});

		Set<String> practiceIds = new HashSet<>();
		practiceIds.addAll(invoiceMap.keySet());
		practiceIds.addAll(dbMap.keySet());

		System.out.println("Length of Invoice list = " + invoiceMap.size());
		System.out.println("Length of DB list = " + dbMap.size());
		System.out.println("Length of Combined list = " + practiceIds.size());

		practiceIds.forEach(practiceId -> {
			if (!PricingUtil.isTestCustomer(practiceId)) {
				String invoiceStatus = invoiceMap.get(practiceId);
				String dbStatus = dbMap.get(practiceId);

				if (!StringUtils.equalsIgnoreCase(dbStatus, invoiceStatus)) {
					System.out.println("");
					System.out.println("");
					System.out.println("Practice Id :" + practiceId);
					System.out.println("Invoice Status :" + invoiceStatus);
					System.out.println("DB status :" + dbStatus);
				}
			}
		});

	}
}
