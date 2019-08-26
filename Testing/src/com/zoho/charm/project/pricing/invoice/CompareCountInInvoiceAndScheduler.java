package com.zoho.charm.project.pricing.invoice;

import java.text.MessageFormat;
import java.util.HashMap;

import com.zoho.charm.project.pricing.Customers;
import com.zoho.charm.project.utils.CommonUtils;

public class CompareCountInInvoiceAndScheduler {

	public static void main(String[] args) {
		HashMap<String, Double> invoiceFile = CommonUtils
				.getHashMapFromFile(CommonUtils.INVOICE_HOME_DIR.concat("InvoicesOutstanding.csv"), 0, 1, ",");

		HashMap<String, Double> dbFile = CommonUtils
				.getHashMapFromFile(CommonUtils.PRICING_HOME_DIR.concat("PaypalLogs_August.csv"), 0, 2, ",");
		
		HashMap<String, Double> monthlyFile = CommonUtils
				.getHashMapFromFile(CommonUtils.PRICING_HOME_DIR.concat("Usage_july_2019_New.csv"), 0,16, ",");

		dbFile.keySet().forEach(practiceId -> {
			try {
				if (!Customers.isTestCustomer(practiceId)) {
					Double count = dbFile.get(practiceId);
					String customerId = Customers.getCustomerId(practiceId);

					if (customerId != null && invoiceFile.containsKey(practiceId)) {
						Double invoiceCount = invoiceFile.get(practiceId) + monthlyFile.get(practiceId);
						if(!count.equals(invoiceCount) && !(invoiceCount == 0 && count == 0.5D)) {
							System.out.println(MessageFormat.format("Practice Id : {0},Customer Id:{3},\n Invoice Cost :{1}, DB Cost : {2}",practiceId,invoiceCount,count,customerId));
						}
					}else if(count > 50) {
						//System.out.println(MessageFormat.format("Practice Id : {0},DB Cost : {1},Customer Id : {2}",practiceId,count,customerId));
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
}
