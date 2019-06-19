package com.zoho.charm.project.apollo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class DrugDosageGenerator {
	static String folderName = "/home/local/ZOHOCORP/aravind-5939/Desktop/Apollo Integration/CSV/";
	private static String delimiter = "\\|";

	public static void main(String[] args) throws IOException {
		BufferedReader reader = null;
		BufferedWriter writer = null;
		try {
			reader = new BufferedReader(new FileReader(folderName.concat("Andhra Pradesh Item Master for API.csv")));
			writer = new BufferedWriter(
					new FileWriter(folderName.concat("Andhra Pradesh Item Master for API With Dosage.csv")));

			String line;
			while ((line = reader.readLine()) != null) {
				String[] values = line.split(delimiter);
				String itemName = values[1];

				String dispenseunit = getDispenseUnit(itemName);
				if (dispenseunit == null) {
					writer.write(line);
					writer.write("|");
					// writer.write(dispenseunit);
					writer.write(System.lineSeparator());
				}
			}
			System.out.println(countsMap.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			writer.flush();
			writer.close();
			reader.close();
		}

	}

	static HashMap<String, String> nameVsDosage = new HashMap<>();
	static {
		nameVsDosage.put("TAB", "Tablet(s)");

		nameVsDosage.put("CAP", "Capsule(s)");
		nameVsDosage.put("CAPS", "Capsule(s)");

		nameVsDosage.put("OINTMENT", "Tube(s)");
		nameVsDosage.put("OINT", "Tube(s)");
		nameVsDosage.put("CREAM", "Tube(s)");
		nameVsDosage.put("CRM", "Tube(s)");
		nameVsDosage.put("GEL", "Tube(s)");
		nameVsDosage.put("WASH", "Tube(s)");

		nameVsDosage.put("SYRUP", "Bottle(s)");
		nameVsDosage.put("SYP", "Bottle(s)");
		nameVsDosage.put("LOTION", "Bottle(s)");
		nameVsDosage.put("SOLUTION", "Bottle(s)");
		nameVsDosage.put("SPRAY", "Bottle(s)");
		nameVsDosage.put("SERUM", "Bottle(s)");
		nameVsDosage.put("SHAMPOO", "Bottle(s)");

		nameVsDosage.put("INJ", "Injection(s)");

		nameVsDosage.put("DROP", "Drop(s)");
		nameVsDosage.put("DROPS", "Drop(s)");

		nameVsDosage.put("POWDER", "Gram(s)");

		nameVsDosage.put("SOAP", "Bar(s)");
		nameVsDosage.put("BAR", "Bar(s)");

		nameVsDosage.put("SACHETS", "Sachet(s)");
		nameVsDosage.put("SACHET", "Sachet(s)");
		// nameVsDosage.put("" ,"" );

	}
	static String dispenseunit = null;
	static HashMap<String, Integer> countsMap = new HashMap<>();

	private static String getDispenseUnit(String itemName) {
		dispenseunit = null;
		for (String dosageName : nameVsDosage.keySet()) {
			if (itemName.contains(dosageName)) {
				dispenseunit = nameVsDosage.get(dosageName);

				if (countsMap.containsKey(dispenseunit)) {
					countsMap.put(dispenseunit, countsMap.get(dispenseunit) + 1);
				} else {
					countsMap.put(dispenseunit, 1);
				}

				break;
			}
		}

		return dispenseunit;
	}
}
