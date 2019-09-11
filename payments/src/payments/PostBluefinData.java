package payments;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class PostBluefinData {

	public static void main(String[] args)  throws Exception{
		//		
		try{
			String content = new String(Files.readAllBytes(Paths.get("/Users/ramanathan-0940/Desktop/Recurring.txt")));			
			JSONArray json = JSONArray.fromObject(content);
			
			String u = "https://ehr.charmtracker.com/api/ehr/payments/bluefin/postback";

			int size = json.size();

			for(int i=0; i<size; i++) {
				//
				JSONObject obj = JSONObject.fromObject( json.get(i).toString() );
				try {
					System.out.println( "Output :" + i + ":::" + obj.toString() );
					//System.out.println( "Output :" + i + ":::" + getResponse(u, obj.toString()) );
				} catch(Exception ee) {
					ee.printStackTrace();
				}

			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	private static String getResponse(String u, String jsonstring) throws Exception {
		//
		
		URL url = new URL(u);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setRequestProperty("charset", "utf-8");
		connection.setDoOutput(true);
		connection.setConnectTimeout(6000);
		
		//
		DataOutputStream out = new DataOutputStream(connection.getOutputStream());			
		//System.out.println(query);
		out.writeBytes(jsonstring);
		out.close();
		
		int status = connection.getResponseCode();
		StringBuilder response = new StringBuilder();
		if( status != 400 ) {
			
			if( status != 200 && status != 201 ) {
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
	
}
