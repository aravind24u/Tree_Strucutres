package payments;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.apache.commons.csv.CSVParser;

public class TopCustomers {

	public static void main(String[] args)  throws Exception{
		// TODO Auto-generated method stub
		String fileName = "/Users/ramanathan-0940/Desktop/a.txt";
		CSVParser parser = new CSVParser(new InputStreamReader(new FileInputStream(new File(fileName))));
		String[][] values = parser.getAllValues();
		StringBuilder sb = new StringBuilder();
		Customers cus = new Customers();
		int count = 0;
		for(String[] row: values){
			//
			int len = row.length;
			
			for(int i=0; i<len; i++) {
				String practiceId = row[i].trim();
				
				if( cus.isTestCustomer(practiceId) ) {
					System.out.println("Test customer:" + practiceId);
				} else {
					//
					if( sb.toString().contains(practiceId) ) {
						//
						System.out.println("Customer Exists:" + practiceId);						
					} else {
						//
						count++;
						sb.append(practiceId + ", ");
						
					}
					
				}
				
			}
			
			
		}
		
		System.out.println(sb.toString());
		System.out.println("Count :" + count);

	}

}
