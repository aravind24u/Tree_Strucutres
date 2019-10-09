package com.zoho.charm.project.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import com.medicalmine.charm.pricing.util.PlanBasedRestrictionUtil;
import com.medicalmine.charm.store.chase.bean.request.Request;
import com.medicalmine.charm.store.chase.bean.request.ValidIndustryTypes;
import com.medicalmine.charm.store.chase.bean.request.ValidTransTypes;
import com.medicalmine.charm.store.chase.bean.request.wrapper.ChaseMerchantCredentials;
import com.medicalmine.charm.store.chase.bean.request.wrapper.ChaseNewOrder;
import com.medicalmine.charm.store.chase.bean.response.Response;

public class Testing {

	protected static final Logger LOGGER = Logger.getLogger(Testing.class.getName());

	public static void main(String[] args) throws Exception {
		System.out.println(postNewOrderToChase("4012888888881881", "0621", "1 Infinite Loop", "Suite 350", "Cupertino",
				"CA", "95014", "8124746412"));
	}

	public static void setCommonProperties(ChaseMerchantCredentials commonCredentials) {
		// Constants - should be moved to DB
		commonCredentials.setOrbitalConnectionUsername("TESTZOHO1074");// CharmPropertiesUtil.getValue("CHASE_CONNECTION_USERNAME");
		commonCredentials.setOrbitalConnectionPassword("4Vm74Xv9vFlP");// CharmPropertiesUtil.getValue("CHASE_CONNECTION_PASSWORD");
		commonCredentials.setBIN("000001");// CharmPropertiesUtil.getValue("CHASE_BIN");
		commonCredentials.setMerchantID("371074");// CharmPropertiesUtil.getValue("CHASE_MERCHANT_ID");
		commonCredentials.setTerminalID("001");// CharmPropertiesUtil.getValue("CHASE_TERMINAL_ID");
		commonCredentials.setIndustryType(ValidIndustryTypes.RC);
		commonCredentials.setCurrencyCode("840");// CharmPropertiesUtil.getValue("CHASE_CURRENCY_CODE");
		commonCredentials.setCurrencyExponent("2");// CharmPropertiesUtil.getValue("CHASE_CURRENCY_EXPONENT");
	}

	public static Boolean postNewOrderToChase(String cardNumber, String expiry, String line1, String line2, String city,
			String state, String zipcode, String phoneNumber) {
		Response response = null;
		try {
			ChaseNewOrder newOrderType = new ChaseNewOrder();

			setCommonProperties(newOrderType);

			// Product Level constants - code level handle
			newOrderType.setMessageType(ValidTransTypes.AC);

			// Entirely based on client data
			newOrderType.setAccountNum(cardNumber);
			newOrderType.setExp(expiry);

			newOrderType.setAVSaddress1(line1);
			newOrderType.setAVSaddress2(line2);
			newOrderType.setAVScity(city);
			newOrderType.setAVSstate(state);
			newOrderType.setAVSzip(zipcode);

			newOrderType.setAVSphoneNum(phoneNumber);

			// Back end logic
			newOrderType.setOrderID("MITXML6");
			newOrderType.setAmount("25");

			Request request = new Request();
			request.setNewOrder(newOrderType);

			String responseStr = marshallClasses(request, Request.class);

			response = unmarshallResponse(responseStr, Response.class);

		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
		return response.getNewOrderResp().getApprovalStatus().equals("1")
				&& response.getNewOrderResp().getRespCode().equals("00");
	}

	public static String marshallClasses(Object request, Class<Request> rootClass) {
		String response = null;
		try {
			JAXBContext context = JAXBContext.newInstance(rootClass);
			Marshaller marshaller = context.createMarshaller();
			// marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			StringWriter body = new StringWriter();
			marshaller.marshal(request, body);
			System.out.println(body);

			System.out.println(body.toString().length());
			response = postPaymentToChase(body.toString());
		} catch (Exception e) {
			PlanBasedRestrictionUtil.sendStoreErrorMessage("Error while marshalling the XML", e);
		}
		return response;
	}

	public static Response unmarshallResponse(String responseString, Class<Response> rootClass) {
		Response response = null;
		try {
			System.out.println(responseString);
			JAXBContext context = JAXBContext.newInstance(rootClass);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			InputStream inputStream = new ByteArrayInputStream(responseString.getBytes(StandardCharsets.UTF_8));

			response = (Response) unmarshaller.unmarshal(inputStream);

		} catch (Exception e) {
			PlanBasedRestrictionUtil.sendStoreErrorMessage("Error while unMarshalling the XML", e);
		}
		return response;
	}

	public static String postPaymentToChase(String doc) {
		String response = null;
		try {
			URI uri = new URI("https", "orbitalvar1.chasepaymentech.com", "/authorize", null);
			List<Header> headers = new ArrayList<>();

			headers.add(new BasicHeader("Content-type", "application/PTI79"));
			headers.add(new BasicHeader("MIME-Version", "1.0"));
			headers.add(new BasicHeader("Content-transfer-encoding", "text"));
			headers.add(new BasicHeader("Request-number", "1"));
			headers.add(new BasicHeader("Document-type", "Request"));

			response = postRequest(uri, headers.toArray(new Header[headers.size()]), doc);
		} catch (Exception e) {
			e.printStackTrace();
			// PlanBasedRestrictionUtil.sendStoreErrorMessage("Error while forming data to
			// post to
			// Chase", e);
		}

		return response;

	}

	public static String postRequest(URI uri, Header[] headers, String doc) throws Exception {
		try {
			HttpPost httpPost = new HttpPost(uri);

			if (headers != null) {
				httpPost.setHeaders(headers);
			}

			if (doc != null) {
				StringEntity entity = new StringEntity(doc);
				httpPost.setEntity(entity);
			}

			HttpClient client = new DefaultHttpClient();

			HttpResponse response = client.execute(httpPost);

			HttpEntity httpEntity = response.getEntity();
			return EntityUtils.toString(httpEntity);
		} catch (Exception e) {
			// PlanBasedRestrictionUtil.sendStoreErrorMessage("Error while posting to
			// Chase", e);
			throw e;
		}
	}
}
