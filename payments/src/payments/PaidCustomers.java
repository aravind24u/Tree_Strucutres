package payments;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.apache.commons.csv.CSVParser;

public class PaidCustomers {

		public static void main(String[] args) {

			try {
				//System.out.println(AuthUtil.decryptString("EC786RT70S3Ex6700sG0703G"));
				String fileName = "/Users/ramanathan-0940/Desktop/Test.csv";
				CSVParser parser = new CSVParser(new InputStreamReader(new FileInputStream(new File(fileName))));
				String[][] values = parser.getAllValues();
				StringBuilder sb = new StringBuilder(); 
				Customers cus = new Customers();
				for(String[] row: values){
					//
					String practiceId = row[0];
					String practiceName= row[1];

					String cusId = cus.getCustomerId(practiceId);
					
					if( cusId.isEmpty() ){
						//
						System.out.println("Practice Id :" + practiceId  + ":" + practiceName);
						
					}
					
					
				}
				
				System.out.println(sb.toString());
							
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
	
}
