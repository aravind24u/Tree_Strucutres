
package payments;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVStrategy;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ZInvoices {
	//
	static String organizationId = "7123525";
	static String refreshToken = "1000.d8473b4f139ad1df384d1a9135b67795.31c148c492f590d2e0a6fa86927de366";
	static String accessToken = "";
	static Long refreshTime = 0L;
	static String invoiceURL = "https://invoice.zoho.com/api/v3/";
	static String invString = "MM-INV-${0}-{1}{2}-01";
	static String cnString = "MM-CN-${0}-{1}-01";
	static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	public static final SimpleDateFormat apiFormat = new SimpleDateFormat("yyyy-MM-dd");
	static String encounterItemId = "201558000000210115", smsItemId = "201558000000429143",
			faxItemId = "201558000000825003", faxAdditionalItemId = "201558000001114005",
			scanMonthlyItemId = "201558000001134001", scanYearlyItemId = "201558000001134009",
			eClaimsPackageId = "201558000001611049", eRxFeeItemId = "201558000002106369",
			videoConsultsItemId = "201558000001795013", flexiChargeItemId = "201558000007819897",
			eCommerceItemId = "201558000006381395", providerItemId = "201558000007807061";
	private static DecimalFormat dFormat = new DecimalFormat("0.00");
	public static HashMap<String, String> dayMap = new HashMap<String, String>();

	static String accountsUrl = "https://accounts.zoho.com/oauth/v2/token";
	static String clientId = "1000.PQJQJHJECN8K36035U48Q1NAMPB93H";
	static String clientSecretId = "eb0a7d169c32c1cd467d43c24dbd7fb7b223b8f58d";
	static String redirectUri = "http://aravind-5939.csez.zohocorpin.com:8080/login/MigrateData.jsp";

	static {
		dayMap.put("January", "01");
		dayMap.put("February", "02");
		dayMap.put("March", "03");
		dayMap.put("April", "04");
		dayMap.put("May", "05");
		dayMap.put("June", "06");
		dayMap.put("July", "07");
		dayMap.put("August", "08");
		dayMap.put("September", "09");
		dayMap.put("October", "10");
		dayMap.put("November", "11");
		dayMap.put("December", "12");
	}

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
		invNumber = invNumber.replace("{1}", dayMap.get(month));
		invNumber = invNumber.replace("{2}", year);

		return invNumber;
	}

	//
	public static JSONObject getInvoiceData(String invoiceNumber) throws Exception {
		//
		String url = invoiceURL + "invoices?&invoice_number=" + invoiceNumber;
		JSONObject json = JSONObject.fromObject(getResponse("GET", url, null, null));

		if (json.containsKey("code") && "0".equals(json.getString("code"))) {
			//
			JSONArray jArr = json.getJSONArray("invoices");
			if (jArr.size() > 0) {
				return jArr.getJSONObject(0);
			}
		}

		return new JSONObject();

	}

	public static JSONObject getInvoiceDetails(String invoiceId) throws Exception {
		//
		String url = invoiceURL + "invoices/" + invoiceId + "?accept=json";
		JSONObject json = JSONObject.fromObject(getResponse("GET", url, null, null));

		if (json.containsKey("code") && "0".equals(json.getString("code"))) {
			//
			return json.getJSONObject("invoice");
		}

		return new JSONObject();

	}

	public static JSONObject getCustomerData(String customerId) throws Exception {
		//
		String url = invoiceURL + "contacts/" + customerId + "?";
		JSONObject json = JSONObject.fromObject(getResponse("GET", url, null, null));

		if (json.containsKey("code") && "0".equals(json.getString("code"))) {
			//
			JSONObject jArr = json.getJSONObject("contact");
			if (jArr.size() > 0) {
				return jArr;
			}
		}

		return new JSONObject();

	}

	private static String getResponse(String method, String u, String jsonstring, String item) throws Exception {
		//

		if (System.currentTimeMillis() - refreshTime > 3500000) {
			JSONObject object = CommonUtil.getAuthTokenUsingRefreshToken(accountsUrl, refreshToken, clientId,
					clientSecretId, redirectUri);
			accessToken = "Zoho-oauthtoken ".concat(object.getString(CommonUtil.ACCESS_TOKEN));
			refreshTime = System.currentTimeMillis();
		}

		if ("GET".equals(method)) {
			//
			u += "&" + String.format("&organization_id=%s&", organizationId);

		}

		URL url = new URL(u);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(method);
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		connection.setRequestProperty("charset", "utf-8");
		connection.setDoOutput(true);
		connection.setConnectTimeout(6000);
		connection.setRequestProperty("Authorization", accessToken);

		if (!"GET".equals(method)) {
			//
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			String query = String.format("&organization_id=%s&JSONString=%s&", organizationId, jsonstring);

			if ((item != null) && (("invoice".equals(item) || "creditnote".equals(item)))) {
				//
				query += "send=false&ignore_auto_number_generation=true&";

			}

			// System.out.println(query);
			out.writeBytes(query);
			out.close();
		}

		int status = connection.getResponseCode();
		StringBuilder response = new StringBuilder();
		if (status != 400) {

			if (status != 200 && status != 201) {
				System.out.println("Response Code : " + connection.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				response.append(line);
			}
		} else {

			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
			String line;
			while ((line = br.readLine()) != null) {
				response.append(line);
			}

			System.out.println("Error:" + response.toString());

			throw new Exception(response.toString());

		}
		connection.disconnect();
		return response.toString();
	}

	public static String getTwoDecimalValues(String strVal) throws Exception {
		//
		if (strVal != null && !strVal.equals(null)) {
			//
			Double val = new Double(strVal);
			String retVal = dFormat.format(val).toString();
			return retVal.equals("-0.00") ? "0.00" : retVal;

		}
		return "0.00";
	}

	public static JSONObject createPayment(JSONObject json) throws Exception {
		//
		// System.out.println("JSON :" + json);

		String url = invoiceURL + "customerpayments?";

		return JSONObject.fromObject(getResponse("POST", url, json.toString(), null));
	}

	public static JSONObject createInvoice(JSONObject json) throws Exception {

		String url = invoiceURL + "invoices?";

		return JSONObject.fromObject(getResponse("POST", url, json.toString(), "invoice"));
	}

	public static JSONObject updateInvoice(JSONObject json, String invoiceId) throws Exception {
		//
		// System.out.println("JSON :" + json);

		String url = invoiceURL + "invoices/" + invoiceId + "?";

		return JSONObject.fromObject(getResponse("PUT", url, json.toString(), "invoice"));
	}

	public static JSONObject createCreditNote(JSONObject json) throws Exception {
		//
		// System.out.println("JSON :" + json);

		String url = invoiceURL + "creditnotes?";

		return JSONObject.fromObject(getResponse("POST", url, json.toString(), "creditnote"));
	}

	private static JSONArray generateInvoiceItems(int encounterCount, Float encounterCharge, Integer smsCount,Float smsCharge,
			boolean isFaxEnabled, Integer faxCount, Float faxCharge, Float scanCharge, Integer eClaimsUsageCount,
			Integer eRxUserCount, Integer noOfVideoProviders, Float videoConsultCharges, Integer ecommerceCount, String ecommerceCost,
			Float planCost, Integer providerCount, Float providerCost, Integer dpmCount, Float dpmCost, Integer externalDevicesCount, Float externalDevicesCost) throws Exception {
		//
		JSONArray json = new JSONArray();

		Float encounterDiscount = 0f;
		if (planCost > 0) {
			JSONObject object = new JSONObject();
			object.put("item_id", flexiChargeItemId);
			object.put("quantity", "1");
			object.put("rate", planCost.toString());
			object.put("discount", "0.00");

			encounterDiscount = planCost;

			json.add(object);
		}

		if (encounterCharge > 0) {
			//
			String encCharge50 = getTwoDecimalValues((encounterCount * 0.50) + "");
			String encCharge30 = getTwoDecimalValues((encounterCount * 0.30) + "");
			String encCharge25 = getTwoDecimalValues((encounterCount * 0.25) + "");
			String encounterChargeStr = getTwoDecimalValues(encounterCharge + encounterDiscount + "");
			if (encCharge50.equals(encounterChargeStr)) {
				JSONObject encObj = new JSONObject();
				encObj.put("item_id", encounterItemId);
				encObj.put("quantity", getTwoDecimalValues(encounterCount + ""));
				encObj.put("rate", "0.50");
				encObj.put("discount", encounterDiscount.toString());

				json.add(encObj);

			} else if (encCharge30.equals(encounterChargeStr)) {
				JSONObject encObj = new JSONObject();
				encObj.put("item_id", encounterItemId);
				encObj.put("quantity", getTwoDecimalValues(encounterCount + ""));
				encObj.put("rate", "0.30");
				encObj.put("discount", encounterDiscount.toString());

				json.add(encObj);
			} else if (encCharge25.equals(encounterChargeStr)) {
				JSONObject encObj = new JSONObject();
				encObj.put("item_id", encounterItemId);
				encObj.put("quantity", getTwoDecimalValues(encounterCount + ""));
				encObj.put("rate", "0.25");
				encObj.put("discount", encounterDiscount.toString());

				json.add(encObj);
			} else if (encounterCount > 2000) {
				JSONObject encObj = new JSONObject();
				encObj.put("item_id", encounterItemId);
				encObj.put("quantity", getTwoDecimalValues(2000 + ""));
				encObj.put("rate", "0.50");
				encObj.put("discount", encounterDiscount.toString());

				json.add(encObj);

				JSONObject encObj1 = new JSONObject();
				encObj1.put("item_id", encounterItemId);
				encObj1.put("quantity", getTwoDecimalValues((encounterCount - 2000) + ""));
				encObj1.put("rate", "0.30");
				encObj1.put("discount", encounterDiscount.toString());
				json.add(encObj1);

			}

		}

		if (isFaxEnabled) {
			//
			JSONObject faxObj = new JSONObject();
			faxObj.put("item_id", faxItemId);

			if (faxCount <= 200) {
				//
				faxObj.put("rate", getTwoDecimalValues(faxCharge + ""));
				faxObj.put("quantity", "1.00");
				faxObj.put("discount", "0.00");

			} else {
				//
				Float cAmount = faxCount * 0.10f;
				if ((faxCharge - cAmount) > 1) {
					faxObj.put("rate", "25.00");
				} else {
					faxObj.put("rate", "20.00");
				}
				faxObj.put("quantity", "1.00");
				faxObj.put("discount", "0.00");

			}

			json.add(faxObj);

			if ((faxCount != null) && (faxCount > 200)) {
				//
				faxCount = faxCount - 200;
				//
				JSONObject faxObjAdd = new JSONObject();
				faxObjAdd.put("item_id", faxAdditionalItemId);
				faxObjAdd.put("rate", "0.10");
				faxObjAdd.put("quantity", getTwoDecimalValues(faxCount + ""));
				faxObjAdd.put("discount", "0.00");

				json.add(faxObjAdd);

			}

		}

		if (smsCharge > 0) {
			//
			JSONObject smsObj = new JSONObject();
			smsObj.put("item_id", smsItemId);

			if (smsCount <= 250) {
				//
				smsObj.put("rate", "20.00");
				smsObj.put("quantity", "1.00");
				smsObj.put("discount", "0.00");

			} else {
				//
				smsObj.put("rate", "0.08");
				smsObj.put("quantity", getTwoDecimalValues(smsCount + ""));
				smsObj.put("discount", "0.00");

			}

			json.add(smsObj);

		}

		if (scanCharge != null && scanCharge > 0) {
			//
			JSONObject scanObj = new JSONObject();

			if (scanCharge == 10.00f) {
				//
				scanObj.put("item_id", scanMonthlyItemId);
			} else {
				//
				scanObj.put("item_id", scanYearlyItemId);
			}
			scanObj.put("rate", getTwoDecimalValues(scanCharge + ""));
			scanObj.put("quantity", "1.00");
			scanObj.put("discount", "0.00");

			json.add(scanObj);

		}

		if ((eClaimsUsageCount != null) && (eClaimsUsageCount > 0)) {
			//
			JSONObject eClaimsObj = new JSONObject();
			eClaimsObj.put("item_id", eClaimsPackageId);
			eClaimsObj.put("quantity", getTwoDecimalValues(eClaimsUsageCount + ""));
			eClaimsObj.put("rate", "0.25");
			eClaimsObj.put("discount", "0.00");

			json.add(eClaimsObj);

		}

		if ((eRxUserCount != null) && (eRxUserCount > 0)) {
			//
			JSONObject eRxObj = new JSONObject();
			eRxObj.put("item_id", eRxFeeItemId);
			eRxObj.put("quantity", getTwoDecimalValues(eRxUserCount + ""));
			eRxObj.put("rate", "10.00");
			eRxObj.put("discount", "0.00");

			json.add(eRxObj);

		}

		if (videoConsultCharges > 0) {
			//
			JSONObject vObj = new JSONObject();
			vObj.put("item_id", videoConsultsItemId);
			Float rate = videoConsultCharges / noOfVideoProviders;
			vObj.put("rate", getTwoDecimalValues(rate.toString()));
			vObj.put("quantity", getTwoDecimalValues(noOfVideoProviders + ""));
			vObj.put("discount", "0.00");

			json.add(vObj);

		}

		if (new Float(ecommerceCost) > 0) {

			JSONObject eCommerceObj = new JSONObject();
			eCommerceObj.put("item_id", eCommerceItemId);

			if (ecommerceCount > 0) {
				if (ecommerceCount <= 2000) {
					eCommerceObj.put("rate", ecommerceCost);
					eCommerceObj.put("quantity", "1");
				} else {
					eCommerceObj.put("rate", ((Float) (new Float(ecommerceCost) / ecommerceCount)).toString());
					eCommerceObj.put("quantity", getTwoDecimalValues(ecommerceCount + ""));
				}

			} else {
				eCommerceObj.put("rate", ecommerceCost);
				eCommerceObj.put("quantity", "1");
			}

			eCommerceObj.put("discount", "0.00");

			json.add(eCommerceObj);
		}

		if (providerCost > 0) {
			JSONObject providerObj = new JSONObject();
			providerObj.put("item_id", providerItemId);
			providerObj.put("rate", String.valueOf(providerCost / providerCount));
			providerObj.put("quantity", getTwoDecimalValues(providerCount + ""));
			providerObj.put("discount", "0.00");

			json.add(providerObj);
		}
		
		if(dpmCost > 0) {
			JSONObject dpmObj = new JSONObject();
			dpmObj.put("item_id", "201558000008888010");
			dpmObj.put("rate", String.valueOf(dpmCost / dpmCount));
			dpmObj.put("quantity", getTwoDecimalValues(dpmCount + ""));
			dpmObj.put("discount", "0.00");

			json.add(dpmObj);	
		}
		
		if(externalDevicesCost > 0) {
			JSONObject dpmObj = new JSONObject();
			dpmObj.put("item_id", "201558000008888001");
			dpmObj.put("rate", String.valueOf(externalDevicesCost / externalDevicesCount));
			dpmObj.put("quantity", getTwoDecimalValues(externalDevicesCount + ""));
			dpmObj.put("discount", "0.00");

			json.add(dpmObj);	
		}
		

		return json;
	}

	private static JSONArray generateCreditNoteItems(int encounterCount, float encounterCharge, Integer smsCount,
			Integer faxCount) throws Exception {
		//
		JSONArray json = new JSONArray();

		if (encounterCount > 0) {
			//
			String encCharge50 = getTwoDecimalValues((encounterCount * 0.50) + "");
			String encCharge30 = getTwoDecimalValues((encounterCount * 0.30) + "");
			String encCharge25 = getTwoDecimalValues((encounterCount * 0.25) + "");
			String encounterChargeStr = getTwoDecimalValues(encounterCharge + "");
			if (encCharge50.equals(encounterChargeStr)) {
				JSONObject encObj = new JSONObject();
				encObj.put("item_id", encounterItemId);
				encObj.put("quantity", getTwoDecimalValues(encounterCount + ""));
				encObj.put("rate", "0.50");
				encObj.put("discount", "0.00");

				json.add(encObj);

			} else if (encCharge30.equals(encounterChargeStr)) {
				JSONObject encObj = new JSONObject();
				encObj.put("item_id", encounterItemId);
				encObj.put("quantity", getTwoDecimalValues(encounterCount + ""));
				encObj.put("rate", "0.30");
				encObj.put("discount", "0.00");

				json.add(encObj);
			} else if (encCharge25.equals(encounterChargeStr)) {
				JSONObject encObj = new JSONObject();
				encObj.put("item_id", encounterItemId);
				encObj.put("quantity", getTwoDecimalValues(encounterCount + ""));
				encObj.put("rate", "0.25");
				encObj.put("discount", "0.00");

				json.add(encObj);
			} else if (encounterCount > 2000) {
				JSONObject encObj = new JSONObject();
				encObj.put("item_id", encounterItemId);
				encObj.put("quantity", getTwoDecimalValues(2000 + ""));
				encObj.put("rate", "0.50");
				encObj.put("discount", "0.00");

				json.add(encObj);

				JSONObject encObj1 = new JSONObject();
				encObj1.put("item_id", encounterItemId);
				encObj1.put("quantity", getTwoDecimalValues((encounterCount - 2000) + ""));
				encObj1.put("rate", "0.30");
				encObj1.put("discount", "0.00");
				json.add(encObj1);

			}

		}

		if ((faxCount != null) && (faxCount > 0)) {
			//
			JSONObject faxObjAdd = new JSONObject();
			faxObjAdd.put("item_id", faxAdditionalItemId);
			faxObjAdd.put("rate", "0.10");
			faxObjAdd.put("quantity", getTwoDecimalValues(faxCount + ""));
			faxObjAdd.put("discount", "0.00");

			json.add(faxObjAdd);
		}

		if (smsCount > 0) {
			//
			JSONObject smsObj = new JSONObject();
			smsObj.put("item_id", smsItemId);
			smsObj.put("rate", "0.08");
			smsObj.put("quantity", getTwoDecimalValues(smsCount + ""));
			smsObj.put("discount", "0.00");

			json.add(smsObj);

		}

		return json;
	}

	@SuppressWarnings("unchecked")
	public static void generateInvoices(String invoiceDate) throws Exception {
		//
		Customers customers = new Customers();
		CSVParser parser = new CSVParser(new InputStreamReader(new FileInputStream(new File(CommonUtil.usageCSV))));
		String[][] values = parser.getAllValues();
		int i = 0;
		StringBuilder issueIds = new StringBuilder();
		for (String[] row : values) {
			//
			String practiceId = row[0];
			try {// 257000013456041,257000000516027,257000000481033
				if (!customers.isTestCustomer(practiceId)) {
					//
					String customerId = customers.getCustomerId(practiceId);
					Float monthlyCharge = new Float(row[29]);
					System.out.println("Customer ID :" + customerId);
					if (!"".equals(customerId) && monthlyCharge > 0) {
						//
						String month = row[1];
						String year = row[2];
						Integer encCount = Integer.valueOf(row[22]) > 0 ? Integer.valueOf(row[22])
								: Integer.valueOf(row[3]);
						String encounterChargeStr = new Float(row[23]) == 0f ? row[4] : row[23];
						Integer smsCount = Integer.valueOf(row[5]);
						String smsChargeStr = row[6];
						String faxChargeStr = row[8];
						Float encounterCharge = Float.valueOf(encounterChargeStr);
						Float smsCharge = Float.valueOf(smsChargeStr);
						Float faxCharge = 0.0f;
						if (!faxChargeStr.equals("null")) {
							faxCharge = Float.valueOf(faxChargeStr);
						}
						Float scanCharge = null;
						String scanChargeStr = row[9];
						Integer noOfVideoProviders = Integer.valueOf(row[10]);
						Float videoConsultChage = Float.parseFloat(row[11]);
						Integer eClaimsPackageCount = Integer.valueOf(row[12]);
						Integer eRxUserCount = Integer.valueOf(row[14]);
						boolean isFaxEnabled = new Float(row[7]) > 0 || faxCharge > 0;
						Integer faxCount = 0;

						Integer ecommerceCount = Integer.parseInt(row[16]);
						String ecommerceCost = row[17];

						Float planCost = new Float(row[28]);

						Integer providerCount = new Integer(row[18]);
						Float providerCost = new Float(row[19]);
						
						Integer dpmCount = Integer.parseInt(row[24]);
						Float dpmCost = Float.parseFloat(row[25]);
						
						Integer externalDevicesCount = Integer.parseInt(row[26]);
						Float externalDevicesCost = Float.parseFloat(row[27]);

						String invNumber = getInvoiceNumberForPracticeId(practiceId, month, year);
						if(!StringUtils.equals(invNumber ,row[30])) {
							issueIds.append(practiceId);
							issueIds.append(System.lineSeparator());
						}

						if (isFaxEnabled) {
							//
							faxCount = Integer.valueOf(row[7].trim());

						}

						if ((smsCount == 0) && (smsCharge > 0)) {
							//
							smsCount = 1;

						}

						if (smsCharge > 20) {
							//
							String str = (smsCharge / 0.08f) + "";
							smsCount = Integer.valueOf(str.split("\\.")[0]);

						}

						if (!"0.0".equals(scanChargeStr)) {
							//
							scanCharge = Float.valueOf(scanChargeStr);

						}

						JSONObject invObj = new JSONObject();
						invObj.put("customer_id", customerId);
						invObj.put("invoice_number", invNumber);
						invObj.put("date", invoiceDate);
						invObj.put("due_date", invoiceDate);
						invObj.put("exchange_rate", "1.00");

						StringBuilder note = new StringBuilder(
								"Thank you for using ChARM EHR. All amounts are in US $ only. ");

						if (encCount > 0) {
							//
							note.append("Total number of consults in " + month + " is " + encCount + ". ");

						}

						if (smsCount > 1) {
							//
							note.append("Total number of SMS/Voice SMS sent in " + month + " is " + smsCount + ". ");

						}

						if (faxCount > 0) {
							//
							note.append(" Number of FAX sent/received in " + month + " is " + faxCount + ". ");

						}

						if (eClaimsPackageCount > 0) {
							//
							note.append("Total number of e-claims submitted in " + month + " is " + eClaimsPackageCount
									+ ". ");

						}

						if (eRxUserCount > 0) {
							note.append("Number of eRx Users in " + month + " is " + eRxUserCount + ". ");
						}
						if (ecommerceCount > 0) {
							note.append("Total Sales of Ecommerce is " + month + " is " + ecommerceCount + ". ");
						}

						invObj.put("notes", note.toString());

						invObj.put("terms",
								"ChARM EHR is Saas based monthly subscription service. It follows Use and Pay model. The subscription for each month is collected at the start of subsequent month.");

						invObj.put("line_items",
								ZInvoices.generateInvoiceItems(encCount, encounterCharge, smsCount,smsCharge ,isFaxEnabled,
										faxCount, faxCharge, scanCharge, eClaimsPackageCount, eRxUserCount,
										noOfVideoProviders,videoConsultChage, ecommerceCount, ecommerceCost, planCost, providerCount,
										providerCost,dpmCount,dpmCost,externalDevicesCount,externalDevicesCost));

						System.out.println("Creating Invoice for Practice Id :" + practiceId);
						ZInvoices.createInvoice(invObj);
						System.out.println("Invoice created for Practice Id :" + practiceId);

						i++;

						if (i % 10 == 0) {
							System.out.println("Wating...");
							Thread.sleep(5000);
						}

					} else {
						//
						System.out.println("Practice Id :" + practiceId);

					}
				} else {
					System.out.println("Test Practice Id :" + practiceId);
				}
			} catch (Exception e) {
				System.out.println("Issue while Creating Invoice for Practice : " + practiceId);
				jsonObject.put(practiceId, e);
			}

		}
		jsonObject.keys().forEachRemaining(practiceId -> {
			System.out.println("Practice Id : " + practiceId);
			try {
				((Exception) jsonObject.get(practiceId.toString())).printStackTrace();
				;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		});
		System.out.println(issueIds.toString());
	}

	public static void generateCreditNotes() throws Exception {
		//
		Customers customers = new Customers();
		CSVParser parser = new CSVParser(new InputStreamReader(new FileInputStream(new File(CommonUtil.usageCSV))));
		String[][] values = parser.getAllValues();
		int i = 0;
		for (String[] row : values) {
			//
			String practiceId = row[0];
			if (!customers.isTestCustomer(practiceId)) {
				//
				String customerId = customers.getCustomerId(practiceId);
				if (!"".equals(customerId)) {
					//
					String date = "022018";
					Integer encCount = Integer.valueOf(row[1]);
					Integer smsCount = Integer.valueOf(row[2]);
					Integer faxCount = Integer.valueOf(row[3]);

					String cnNumber = cnString.replace("${0}", practiceId.substring(practiceId.length() - 7));
					cnNumber = cnNumber.replace("{1}", date);

					JSONObject invObj = new JSONObject();
					invObj.put("customer_id", customerId);
					invObj.put("creditnote_number", cnNumber);
					invObj.put("date", "2018-02-02");
					invObj.put("exchange_rate", "1.00");

					String note = "Thank you for using ChARM EHR. All amounts are in US $ only. ";
					invObj.put("notes", note);
					invObj.put("line_items", ZInvoices.generateCreditNoteItems(encCount, 0.0f, smsCount, faxCount));
					// System.out.println(practiceId + "::" + customerId + "::" + encCount + "::" +
					// smsCount + "::" + isFaxEnabled + "::" + faxCount + "::" + faxCharge + "::" +
					// scanCharge + "::" + eClaimsPackageCount + "::" + eRxUserCount);
					System.out.println("Creating CN for Practice Id :" + practiceId);
					ZInvoices.createCreditNote(invObj);
					System.out.println("CN created for Practice Id :" + practiceId);

					i++;

					if (i % 10 == 0) {
						//
						try {
							System.out.println("Wating...");
							Thread.sleep(5000);
						} catch (InterruptedException ex) {
							// do stuff
						}

					}

				} else {
					//
					System.out.println("Practice Id :" + practiceId);

				}
			} else {
				System.out.println("Test Practice Id :" + practiceId);
			}

		}

	}

	public static void checkInvoices() throws Exception {
		//
		Customers customers = new Customers();
		CSVParser parser = new CSVParser(new InputStreamReader(new FileInputStream(new File(CommonUtil.usageCSV))));
		String[][] values = parser.getAllValues();
		int i = 0, j = 0;
		Float total = 0.0f, totalNew = 0.0f;
		for (String[] row : values) {
			//
			String practiceId = row[0];
			if (!customers.isTestCustomer(practiceId)) {
				//
				String customerId = customers.getCustomerId(practiceId);
				Float mCharge = Float.valueOf(row[6]);
				if (!"".equals(customerId)) {
					//
					total += mCharge;
					i++;
				} else {
					//
					totalNew += mCharge;
					j++;
				}
			}

		}

		System.out.println("Existing :" + i + ":" + total);
		System.out.println("New :" + j + ":" + totalNew);

	}

	public static void checkEncounterCharge() throws Exception {
		//
		CSVParser parser = new CSVParser(new InputStreamReader(new FileInputStream(new File(CommonUtil.usageCSV))));
		String[][] values = parser.getAllValues();
		Customers cus = new Customers();
		Double total = 0.0d;
		for (String[] row : values) {
			//
			String practiceId = row[0];
			if (!cus.isTestCustomer(practiceId)) {
				Integer encCount = Integer.valueOf(row[3]);
				Double encCharge = Double.valueOf(getTwoDecimalValues(row[4]));
				Double charge = Double.valueOf(getTwoDecimalValues(row[29]));
				String invNumber = getInvoiceNumberForPracticeId(practiceId, row[1], row[2]);

				total += charge;
				double cCharge = (double) encCount * 0.50;
				if (encCount >= 50) {
					if (getTwoDecimalValues(cCharge + "").equals(getTwoDecimalValues(encCharge + ""))) {

					} else {
						System.out.println("Invoice : " + invNumber + "::" + getTwoDecimalValues(cCharge + "") + "::"
								+ getTwoDecimalValues(encCharge + "") + "::" + row[29]);
					}
				}

			}

		}

		System.out.println("Total :" + getTwoDecimalValues(total + ""));

	}

	public static void checkInvoiceTotal() throws Exception {
		//
		CSVParser parser = new CSVParser(new InputStreamReader(new FileInputStream(new File(CommonUtil.usageCSV))));
		String[][] values = parser.getAllValues();
		StringBuilder sb = new StringBuilder();
		Customers cus = new Customers();
		int i = 0;
		for (String[] row : values) {
			//
			String practiceId = row[0];
			if (!cus.isTestCustomer(practiceId) && (i != 0)) {
				String amount = ZInvoices.getTwoDecimalValues(row[29]);
				String invNumber = getInvoiceNumberForPracticeId(practiceId, row[1], row[2]);
				JSONObject invData = new JSONObject();
				try {
					//
					invData = ZInvoices.getInvoiceData(invNumber);

				} catch (Exception e) {
					//
					try {
						Thread.sleep(5000);
						invData = ZInvoices.getInvoiceData(invNumber);
					} catch (Exception ex) {
						// do stuff
						System.out.println("\n\nError while retrying..." + ex.getMessage());
					}
				}

				if (invData.isEmpty()) {
					sb.append("\"" + practiceId + "\", ");
				} else {
					//
					String invoiceAmount = ZInvoices.getTwoDecimalValues(invData.getString("total"));

					if (!amount.equals(invoiceAmount)) {
						//
						System.out.println(practiceId + " : " + invNumber + " : " + amount + " : " + invoiceAmount);

					}

				}

			}

			i++;

			if (i % 20 == 0) {
				//
				try {
					System.out.println("Wating...");
					Thread.sleep(5000);
				} catch (InterruptedException ex) {
					// do stuff
				}

			}

		}

		System.out.println("\n\n\n" + sb.toString());

	}

	static org.json.JSONObject jsonObject = new org.json.JSONObject();

	public static void updateInvoices() throws Exception {
		//
		Customers customers = new Customers();
		CSVParser parser = new CSVParser(new InputStreamReader(new FileInputStream(new File(CommonUtil.usageCSV))));
		String[][] values = parser.getAllValues();
		int i = 0;
		for (String[] row : values) {
			//
			String practiceId = row[0];
			try {
				String customerId = customers.getCustomerId(practiceId);

				// System.out.println("Customer Id :" + customerId);

				if (!"".equals(customerId)) {
					//
					String month = row[1];
					String year = row[2];
					Integer encCount = Integer.valueOf(row[3]);
					String encounterChargeStr = (String) row[4];
					Integer smsCount = Integer.valueOf(row[5]);
					String smsChargeStr = (String) row[6];
					String faxChargeStr = (String) row[8];
					Float encounterCharge = Float.valueOf(encounterChargeStr);
					Float smsCharge = Float.valueOf(smsChargeStr);
					Float faxCharge = 0.0f;
					if (!faxChargeStr.equals("null")) {
						faxCharge = Float.valueOf(faxChargeStr);
					}
					Float scanCharge = null;
					String scanChargeStr = (String) row[9];
					Integer noOfVideoProviders = Integer.valueOf(row[10]);
					Float videoconsultCharges = Float.parseFloat(row[11]);
					Integer eClaimsPackageCount = Integer.valueOf(row[12]);
					Integer eRxUserCount = Integer.valueOf(row[14]);
					boolean isFaxEnabled = new Float(row[8]) > 0;
					Integer faxCount = 0;
					
					String invNumber = getInvoiceNumberForPracticeId(practiceId, month, year);

					JSONObject invData = new JSONObject();
					try {
						invData = ZInvoices.getInvoiceData(invNumber);
					} catch (Exception e) {
						//
						System.out.println("\n\nException :" + e.getMessage());
						try {
							System.out.println("\n\nConnection timeout... Wating...");
							Thread.sleep(5000);
							invData = ZInvoices.getInvoiceData(invNumber);

						} catch (Exception ex) {
							// do stuff
							System.out.println("\n\nError while retrying..." + ex.getMessage());

						}
					}

					String invoiceId = invData.get("invoice_id") + "";
					String invoiceStatus = invData.get("status") + "";

					if (isFaxEnabled) {
						//
						faxCount = Integer.valueOf(row[7].trim());

					}

					if ((smsCount == 0) && (smsCharge > 0)) {
						//
						smsCount = 1;

					}

					if (smsCharge > 20) {
						//
						String str = (smsCharge / 0.08f) + "";
						smsCount = Integer.valueOf(str.split("\\.")[0]);

					}

					if (!"0.0".equals(scanChargeStr)) {
						//
						scanCharge = Float.valueOf(scanChargeStr);

					}

					JSONObject invObj = new JSONObject();
					invObj.put("customer_id", customerId);
					invObj.put("invoice_number", invNumber);
					invObj.put("date", "2017-01-02");
					invObj.put("due_date", "2017-01-02");
					invObj.put("exchange_rate", "1.00");

					String note = "Thank you for using ChARM EHR. All amounts are in US $ only. ";

					if (encCount > 0) {
						//
						note += "Total number of consults in " + month + " is " + encCount + ". ";

					}

					if (smsCount > 1) {
						//
						note += "Total number of SMS/Voice SMS sent in " + month + " is " + smsCount + ". ";

					}

					if (faxCount > 0) {
						//
						note += "Total number of FAX sent/received in " + month + " is " + faxCount + ". ";

					}

					if (eClaimsPackageCount > 0) {
						//
						note += "Total number of e-claims submitted in " + month + " is " + eClaimsPackageCount + ". ";

					}

					if (eRxUserCount > 0) {
						note += "Number of eRx Users in " + month + " is " + eRxUserCount + ". ";
					}

					invObj.put("notes", note);

					invObj.put("terms",
							"ChARM EHR is Saas based monthly subscription service. It follows Use and Pay model. The subscription for each month is collected at the start of subsequent month.");

					invObj.put("line_items",
							ZInvoices.generateInvoiceItems(encCount, encounterCharge, smsCount,smsCharge, isFaxEnabled, faxCount,
									faxCharge, scanCharge, eClaimsPackageCount, eRxUserCount, noOfVideoProviders,videoconsultCharges, 0,
									"0", 0F, 0, 0F, 0, 0F, 0, 0F));

					// System.out.println(practiceId + "::" + customerId + "::" + encCount + "::" +
					// smsCount + "::" + isFaxEnabled + "::" + faxCount + "::" + invNumber + "::" +
					// invoiceId + "::" + invoiceStatus + "::" + invoiceTotal + "::" + row[6] );
					if ("draft".equals(invoiceStatus)) {
						System.out.println("Updating Invoice for Practice Id :" + practiceId);
						ZInvoices.updateInvoice(invObj, invoiceId);
						// ZInvoices.createInvoice(invObj);
						System.out.println("Invoice updated for Practice Id :" + practiceId);
					}
					i++;
					if (i % 5 == 0) {
						//
						try {
							System.out.println("Wating...");
							Thread.sleep(5000);
						} catch (InterruptedException ex) {
							// do stuff
						}

					}

				} else {
					//
					System.out.println("Practice Id :" + practiceId);

				}
			} catch (Exception e) {
				System.out.println("Issue while Creating Invoice for Practice : " + practiceId);
				jsonObject.put(practiceId, e);
			}
		}

	}

	public static void checkPaymentData() throws Exception {
		//
		String fileName = "/Users/ramanathan-0940/Desktop/Payments.csv";
		CSVParser parser = new CSVParser(new InputStreamReader(new FileInputStream(new File(fileName))));
		String[][] values = parser.getAllValues();
		StringBuilder sb = new StringBuilder();
		Customers cus = new Customers();
		int i = 0;
		for (String[] row : values) {
			//
			String practiceId = row[4];
			if (!cus.isTestCustomer(practiceId)) {
				String amount = ZInvoices.getTwoDecimalValues(row[1]);
				// practiceId = customers.getPracticeIdForProfile(profileId);
				String invNumber = getInvoiceNumberForPracticeId(practiceId, "July", "2018");
				System.out.println("Invoice Number :" + invNumber);

				JSONObject invData = new JSONObject();
				try {
					//
					invData = ZInvoices.getInvoiceData(invNumber);

				} catch (Exception e) {
					//
					System.out.println("\n\nException :" + e.getMessage());
					try {
						System.out.println("\n\nConnection timeout... Wating...");
						Thread.sleep(5000);
						invData = ZInvoices.getInvoiceData(invNumber);

					} catch (Exception ex) {
						// do stuff
						System.out.println("\n\nError while retrying..." + ex.getMessage());

					}

				}

				if (invData.isEmpty()) {
					sb.append("\"" + practiceId + "\", ");
				} else {
					//
					String invoiceAmount = ZInvoices.getTwoDecimalValues(invData.getString("total"));

					if (!amount.equals(invoiceAmount)) {
						//
						System.out.println(practiceId + ":WITH_DUE");

					}

				}

				i++;

			}

			if (i % 10 == 0) {
				//
				try {
					System.out.println("Wating...");
					Thread.sleep(5000);
				} catch (InterruptedException ex) {
					// do stuff
				}

			}

		}

		System.out.println("\n\n\n" + sb.toString());

	}

	public static void getNewCustomers() throws Exception {
		//
		Customers customers = new Customers();
		Reader reader = new InputStreamReader(new FileInputStream(new File(CommonUtil.usageCSV)));
		CSVStrategy strategy = new CSVStrategy(',', '"', '#');
		CSVParser parser = new CSVParser(reader, strategy);
		String[][] values = parser.getAllValues();

		StringBuilder newCus = new StringBuilder();
		for (String[] row : values) {
			//
			String practiceId = row[0];

			if (!customers.isTestCustomer(practiceId)) {
				String customerId = customers.getCustomerId(practiceId);
				Double monthlyCharge = Double.parseDouble(row[29]);
				if (customerId.isEmpty() && monthlyCharge > 0) {
					//
					for (int i = 0; i < row.length; i++) {
						newCus.append(row[i]);
						newCus.append(',');
					}
					newCus.append(System.lineSeparator());
				}
			}

		}

		System.out.println(newCus.toString());

	}

	public static void cleanup() throws Exception {
		//
		String fileName = "/Users/ramanathan-0940/Desktop/Usage_May_2019.csv";
		CSVParser parser = new CSVParser(new InputStreamReader(new FileInputStream(new File(fileName))));
		String[][] values = parser.getAllValues();

		StringBuilder newCus = new StringBuilder();
		int size = 17;
		for (String[] row : values) {
			//
			String str = "";
			for (int i = 0; i < size; i++) {
				//
				String x = row[i];
				if (StringUtils.isEmpty(x)) {
					str += ",null";
				} else {
					str += "," + row[i];
				}

			}
			newCus.append(str.substring(1) + "\n");

		}

		System.out.println(newCus.toString());

		File outFile = new File(CommonUtil.usageCSV);
		if (outFile.exists()) {
			outFile.delete();
			outFile.createNewFile();
		}

		try (FileWriter file = new FileWriter(outFile)) {
			file.write(newCus.toString());
		}

	}

	public static void getTotal(boolean includeNewCustomers) throws Exception {
		//
		Customers customers = new Customers();
		CSVParser parser = new CSVParser(new InputStreamReader(new FileInputStream(new File(CommonUtil.usageCSV))));
		String[][] values = parser.getAllValues();
		double total = 0.00;
		double encTotal = 0.00;
		double smsTotal = 0.00;
		double faxTotal = 0.00;
		double scanTotal = 0.00;
		double eClaimTotal = 0.00;
		double eRxTotal = 0.00, videoTotal = 0.00;
		int encAccCount = 0, smsAccCount = 0, faxAccCount = 0, scanAccCount = 0, eClaimAccCount = 0, eRxAccCount = 0,
				videoAccCount = 0;
		int encCount = 0, smsCount = 0, faxCount = 0, eClaimCount = 0, eRxCount = 0, videoCount = 0;
		for (String[] row : values) {
			//
			String practiceId = row[0];
			String customerId = customers.getCustomerId(practiceId);
			if (((customerId != null && !customerId.isEmpty()) || includeNewCustomers)
					&& !customers.isTestCustomer(practiceId)) {
				//
				Double encCharge = Double.parseDouble(new Float(row[23]) == 0f ? row[4] : row[23]);

				if (encCharge > 0) {
					encAccCount++;
					encTotal += encCharge;
					encCount += Integer.parseInt(row[22]) > 0 ? Integer.parseInt(row[22])
							: Integer.parseInt(row[3]);
				}

				if (!"null".equals(row[6])) {
					Double smsCharge = Double.valueOf(row[6]);
					if (smsCharge > 0) {
						smsTotal += smsCharge;
						smsAccCount++;
						smsCount += Integer.valueOf(row[5]);
					}
				}

				if (!"null".equals(row[8]) && !"".equals(row[8])) {
					Double faxCharge = Double.valueOf(row[8]);
					if (faxCharge > 0) {
						faxTotal += faxCharge;
						faxAccCount++;
						faxCount += Integer.valueOf(row[7]);
					}

				}

				if (!"null".equals(row[9])) {
					Double scanCharge = Double.valueOf(row[9]);
					if (scanCharge > 0) {
						scanTotal += scanCharge;
						scanAccCount++;
					}
				}

				if (!"0".equals(row[10])) {
					Double videoCharge = Double.valueOf(row[10]) * 20;
					if (videoCharge > 0) {
						videoTotal += videoCharge;
						videoCount += Integer.parseInt(row[10]);
						videoAccCount++;
					}
				}

				if (!"null".equals(row[13])) {
					Double eClaimsCharge = Double.valueOf(row[13]);
					if (eClaimsCharge > 0) {
						eClaimTotal += eClaimsCharge;
						eClaimAccCount++;
						eClaimCount += Integer.valueOf(row[12]);
					}
				}

				if (!"null".equals(row[15])) {
					Double eRxCharge = Double.valueOf(row[15]);
					if (eRxCharge > 0) {
						eRxTotal += eRxCharge;
						eRxAccCount++;
						eRxCount += Integer.valueOf(row[14]);
					}
				}

				total += Double.valueOf(row[29]);

			}

		}

		System.out.println("Encounter Accounts : " + encAccCount);
		System.out.println("Encounter Count : " + encCount);
		System.out.println("Encounter Total : " + getTwoDecimalValues(encTotal + ""));

		System.out.println("SMS Accounts : " + smsAccCount);
		System.out.println("SMS Count : " + smsCount);
		System.out.println("SMS Total : " + getTwoDecimalValues(smsTotal + ""));

		System.out.println("FAX Accounts : " + faxAccCount);
		System.out.println("FAX Count : " + faxCount);
		System.out.println("FAX Total : " + getTwoDecimalValues(faxTotal + ""));

		System.out.println("Scan Accounts : " + scanAccCount);
		System.out.println("Scan Total : " + getTwoDecimalValues(scanTotal + ""));

		System.out.println("e-Claims Accounts : " + eClaimAccCount);
		System.out.println("e-Claims Count : " + eClaimCount);
		System.out.println("e-Claims Total : " + getTwoDecimalValues(eClaimTotal + ""));

		System.out.println("eRx Accounts : " + eRxAccCount);
		System.out.println("eRx Count : " + eRxCount);
		System.out.println("eRx Total : " + getTwoDecimalValues(eRxTotal + ""));

		System.out.println("Video Accounts : " + videoAccCount);
		System.out.println("Video Count : " + videoCount);
		System.out.println("Video Total : " + getTwoDecimalValues(videoTotal + ""));

		System.out.println("Total Monthly Charge: " + getTwoDecimalValues(total + ""));

		total = encTotal + smsTotal + faxTotal + scanTotal + eClaimTotal + eRxTotal + videoTotal;

		System.out.println("Total Line Items: " + getTwoDecimalValues(total + ""));

		System.out.println(encAccCount);
		System.out.println(encCount);
		System.out.println(getTwoDecimalValues(encTotal + ""));

		System.out.println("\n" + smsAccCount);
		System.out.println(smsCount);
		System.out.println(getTwoDecimalValues(smsTotal + ""));

		System.out.println("\n" + faxAccCount);
		System.out.println(faxCount);
		System.out.println(getTwoDecimalValues(faxTotal + ""));

		System.out.println("\n" + scanAccCount);
		System.out.println(getTwoDecimalValues(scanTotal + ""));

		System.out.println("\n" + eClaimAccCount);
		System.out.println(eClaimCount);
		System.out.println(getTwoDecimalValues(eClaimTotal + ""));

		System.out.println("\n" + eRxAccCount);
		System.out.println(eRxCount);
		System.out.println(getTwoDecimalValues(eRxTotal + ""));

		System.out.println("\n" + videoAccCount);
		System.out.println(videoCount);
		System.out.println(getTwoDecimalValues(videoTotal + ""));

	}

	public static void generateInvoiceData() throws Exception {
		//
		Customers customers = new Customers();
		CSVStrategy csvStrategy = new CSVStrategy(',', '"', '#');
		CSVParser parser = new CSVParser(new InputStreamReader(new FileInputStream(new File(CommonUtil.usageCSV))),
				csvStrategy);
		String[][] values = parser.getAllValues();
		StringBuilder header = new StringBuilder();
		StringBuilder newCus = new StringBuilder();
		int i = 0;

		for (String[] row : values) {
			//
			String practiceId = row[0];
			String customerId = customers.getCustomerId(practiceId);
			if (customerId.isEmpty() && !customers.isTestCustomer(practiceId)) {
				Double monthlyCharge = new Double(row[29]);
				if (monthlyCharge > 0) {
					//
					String invNumber = getInvoiceNumberForPracticeId(practiceId, row[1], row[2]);
					System.out.println("Invoice Number :" + invNumber);
					JSONObject invData = new JSONObject();
					try {
						invData = ZInvoices.getInvoiceData(invNumber);
					} catch (Exception e) {
						//
						System.out.println("\n\nException :" + e.getMessage());
						try {
							System.out.println("\n\nConnection timeout... Wating...");
							Thread.sleep(5000);
							invData = ZInvoices.getInvoiceData(invNumber);

						} catch (Exception ex) {
							// do stuff
							System.out.println("\n\nError while retrying..." + ex.getMessage());

						}
					}

					if (invData.isEmpty()) {
						customerId = "";
						invNumber = getInvoiceNumberForPracticeId(practiceId, "May", "2019");

						System.out.println("Invoice Number :" + invNumber);

						try {
							invData = ZInvoices.getInvoiceData(invNumber);
						} catch (Exception e) {
							//
							System.out.println("\n\nException :" + e.getMessage());
							try {
								System.out.println("\n\nConnection timeout... Wating...");
								Thread.sleep(5000);
								invData = ZInvoices.getInvoiceData(invNumber);

							} catch (Exception ex) {
								// do stuff
								System.out.println("\n\nError while retrying..." + ex.getMessage());

							}
						}

						if (invData.isEmpty()) {
							customerId = "";
						} else {
							//
							customerId = invData.get("customer_id") + "";

						}

					} else {
						//
						customerId = invData.get("customer_id") + "";

					}

					if (!customerId.isEmpty()) {
						//
						header.append("\\\"" + practiceId + "\\\":\\\"" + customerId + "\\\", ");
						customers.addCustomer(practiceId, customerId);
					} else if (!customers.isTestCustomer(practiceId)) {
						//
						newCus.append("\"" + practiceId + "\", ");
					} else {
						//
						System.out.println("Test Customer:" + practiceId);

					}

					i++;

					if (i % 5 == 0) {
						//
						try {
							System.out.println("Wating...");
							Thread.sleep(5000);
						} catch (InterruptedException ex) {
							// do stuff
						}

					}
				}
			} else {
				//
				System.out.println("Customer:" + practiceId + ":" + customerId);

			}

		}

		customers.writeCustomersToFile();

		System.out.println(header.toString());
		System.out.println("--------");
		System.out.println(newCus.toString());

	}

	public static void generatePayments(String month,String year) throws Exception {
		//
		Customers customers = new Customers();
		String fileName = CommonUtil.invoiceDir.concat("/Payments.csv");
		CSVParser parser = new CSVParser(new InputStreamReader(new FileInputStream(new File(fileName))));
		String[][] values = parser.getAllValues();

		StringBuilder sbb = new StringBuilder();
		int i = 1;
		for (String[] row : values) {
			//
			String practiceId = row[4];
			if (!customers.isTestCustomer(practiceId)) {
				//
				String transactionId = row[2];
				if (!checkifPresent(transactionId)) {
					System.out.println("Adding Transaction " + transactionId + " for practice : " + practiceId);
					String dStr = row[3];
					String amount = ZInvoices.getTwoDecimalValues(row[1]);
					String customerId = customers.getCustomerId(practiceId);
					String invNumber = getInvoiceNumberForPracticeId(practiceId, month,year);
					

					JSONObject invData = new JSONObject();
					try {
						//
						invData = ZInvoices.getInvoiceData(invNumber);

					} catch (Exception e) {
						//
						// System.out.println("\n\nException :" + e.getMessage());
						try {
							// System.out.println("\n\nConnection timeout... Wating...");
							Thread.sleep(5000);
							invData = ZInvoices.getInvoiceData(invNumber);

						} catch (Exception ex) {
							// do stuff
							System.out.println("\n\nError while retrying..." + ex.getMessage());

						}

					}
					if (invData.isEmpty()) {
						//
						sbb.append("\"" + row[0] + "\",");
						sbb.append("\"" + row[1] + "\",");
						sbb.append("\"" + row[2] + "\",");
						sbb.append("\"" + row[3] + "\",");
						sbb.append("\"" + practiceId + "\",");
						sbb.append("\"" + customerId + "\",");
						sbb.append("\"No INVOICE\"\n");
						System.out.println("Invoice not found :" + practiceId);

					} else {
						//
						String invoiceId = invData.getString("invoice_id");
						String status = invData.getString("status");
						customerId = invData.getString("customer_id");
						// String status = invData.getString("status");
						String invoiceAmount = ZInvoices.getTwoDecimalValues(invData.getString("total"));
						Date date = format.parse(dStr);

						if (amount.equals(invoiceAmount) || (Double.valueOf(amount) <= Double.valueOf(invoiceAmount))) {
							//
							if ("draft".equals(status)) {
								//
								// System.out.println( "Applying payment :" + invNumber );
								JSONObject json = new JSONObject();
								json.put("customer_id", customerId);
								json.put("payment_mode", "Payflow Pro");
								json.put("reference_number", transactionId);
								json.put("amount", amount);
								json.put("date", apiFormat.format(date));

								invData = new JSONObject();
								invData.put("invoice_id", invoiceId);
								invData.put("amount_applied", amount);

								JSONArray invArr = new JSONArray();
								invArr.add(invData);

								json.put("invoices", invArr);

								try {
									createPayment(json);
								} catch (Exception e) {
									//
									System.out.println("Error Message :" + e.getMessage());
									try {
										System.out.println("\n\nConnection timeout... Wating...");
										Thread.sleep(5000);
										System.out.println(createPayment(json).toString());

									} catch (Exception ex) {
										// do stuff
										System.out.println("\n\nError while retrying..." + ex.getMessage());
										System.out.println("\n\n\n" + sbb.toString());

									}

								}

							} else {
								sbb.append("\"" + row[0] + "\",");
								sbb.append("\"" + row[1] + "\",");
								sbb.append("\"" + row[2] + "\",");
								sbb.append("\"" + row[3] + "\",");
								sbb.append("\"" + practiceId + "\",");
								sbb.append("\"" + customerId + "\",");
								sbb.append("\"" + status + "\",");
								sbb.append("\"Not in Draft\"\n");
							}

						} else {
							//
							System.out.println("Invoice with due:" + practiceId + ":" + invNumber);
							if ("draft".equals(status)) {
								//
								// System.out.println( "Applying payment :" + invNumber );
								JSONObject json = new JSONObject();
								json.put("customer_id", customerId);
								json.put("payment_mode", "Payflow Pro");
								json.put("reference_number", transactionId);
								json.put("amount", amount);
								json.put("date", apiFormat.format(date));

								invData = new JSONObject();
								invData.put("invoice_id", invoiceId);
								invData.put("amount_applied", invoiceAmount);

								JSONArray invArr = new JSONArray();
								invArr.add(invData);

								json.put("invoices", invArr);

								try {
									createPayment(json);
								} catch (Exception e) {
									//
									System.out.println("Error Message :" + e.getMessage());
									try {
										System.out.println("\n\nConnection timeout... Wating...");
										Thread.sleep(5000);
										System.out.println(createPayment(json).toString());

									} catch (Exception ex) {
										// do stuff
										System.out.println("\n\nError while retrying..." + ex.getMessage());
										System.out.println("\n\n\n" + sbb.toString());

									}

								}

							}
							sbb.append("\"" + row[0] + "\",");
							sbb.append("\"" + row[1] + "\",");
							sbb.append("\"" + row[2] + "\",");
							sbb.append("\"" + row[3] + "\",");
							sbb.append("\"" + practiceId + "\",");
							sbb.append("\"" + customerId + "\",");
							sbb.append("\"With DUE\"\n");

						}

					}
					i++;
				} else {
					System.out.println("Skipping : " + transactionId + " as already present");
				}
				if (i % 20 == 0) {
					//
					try {
						System.out.println("\n\nWating...");
						Thread.sleep(5000);
					} catch (InterruptedException ex) {
						// do stuff
					}

				}
			}

		}

		System.out.println("\n\n\n" + sbb.toString());

	}

	public static boolean checkifPresent(String transactionId) throws Exception {
		String url = invoiceURL.concat("customerpayments");
		url = url.concat("?reference_number_startswith=").concat(transactionId);

		String response = getResponse("GET", url, null, null);

		JSONObject jsonObject2 = JSONObject.fromObject(response);
		if (jsonObject2.getJSONArray("customerpayments").size() > 0) {
			System.out.println("Payment already present : " + transactionId);
			return true;
		}
		return false;

	}

	public static void syncData() throws Exception {
		//
		CSVParser parser = new CSVParser(new InputStreamReader(new FileInputStream(new File(CommonUtil.usageCSV))));
		String[][] values = parser.getAllValues();
		int i = 0;
		for (String[] row : values) {
			//
			String practiceId = row[0];
			String invNumber = getInvoiceNumberForPracticeId(practiceId, "September", "2018");
			String amount = ZInvoices.getTwoDecimalValues(row[6]);
			JSONObject invData = ZInvoices.getInvoiceData(invNumber);

			if (!invData.isEmpty()) {
				//
				String invoiceAmount = ZInvoices.getTwoDecimalValues(invData.getString("total"));
				System.out.println(practiceId + "::" + amount + "::" + invoiceAmount);
				if (!amount.equals(invoiceAmount)) {
					System.out.println(practiceId + "::" + amount + "::" + invoiceAmount + "::No Match");
				}

			}

			i++;

			if (i % 5 == 0) {
				//
				try {
					System.out.println("\n\nWating...");
					Thread.sleep(1000);
				} catch (InterruptedException ex) {
					// do stuff
				}

			}

		}

	}

	public static void outData() throws Exception {
		//
		String fileName = "/Users/ramanathan-0940/Desktop/console_out.csv";
		CSVParser parser = new CSVParser(new InputStreamReader(new FileInputStream(new File(fileName))));
		String[][] values = parser.getAllValues();
		StringBuilder sb = new StringBuilder();
		for (String[] row : values) {
			//
			String myId = row[0];
			String status = row[7];

			if ("SYS_UPDATE".equals(status)) {
				//
				sb.append(myId + ",");

			}

		}

		System.out.println(sb.toString());
	}

	public static JSONArray getCustomerInvoices(String customerId, String start, String end) throws Exception {
		//
		String url = invoiceURL + "invoices?sort_column=date&sort_order=D&date_start=" + start + "&date_end=" + end
				+ "&customer_id=" + customerId;
		JSONObject json = JSONObject.fromObject(getResponse("GET", url, null, null));

		if (json.containsKey("code") && "0".equals(json.getString("code"))) {
			//
			JSONArray jArr = json.getJSONArray("invoices");
			if (jArr.size() > 0) {
				return jArr;
			}
		}

		return new JSONArray();

	}

}
