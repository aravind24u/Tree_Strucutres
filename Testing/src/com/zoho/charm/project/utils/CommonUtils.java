package com.zoho.charm.project.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.medicalmine.api.rest.pharmacy.constants.PharmacyHubConstants;
import com.zoho.charm.project.utils.encoder.EncodingConstants;

import net.sf.json.JSONObject;

public class CommonUtils {

	public static final String APOLLO_HOME_DIR = "/home/med/Aravind/Docs/Apollo/";
	public static final String PRICING_HOME_DIR = "/home/med/Aravind/Docs/pricing/";
	public static final String INVOICE_HOME_DIR = "/home/med/Aravind/Docs/Invoice/";

	private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.###");;
	
	
	public static String CLIENT_ID = "client_id";
	public static String CLIENT_SECRET = "client_secret";
	public static String REDIRECT_URI = "redirect_uri";
	public static String GRANT_TYPE = "grant_type";
	public static String GRANT_TYPE_REFRESH_TOKEN = "refresh_token";
	public static String REFRESH_TOKEN = "refresh_token";
	public static String ACCESS_TOKEN = "access_token";
	

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

	public static List<String> getPracticeIdsToList(List<String> file) throws Exception {
		List<String> practiceIds = new ArrayList<>();
		file.forEach(line -> {
			if (StringUtils.isNotEmpty(line)) {
				String[] values = line.split(",");
				practiceIds.add(values[0]);
			}
		});
		return practiceIds;
	}

	public static HashMap<String, Double> getHashMapFromFile(String fileName) {
		return getHashMapFromFile(CommonUtils.PRICING_HOME_DIR.concat(fileName), 0, 16, ",");
	}

	public static HashMap<String, Double> getHashMapFromFile(String fileName, Integer keyIndex, Integer valueIndex,
			String delimiter) {

		BufferedReader reader = null;
		HashMap<String, Double> usage = new HashMap<>();
		System.out.println("Loading file : ".concat(fileName));
		try {
			reader = new BufferedReader(new FileReader(fileName));

			String line = reader.readLine();
			line = reader.readLine();

			while (line != null && line.trim().length()>0) {
				line = line.replaceAll("\"", "");
				String[] values = line.split(delimiter);

				String practiceId = values[keyIndex];
				Double monthlyCharge = new Double(DECIMAL_FORMAT.format(new Double(values[valueIndex])));

				usage.put(practiceId, monthlyCharge);

				line = reader.readLine();
			}
			System.out.println("Finished loading file : ".concat(CommonUtils.PRICING_HOME_DIR.concat(fileName)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return usage;
	}
	
	public static JSONObject getAuthTokenUsingRefreshToken(String authServerUrl, String refreshToken, String clientId,
            String clientSecretId, String redirectUri) {
 
        HashMap<String, Object> parameters = new HashMap<>();
        JSONObject response = null;
        try {
            parameters.put(REFRESH_TOKEN, refreshToken);
            parameters.put(CLIENT_ID, clientId);
            parameters.put(CLIENT_SECRET, clientSecretId);
            parameters.put(REDIRECT_URI, redirectUri);
            parameters.put(GRANT_TYPE, GRANT_TYPE_REFRESH_TOKEN);
 
            String result = sendAPIRequest(PharmacyHubConstants.API_METHOD_POST, authServerUrl, parameters,
                    null, PharmacyHubConstants.API_CONTENT_JSON, Boolean.FALSE, null, Boolean.TRUE);
            response = JSONObject.fromObject(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
	
	 public static String sendAPIRequest(String method, String url, HashMap<String, Object> parameters, String doc,
	            String contentType, boolean disableAutoRedirect, String authToken) throws Exception {
	        return sendAPIRequest(method, url, parameters, doc, contentType, disableAutoRedirect, authToken, Boolean.FALSE);
	    }
	 
	    public static String sendAPIRequest(String method, String url, HashMap<String, Object> parameters, String doc,
	            String contentType, boolean disableAutoRedirect, String authToken, Boolean disableAuth) throws Exception {
	 
	        HttpURLConnection connection = null;
	        OutputStream documentWriter = null;
	        URL urlToCall = null;
	        int BUFFER_SIZE = 500;
	        BufferedReader responseReader = null;
	        StringBuilder response = new StringBuilder();
	 
	        try {
	            String params = getQueryString(parameters);
	 
	            urlToCall = new URL(url + params);
	 
	            System.out.println("Calling the URL : \"" + url + " \" with parameters : {" + params + " }");
	 
	            connection = (HttpURLConnection) urlToCall.openConnection();
	            connection.setRequestMethod(method);
	            connection.setDoOutput(Boolean.TRUE);
	            connection.setDoInput(Boolean.TRUE);
	            connection.setUseCaches(Boolean.FALSE);
	            connection.setReadTimeout(10000 * 60);
	            connection.setConnectTimeout(600000);
	            if (!disableAuth) {
	                connection.setRequestProperty("Authorization", authToken);// No I18N
	            }
	            if (disableAutoRedirect) {
	                connection.setInstanceFollowRedirects(Boolean.FALSE);
	            }
	            connection.setRequestProperty("Content-Type", contentType); // No I18N
	            if (doc != null && !doc.trim().isEmpty()) {
	                connection.setRequestProperty("Content-Length", String.valueOf(doc.length())); // No I18N
	                documentWriter = connection.getOutputStream();
	                documentWriter.write(doc.getBytes());
	                documentWriter.flush();
	            }
	            responseReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	            char[] cbuf = new char[BUFFER_SIZE + 1];
	            while (Boolean.TRUE) {
	                int numCharRead = responseReader.read(cbuf, 0, BUFFER_SIZE);
	                if (numCharRead == -1) {
	                    break;
	                }
	                String line = new String(cbuf, 0, numCharRead);
	                response.append(line);
	            }
	 
	        } catch (Exception e) {
	            System.out.println(MessageFormat.format("Exception while Calling the URL {0} ,\n With parameters {1} "
	                    + ",\n Doc : {2} ,\n The Exception is : {3}",
	                    url, parameters.toString(), doc, e.getMessage() ));
	            e.printStackTrace();
	            throw e;
	        } finally {
	            if (responseReader != null) {
	                responseReader.close();
	            }
	            if (documentWriter != null) {
	                documentWriter.close();
	            }
	            if (connection != null) {
	                connection.disconnect();
	            }
	        }
	 
	        return response.toString();
	    }
	 
	    /**
	     * This function is used to get the query string formed of the parameters
	     * 
	     * @param parameters
	     * @return
	     */
	    private static String getQueryString(HashMap<String, Object> parameters) {
	        try {
	            if (parameters != null) {
	                StringBuilder params = new StringBuilder("?");
	 
	                for (String key : parameters.keySet()) {
	                    String value = parameters.get(key).toString();
	                    if (value != null && !value.trim().isEmpty()) {
	                        params.append(URLEncoder.encode(key, "utf-8"));
	                        params.append("=");
	                        params.append(URLEncoder.encode(value, "utf-8"));
	                        params.append("&");
	                    }
	                }
	                params.deleteCharAt(params.length() - 1);
	                return params.toString();
	            }
	        } catch (UnsupportedEncodingException e) {
	            System.out.println("ERROR: Problem URL encoding the encrypted token.");
	        }
	        return "";
	    }
}
