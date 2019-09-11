package payments;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;

import org.apache.commons.csv.CSVParser;

public class UsageReport {

	public static void main(String[] args) throws Exception  {
		String fileName = "/Users/ramanathan-0940/Desktop/a.csv";
		CSVParser parser = new CSVParser(new InputStreamReader(new FileInputStream(new File(fileName))));
		String[][] values = parser.getAllValues();
		StringBuilder sb = new StringBuilder();
		TreeMap<String, HashMap<String, Object>> list = new TreeMap<String, HashMap<String, Object>>();
		for (String[] row : values) {
			//
			String practiceId = row[0];
			//System.out.println("Practice ID ->" + practiceId);
			String practiceName = row[1];
			String month = row[2];
			Integer encCount = Integer.valueOf( row[3] );
			HashMap<String, Object> valMap = new HashMap<>();

			if( list.containsKey(practiceId) ) {
				valMap = list.get(practiceId);
			} else {
				valMap.put("Id", practiceId);
				valMap.put("Name", practiceName);
				list.put(practiceId, valMap);
			}

			int total = 0;
			int count = 0;

			if( valMap.containsKey("Total") ) {
				total = (Integer) valMap.get("Total");
			}

			if( valMap.containsKey("Count") ) {
				count = (Integer) valMap.get("Count");
			}			

			if( encCount > 50 ) {
				total += encCount;
				count++;
			}

			valMap.put("Total", total);
			valMap.put("Count", count);
			float c = 0;
			if( count>0 ) {
				c = total/count;
			}
			valMap.put("Average", c);
			valMap.put(month, encCount);

		}

		Iterator<String> ite = list.keySet().iterator();
		String[] keys = {"Name", "Total", "Count", "Average", "January", "February", "March", "April", "May", "June", "July"};
		sb.append("Id");
		for(String key: keys) {				
			sb.append(", " + key );
		}			
		sb.append(",average\n");		
		while(ite.hasNext()) {
			//
			HashMap<String, Object> valMap = list.get(ite.next());
			sb.append(valMap.get("Id"));
			for(String key: keys) {				
				if( valMap.get(key) != null) {
					sb.append(", " + valMap.get(key) );
				} else {
					sb.append(", -");
				}
			}						
			sb.append("\n");			
		}

		System.out.println("\n\n\n" + sb.toString());

		try (FileWriter file = new FileWriter("/Users/ramanathan-0940/Desktop/UsageReport_2018.csv")) {
			file.write(sb.toString());
		}

	}

}
