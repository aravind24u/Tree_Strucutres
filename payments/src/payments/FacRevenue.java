package payments;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeMap;

import org.apache.commons.csv.CSVParser;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class FacRevenue {

	public static void main(String[] args)  throws Exception{
		// TODO Auto-generated method stub
		String fileName = "/Users/ramanathan-0940/Desktop/Fac_Revenue.csv";
		CSVParser parser = new CSVParser(new InputStreamReader(new FileInputStream(new File(fileName))));
		String[][] values = parser.getAllValues();
		StringBuilder sb = new StringBuilder();
		Customers cus = new Customers();
		
		TreeMap<String, HashMap<String, Object>> pracList = new TreeMap<>(); 
		
		for(String[] arr: values) {
			String practiceId = arr[0];			
			
			if( !cus.isTestCustomer(practiceId) ) {
				//
				HashMap<String, Object> facDetails = new HashMap<>();
				if( pracList.containsKey(practiceId) ) {
					facDetails = pracList.get(practiceId);
					String state1 = facDetails.get("facility_state") + "";
					if( !state1.contains( arr[4] ) ) {
						facDetails.put("facility_state", facDetails.get("facility_state") + ", " + arr[4]);						
					} 
					
				} else {
					//
					facDetails.put("practice_name", arr[1].replaceAll(",", ".")  );
					facDetails.put("facility_state", arr[4]);
					facDetails.put("revenue", ZInvoices.getTwoDecimalValues( arr[5] ));
					pracList.put(practiceId, facDetails);
				}							
				
			}
			
		}
		
		String[] keys = pracList.keySet().toArray(new String[pracList.size()]);
		sb.append("practice_id, practice_name, facility_state, revenue\n");
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		String startDate = "2018-01-01";
		Date othersStartDate = df.parse(startDate);		
		String endDate = "2019-01-01";
		Date othersEndDate = df.parse(endDate);		
		
		for(String key: keys) {
			//
			String customerId = cus.getCustomerId(key);
			HashMap<String, Object> facDetails = pracList.get(key);
			Double otherRevenue = 0d;			
			if( !customerId.isEmpty() ) {
				//
				if( facDetails.containsKey("other_revenue")  ) {
					otherRevenue = Double.valueOf( facDetails.get("other_revenue") + "" );
				}
				
				String str = key.substring(3).replaceFirst("^0+(?!$)", "");
				int len = str.length();				
				if( len < 7 ) {
					//			
					for( int j=len; j<7; j++ ) {
						str = "0" + str;
					}
					
				}			
				

				String subInvFormat = "^(MM-INV-RCM-" + str + "|MM-INV-" + str + ").*$";
				JSONArray jArr = ZInvoices.getCustomerInvoices(customerId, startDate, endDate);
				int size = jArr.size();
				System.out.println("Key :" + key + ":" + customerId + ":" + subInvFormat + ":" + size);			
				for( int i=0; i<size; i++ ) {
					//
					JSONObject jObj = jArr.getJSONObject(i);			
					String invoice_number =  jObj.getString("invoice_number");
					String dateStr = jObj.getString("date");
					double total = Double.valueOf( jObj.getString("total") );
					
					Date d = df.parse(dateStr);
					Long d1 = d.getTime();				

					if( !invoice_number.matches(subInvFormat) && ( ( d1>=othersStartDate.getTime()  ) && ( d1<othersEndDate.getTime() ) ) ) {
						//
						otherRevenue += total;
						
					}
					
				}
				
				facDetails.put("other_revenue", otherRevenue);
				
				try{
					System.out.println("Wating...");
					Thread.sleep(5000);
				} catch (InterruptedException ex) {
					// do stuff
				}				
				
			} else {
				System.out.println("Key :" + key + ": No customer");				
				facDetails.put("other_revenue", otherRevenue);
			}
			
		}		
		
		for(String key: keys) {
			HashMap<String, Object> facDetails = pracList.get(key);
			sb.append(key + ", \"" + facDetails.get("practice_name") + "\", \"" + facDetails.get("facility_state") + "\", " + facDetails.get("revenue") + ", " + facDetails.get("other_revenue") + "\n" );
		}		
		
		File outFile = new File("/Users/ramanathan-0940/Desktop/Fac_Report.csv");
		if( outFile.exists() ) {
			outFile.delete();
			outFile.createNewFile();
		}
		
		try (FileWriter file = new FileWriter(outFile)) {
			file.write(sb.toString());
		}		
		
		System.out.println(sb.toString());		

	}

}
