package com.zoho.charm.project.pricing.invoice;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.text.MessageFormat;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import com.zoho.charm.project.pricing.Customers;
import com.zoho.charm.project.utils.CommonUtils;

public class GetOutstandingInInvoice {
	static BufferedWriter writer = null;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {

		try {
			writer = new BufferedWriter(new FileWriter(CommonUtils.INVOICE_HOME_DIR.concat("InvoicesOutstanding.txt")));

			JSONObject object = Customers.customers;

			object.keys().forEachRemaining(practiceId -> {
				try {
					String customerId = object.getString(practiceId.toString());
					String outStanding = getOutStanding(customerId);

					writer.append(System.lineSeparator());
					writer.append(practiceId.toString());
					writer.append(",");
					writer.append(outStanding);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			writer.flush();
			writer.close();

		}
	}

	static String contacts_url = "https://invoice.zoho.com/api/v3/contacts/{0}";

	public static String getOutStanding(String customerId) throws Exception {
		String outstanding = "";

		String url = MessageFormat.format(contacts_url, customerId);

		JSONObject result = new JSONObject(performGetRequest(url));

		if (result != null && result.has("contact")) {
			outstanding = result.getJSONObject("contact").getString("outstanding_receivable_amount");
		}
		return outstanding;
	}

	static Integer count = 0;
	static Long refreshTime = 0L;

	static String accountsUrl = "https://accounts.zoho.com/oauth/v2/token";
	static String clientId = "1000.PQJQJHJECN8K36035U48Q1NAMPB93H";
	static String clientSecretId = "eb0a7d169c32c1cd467d43c24dbd7fb7b223b8f58d";
	static String redirectUri = "http://aravind-5939.csez.zohocorpin.com:8080/login/MigrateData.jsp";
	static String refreshToken = "1000.d8473b4f139ad1df384d1a9135b67795.31c148c492f590d2e0a6fa86927de366";
	static String accessToken = "";

	public static String performGetRequest(String url) throws Exception {

		if (System.currentTimeMillis() - refreshTime > 3500000) {
			JSONObject object = new JSONObject(CommonUtils
					.getAuthTokenUsingRefreshToken(accountsUrl, refreshToken, clientId, clientSecretId, redirectUri)
					.toString());
			accessToken = "Zoho-oauthtoken ".concat(object.getString(CommonUtils.ACCESS_TOKEN));
			refreshTime = System.currentTimeMillis();
		}

		HttpClient client = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet(url);
		System.out.println(count++);
		System.out.println(getRequest.getURI());
		String response1 = "";
		getRequest.setHeader("Authorization", accessToken);
		getRequest.setHeader("X-com-zoho-invoice-organizationid", "7123525");
		getRequest.setHeader("Content-Type", "application/json");

		try {
			HttpResponse response = client.execute(getRequest);
			System.out.println(response);
			StatusLine statusLine = response.getStatusLine();
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			response1 = IOUtils.toString(rd);

			Thread.sleep(1000);

			System.out.println(response1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response1;
	}
}
