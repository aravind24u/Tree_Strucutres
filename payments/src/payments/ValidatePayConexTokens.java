package payments;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

import org.apache.commons.csv.CSVParser;

public class ValidatePayConexTokens {

	public static void main(String[] args) throws Exception {
		String fileName = "/Users/ramanathan-0940/Desktop/Tokens.csv";
		String payconexFileName = "/Users/ramanathan-0940/Desktop/Payconex.csv";
		
		CSVParser parser = new CSVParser(new InputStreamReader(new FileInputStream(new File(fileName))));
		CSVParser parser1 = new CSVParser(new InputStreamReader(new FileInputStream(new File(payconexFileName))));		
		
		String[][] values = parser.getAllValues();
		String[][] payconexValues = parser1.getAllValues();		
		
		StringBuilder sb = new StringBuilder();
		for (String[] row : values) {
			//
			int token = Integer.valueOf( row[0] );
			boolean exists = Boolean.FALSE;
			for(String[] transRow : payconexValues) {
				//
				int transId = Integer.valueOf( transRow[0] );				
				
				if( token == transId ) {
					exists = Boolean.TRUE;
				}
				
			}
			
			if( !exists ) {
				sb.append( row[0] + "," );
			}
		}
		System.out.println("\n\n\n" + sb.toString());

		try (FileWriter file = new FileWriter("/Users/ramanathan-0940/Desktop/Status.csv")) {
			file.write(sb.toString());
		}		

	}

}
