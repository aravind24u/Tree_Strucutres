package payments;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.medicalmine.api.rest.pharmacy.constants.PharmacyHubConstants;

import net.sf.json.JSONObject;

public class CommonUtil {
	
	private static final Logger LOGGER = Logger.getLogger(CommonUtil.class.getName());
	static String CLIENT_ID = "client_id";
	static String CLIENT_SECRET = "client_secret";
	static String REDIRECT_URI = "redirect_uri";
	static String GRANT_TYPE = "grant_type";
	static String GRANT_TYPE_REFRESH_TOKEN = "refresh_token";
	static String REFRESH_TOKEN = "refresh_token";
	static String ACCESS_TOKEN = "access_token";
	
	static String baseDir = "/home/local/ZOHOCORP/aravind-5939/Docs";
	static String invoiceDir = baseDir.concat("/Invoice");
	static String pricingDir = baseDir.concat("/pricing");
	static String resellerDir = baseDir.concat("/Reseller Report");
	
	static String ZCustomersFile = invoiceDir.concat("/ZCustomers.txt");
	static String usageCSV = pricingDir.concat("/Usage_august_2019_New.csv");
	
	static String resellerReport = resellerDir.concat("/ResellerReport.csv");
	
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
 
            String result = CommonUtil.sendAPIRequest(PharmacyHubConstants.API_METHOD_POST, authServerUrl, parameters,
                    null, PharmacyHubConstants.API_CONTENT_JSON, Boolean.FALSE, null, Boolean.TRUE);
            response = JSONObject.fromObject(result);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Exception", e);
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
	 
	            LOGGER.log(Level.INFO,"Calling the URL : \"" + url + " \" with parameters : {" + params + " }");
	 
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
	            LOGGER.log(Level.SEVERE, "Exception", e);
	            LOGGER.log(Level.SEVERE, "Exception while Calling the URL {0} ,\n With parameters {1} "// No I18N
	                    + ",\n Doc : {2} ,\n The Exception is : {3}", // No I18N
	                    new String[] { url, parameters.toString(), doc, e.getMessage() });
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
	            LOGGER.log(Level.SEVERE, "ERROR: Problem URL encoding the encrypted token.");
	        }
	        return "";
	    }
}
