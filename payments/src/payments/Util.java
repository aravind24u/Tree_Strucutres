package payments;

import java.io.FileReader;

import org.json.simple.parser.JSONParser;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class Util {
	
	public static void main(String[] args) throws Exception {
		//
		JSONArray jArr = new JSONArray();
		JSONArray gArr = new JSONArray();
		JSONParser parser = new JSONParser();
		JSONObject jObj = JSONObject.fromObject( parser.parse(new FileReader("/Users/ramanathan-0940/Desktop/Test.json")) );
		JSONArray arr = JSONArray.fromObject(jObj.get("lab_results"));
	
		int count = 0;
		for(int i=0; i<arr.size(); i++){
			//
			JSONObject obj = JSONObject.fromObject(arr.get(i));
			
			//System.out.println(jArr.contains( obj.get("medical_record_entry_id") ));
			//
			jArr.add(obj.get("medical_record_entry_id"));
			
			if( obj.has("group_id") ){
				//
				if( gArr.contains( obj.get("group_id") ) ){
					//
					gArr.add( obj.get("group_id") );
					
				} else {
					//
					gArr.add( obj.get("group_id") );
					count++;
					
				}
				
			} else {
				//
				count++;
				
			}
			
		}
		
		System.out.println(gArr);
		System.out.println(count);
		
	}
	
}
