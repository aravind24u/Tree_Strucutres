package payments;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;

import org.apache.commons.csv.CSVParser;
import net.sf.json.JSONObject;

public class Report {

	private static DecimalFormat dFormat = new DecimalFormat("0.00");

	public static void main(String[] args)  throws Exception{
		//
		CSVParser parser = new CSVParser(new InputStreamReader(new FileInputStream(new File(CommonUtil.usageCSV))));
		String[][] values = parser.getAllValues();
		StringBuilder sb = new StringBuilder();

		TreeMap<String, HashMap<String, Object>> list = new TreeMap<String, HashMap<String, Object>>();
		for(String[] row: values){
			//
			String practiceId = row[0];
			String mayDue = row[25];

			HashMap<String, Object> data = new HashMap<String, Object>();			
			data.put("may", getTwoDecimalValues( mayDue ));
			data.put("due", getTwoDecimalValues( "0.0" ));
			list.put(practiceId, data);

		}			

		Customers cus = new Customers();

		String content = new String(Files.readAllBytes(Paths.get("/Users/ramanathan-0940/Desktop/Profiles.json")));			
		JSONObject jsonObject = JSONObject.fromObject(content);		

		String fileName = "/Users/ramanathan-0940/Desktop/DueList.csv";
		parser = new CSVParser(new InputStreamReader(new FileInputStream(new File(fileName))));
		values = parser.getAllValues();		
		sb.append("Practice ID, Profile, Due, ZInvoice Due, May, Diff\n");
		int count=0;
		for(String[] row: values){
			//
			String practiceId = row[0];

			HashMap<String, Object> data = new HashMap<String, Object>();

			if( list.containsKey(practiceId) ) {
				data = (HashMap<String, Object>) list.get(practiceId);
			} else {
				list.put(practiceId, data);
				data.put("may", getTwoDecimalValues( "0.0" ));				

			}

			data.put("due", getTwoDecimalValues( row[1] ));
		}		

		Iterator<String> ite = list.keySet().iterator();

		while(ite.hasNext()) {
			//
			String practiceId = ite.next();			
			String customerId = cus.getCustomerId(practiceId);

			if(  !customerId.isEmpty() && ( !cus.isTestCustomer(practiceId) ) ) {
				//
				HashMap<String, Object> data = (HashMap<String, Object>) list.get(practiceId);
				Double due = Double.valueOf( data.get("due") + "" );

				System.out.println("Checking practice:" + practiceId);
				JSONObject json = ZInvoices.getCustomerData(customerId);
				String invoiceDueStr = "0.00";
				if( json.containsKey("outstanding_receivable_amount") ) {
					invoiceDueStr = json.get("outstanding_receivable_amount") + "";
				}

				String mayUsage = data.get("may") + "";
				String profile = "";
				if( jsonObject.containsKey(practiceId) ) {
					profile = jsonObject.getString(practiceId);
				}
				double invoiceDue = Double.valueOf(invoiceDueStr);
				//
				System.out.println( practiceId + "," + profile + "," + due + "," + invoiceDue + "," + mayUsage + "," + ( due-invoiceDue ) );
				sb.append(practiceId + "," + profile + "," +  getTwoDecimalValues( due + "" ) + "," + getTwoDecimalValues( invoiceDueStr ) + "," + mayUsage + "," + ( due-invoiceDue ) + "\n" );
				count++;
				if( count%20 == 0 ) {
					try{
						System.out.println("Wating...");
						Thread.sleep(5000);
					} catch (InterruptedException ex) {
						// do stuff
					}					
				}

			}			

		}

		File outFile = new File("/Users/ramanathan-0940/Desktop/Final_Charge_Report.csv");
		if( outFile.exists() ) {
			outFile.delete();
			outFile.createNewFile();
		}

		try (FileWriter file = new FileWriter(outFile)) {
			file.write(sb.toString());
		}		

		System.out.println(sb.toString());		

	}

	public static String getTwoDecimalValues(String strVal)throws Exception {
		//
		if(strVal != null && !strVal.equals(null)) {
			//
			Double val = new Double(strVal);
			String retVal = dFormat.format(val).toString();
			return retVal.equals("-0.00")? "0.00": retVal;

		}
		return "0.00";
	}	

}
