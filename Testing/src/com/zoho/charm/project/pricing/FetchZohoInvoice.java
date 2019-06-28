package com.zoho.charm.project.pricing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import com.zoho.charm.project.utils.CommonUtils;

public class FetchZohoInvoice {
	public static void main(String[] args) throws Exception {
		String url = "https://invoice.zoho.com/api/v3/invoices?invoice_number={0}";
		String contentType = "multipart/form-data";
		String oauthToken = "Zoho-oauthtoken 1000.0a4fd9b023312bcb8a7fc063fc275c09.a6e795794f66ca8a1a5e8beefdc557d4";
		List<String> practiceIds = getInvoiceIds();
		Integer count = 0;
		HttpClient client = new DefaultHttpClient();
		HttpResponse response = null;
		BufferedWriter writer = new BufferedWriter(
				new FileWriter(CommonUtils.INVOICE_HOME_DIR.concat("InvoicesStatus.txt")));
		Scanner scanner = new Scanner(System.in);

		ArrayList<String>skippedList = new ArrayList<>();
		
		for (String practiceId : practiceIds) {
			response = null;
			String invoiceId = getInvoiceNumberForPracticeId(practiceId, "05", "2019");
			String url1 = MessageFormat.format(url, invoiceId);
			HttpGet getRequest = new HttpGet(url1);
			System.out.println(count++);
			System.out.println(getRequest.getURI());

			getRequest.setHeader("Authorization", oauthToken);
			getRequest.setHeader("X-com-zoho-invoice-organizationid", "7123525");
			getRequest.setHeader("Content-Type", contentType);

			try {
				response = client.execute(getRequest);
				System.out.println(response);
				StatusLine statusLine = response.getStatusLine();
				BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				String response1 = IOUtils.toString(rd);
				if (statusLine.getStatusCode() == 401) {
					System.out.println("AuthToken Expired. Please enter a new one :");
					oauthToken = "Zoho-oauthtoken ".concat(scanner.nextLine());
					skippedList.add(practiceId);
					continue;
				}

				Thread.sleep(1000);

				System.out.println(response1);
				JSONObject obj = new JSONObject(response1);
				if(!obj.getString("message").equals("success")) {
					System.out.println("AuthToken Expired. Please enter a new one :");
					oauthToken = "Zoho-oauthtoken ".concat(scanner.nextLine());
					skippedList.add(practiceId);
					continue;
				}
				if (obj.has("invoices")) {
					JSONArray invoices = obj.getJSONArray("invoices");

					if (invoices.length() > 0) {
						JSONObject invoice = invoices.getJSONObject(0);
						writer.write(System.lineSeparator());
						writer.write(practiceId);
						writer.write(",");
						writer.write(invoiceId);
						writer.write(",");
						writer.write(invoice.get("status").toString());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (count % 10 == 0) {
				System.out.println("Sleeping for 10 seconds");
				//Thread.sleep(10000);
			}
		}
		System.out.println("\n\nSkipped list");
		skippedList.forEach(practiceId ->{
			System.out.println(practiceId);
		});
		try {
			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static List<String> getInvoiceIds() {

		List<String> practiceIds = new ArrayList<>();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(CommonUtils.INVOICE_HOME_DIR.concat("PracticesList.txt")));
			String line = reader.readLine();
			while (line != null) {
				if (StringUtils.isNotEmpty(line.trim())) {
					practiceIds.add(line.trim());
				}
				line = reader.readLine();

			}
		} catch (Exception e) {
			System.out.println("File not found\n");
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
				}
			}
		}

		return practiceIds;

	}

	static String invString = "MM-INV-${0}-{1}{2}-01";

	public static String getInvoiceNumberForPracticeId(String practiceId, String month, String year) throws Exception {
		//
		String str = practiceId.substring(3).replaceFirst("^0+(?!$)", "");
		int len = str.length();
		if (len < 7) {
			//
			for (int i = len; i < 7; i++) {
				str = "0" + str;
			}

		}

		String invNumber = invString.replace("${0}", str);
		invNumber = invNumber.replace("{1}", month);
		invNumber = invNumber.replace("{2}", year);

		return invNumber;
	}
}
