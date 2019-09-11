package payments;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.csv.CSVParser;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ResellerReport {

	public static void main(String[] args)  throws Exception{
		// TODO Auto-generated method stub
		String fileName = "/home/med/Aravind/Docs/Reseller Report/resellers.csv";
		CSVParser parser = new CSVParser(new InputStreamReader(new FileInputStream(new File(fileName))));
		String[][] values = parser.getAllValues();
		StringBuilder sb = new StringBuilder();
		Customers cus = new Customers();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat mf = new SimpleDateFormat("MMMM");
		SimpleDateFormat yf = new SimpleDateFormat("yyyy");

		String startDate = "2019-04-01";
		Date subStartDate = df.parse("2019-05-01");
		Date othersStartDate = df.parse(startDate);
		
		String endDate = "2019-08-01";
		Date subEndDate = df.parse(endDate);
		Date othersEndDate = df.parse("2019-07-01");
		


		
		HashMap<String, String> items = new HashMap<String, String>();
		items.put("month", "month");
		items.put("year", "year");		
		ArrayList<HashMap<String, Object>> customers = new ArrayList<HashMap<String, Object>>();
		String[] months = {"April","May","June"};
		int c =0;
		for(String[] row: values){
			//
			String resellerId = row[0];
			String resellerName = row[1];
			String practiceId = row[2];
			String practiceName =  row[3];
			String timeOfCreation =  row[4];			
			
			String customerId = cus.getCustomerId(practiceId);
			
			if( !customerId.isEmpty() ) {
				//
				JSONArray jArr = ZInvoices.getCustomerInvoices(customerId, startDate, endDate);
				int size = jArr.size();
				int i=0;
				
				HashMap<String, Object> customer = new HashMap<String, Object>();
				customer.put("reseller_id", resellerId);
				customer.put("reseller_name", resellerName.replaceAll(",", ".") );
				customer.put("practice_id", practiceId);
				customer.put("practice_name", practiceName.replaceAll(",", ".") );
				customer.put("time_of_creation", timeOfCreation);
				
				
				while( i<size ) {
					c++;
					
					if( c%15 == 0 ) {
						try{
							System.out.println("Wating...");
							Thread.sleep(5000);
						} catch (InterruptedException ex) {
							// do stuff
						}						
					}
					
					JSONObject jObj = jArr.getJSONObject(i);
					
					String invoiceId = jObj.getString("invoice_id");
					String invoice_number =  jObj.getString("invoice_number");
					String dateStr = jObj.getString("date");
					
					Date d = df.parse(dateStr);
					Long d1 = d.getTime();
					String monthStr = mf.format(d);
					String yearStr = yf.format(d);
					
					String str = practiceId.substring(3).replaceFirst("^0+(?!$)", "");
					int len = str.length();				
					if( len < 7 ) {
						//			
						for( int j=len; j<7; j++ ) {
							str = "0" + str;
						}
						
					}					
														
					String subInvFormat = "^(MM-INV-RCM-" + str + "|MM-INV-" + str + ").*$";
					if( ( invoice_number.matches(subInvFormat) && ( ( d1>=subStartDate.getTime()  ) && ( d1<subEndDate.getTime() ) ) ) || ( !invoice_number.matches(subInvFormat) && ( ( d1>=othersStartDate.getTime()  ) && ( d1<othersEndDate.getTime() ) )   ) ) {
						//
						if( invoice_number.matches(subInvFormat) ) {							
							d.setMonth(d.getMonth() - 1 );
							monthStr = mf.format(d);
							
						}
						
						System.out.println("Invoice #:" + invoice_number + "::" + dateStr + "::" + monthStr);
						
						String month = monthStr + "";
						HashMap<String, Object> valMap = new HashMap<String, Object>();
								
						if( customer.containsKey(month) ) {
							valMap = (HashMap<String, Object>) customer.get(month);
						}
																	
						JSONObject inv = ZInvoices.getInvoiceDetails(invoiceId);
						JSONArray lineItems = JSONArray.fromObject(inv.get("line_items"));
						
						int lSize = lineItems.size();
						int j = 0;
						
						while( j<lSize ) {
							JSONObject lineItem = lineItems.getJSONObject(j);
							String name = lineItem.getString("name");
							String _quantity = lineItem.get("quantity") + "";
							String _rate = lineItem.get("rate") + "";
							String _total = lineItem.get("item_total") + "";

							if( !items.containsKey(name) ) {
								items.put(name + "_quantity", name + "_quantity" );
								items.put(name + "_rate", name + "_rate" );
								items.put(name + "_total", name + "_total" );
							}
							
							valMap.put(name + "_quantity", _quantity );
							valMap.put(name + "_rate", _rate );
							valMap.put(name + "_total", _total );							
							j++;
						}
						
						valMap.put("month", monthStr );
						valMap.put("year", yearStr );						
						
						customer.put(month, valMap);
						
					} else {
						//
						System.out.println("Out of date range Invoice #:" + invoice_number + "::" + dateStr);
					}
					
					i++;										
				}
				
				customers.add(customer);
				
			} else {
				//
				System.out.println("Customer does not exists --->" + practiceId);
				
			}
			
			
		}
		
		String[] keys = items.keySet().toArray(new String[items.size()]);
		sb.append("reseller_id,reseller_name,practice_id,practice_name,time_of_creation");
		
		for(String key: keys) {
			sb.append("," + key);
		}
		
		sb.append("\n");
		
		int k = keys.length;
		for(HashMap<String, Object> customer: customers ) {
			//
			for(String month: months) {
				HashMap<String, Object> valMap = (HashMap<String, Object>) customer.get(month);
				
				if( valMap != null ) {				
					sb.append(customer.get("reseller_id") + ",");
					sb.append(customer.get("reseller_name") + ",");
					sb.append(customer.get("practice_id") + ",");
					sb.append(customer.get("practice_name") + ",");
					sb.append(customer.get("time_of_creation") + "");				
					
					for(String key: keys) {
						sb.append("," + valMap.get(key));
					}
	
					sb.append("\n");				
				} else {
					sb.append(customer.get("reseller_id") + ",");
					sb.append(customer.get("reseller_name") + ",");
					sb.append(customer.get("practice_id") + ",");
					sb.append(customer.get("practice_name") + ",");
					sb.append(customer.get("time_of_creation") + "");				
					
					for(int i=0; i<k; i++) {
						sb.append(",-");
					}
	
					sb.append("\n");					
				}
				
			}
			
		}
		
		File outFile = new File(CommonUtil.resellerReport);
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
