package payments;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

import org.apache.commons.csv.CSVParser;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class DueReport {

	public static void main(String[] args)  throws Exception{

		//
		StringBuilder sb = new StringBuilder();
		Customers cus = new Customers();
		
		String fileName = "/Users/ramanathan-0940/Desktop/Store_Charge.csv";
		CSVParser parser = new CSVParser(new InputStreamReader(new FileInputStream(new File(fileName))));
		String[][] values = parser.getAllValues();		
		sb.append("Practice ID, Name, April, Charge, Diff, Comments, Current Due, Credit, First Name, Last Name, Email\n");
		int count=0;
		for(String[] row: values){
			//
			String practiceId = row[0];
			String practiceName = row[1];
			String aprilUsage = row[2];
			String charge = row[3];
			String diff = row[4];
			String comments = row[5];
			String customerId = cus.getCustomerId(practiceId);
			
			if(  !customerId.isEmpty() && ( !cus.isTestCustomer(practiceId) ) ) {
				//
				System.out.println("Checking practice:" + practiceId);
				JSONObject json = ZInvoices.getCustomerData(customerId);
				String due = "0.00", credit = "0.00";
				String firstName = "", lastName = "", email = "";
				if( json.containsKey("outstanding_receivable_amount") ) {
					due = json.get("outstanding_receivable_amount") + "";
				}
				
				if( json.containsKey("unused_credits_receivable_amount") ) {
					credit = json.get("unused_credits_receivable_amount") + "";
				}
				
				if( json.containsKey("contact_persons") ) {
					
					JSONArray arr = JSONArray.fromObject( json.get("contact_persons") + "" );
					int size = arr.size();
					for( int i=0; i<size; i++ ) {
						//
						JSONObject obj = JSONObject.fromObject( arr.get(i) );
						
						if( obj.containsKey("is_primary_contact") && (obj.get("is_primary_contact") + "").equals("true") ) {
							//
							firstName = obj.get("first_name") + "";
							lastName = obj.get("last_name") + "";
							email = obj.get("email") + "";
							
						}
						
					}
					due = json.get("outstanding_receivable_amount") + "";
				}				
				
				sb.append(practiceId + "," + practiceName + "," + aprilUsage + "," + charge + "," + diff + "," + comments + "," + due + "," + credit + "," + firstName.replace(",", " ") + "," + lastName.replace(",", " ") + "," + email + "\n" );
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

}
