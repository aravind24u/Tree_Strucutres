package com.zoho.charm.project.apollo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;

import com.zoho.charm.project.utils.CommonUtils;

public class DrugDosageGenerator {
	private static String delimiter = "\\|";

	public static void main(String[] args) throws IOException {
		BufferedReader reader = null;
		BufferedWriter writer = null;
		try {
			reader = new BufferedReader(new FileReader(CommonUtils.APOLLO_HOME_DIR.concat("Andhra Pradesh Item Master for API.csv")));
			writer = new BufferedWriter(
					new FileWriter(CommonUtils.APOLLO_HOME_DIR.concat("Andhra Pradesh Item Master for API With Dosage.csv")));

			String line;
			HashMap<String, Integer> repeatedWord = new HashMap<>();
			while ((line = reader.readLine()) != null) {
				String[] values = line.split(delimiter);
				String itemName = values[1];

				String dispenseunit = getDispenseUnit(itemName);
				if (dispenseunit == null) {
					writer.write(line);
					writer.write("|");
					// writer.write(dispenseunit);
					writer.write(System.lineSeparator());

					String[] itemNameWords = itemName.split(" ");
					for (String word : itemNameWords) {
						word = word.trim();
						if (repeatedWord.containsKey(word)) {
							repeatedWord.put(word, repeatedWord.get(word) + 1);
						} else {
							repeatedWord.put(word, 1);
						}
					}
				}
			}
			countsMap.keySet().forEach(keyword -> {
				System.out.println(keyword + " : " + countsMap.get(keyword));
			});
			System.out.println("---------------------------");
			repeatedWord.keySet().forEach(keyword -> {
				if (repeatedWord.get(keyword) > 5) {
					System.out.println(keyword + " : " + repeatedWord.get(keyword));
				}
			});
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
		nameVsDosage.put("TABS", "Tablet(s)");
		nameVsDosage.put("SUPPOSITORIES", "Tablet(s)");
		nameVsDosage.put("SUPPOSITORY", "Tablet(s)");
		nameVsDosage.put("SUPPOSITORY", "Tablet(s)");
		nameVsDosage.put("SUPP", "Tablet(s)");
		nameVsDosage.put("SUPPOSTORIES", "Tablet(s)");

		nameVsDosage.put("CAP", "Capsule(s)");
		nameVsDosage.put("CAPS", "Capsule(s)");

		nameVsDosage.put("OINTMENT", "Tube(s)");
		nameVsDosage.put("OINT", "Tube(s)");
		nameVsDosage.put("CREAM", "Tube(s)");
		nameVsDosage.put("CRM", "Tube(s)");
		nameVsDosage.put("GEL", "Tube(s)");
		nameVsDosage.put("WASH", "Tube(s)");
		nameVsDosage.put("PASTE", "Tube(s)");

		nameVsDosage.put("SYRUP", "Bottle(s)");
		nameVsDosage.put("SYP", "Bottle(s)");
		nameVsDosage.put("SYR", "Bottle(s)");
		nameVsDosage.put("LOTION", "Bottle(s)");
		nameVsDosage.put("LOTTION", "Bottle(s)");
		nameVsDosage.put("SOLUTION", "Bottle(s)");
		nameVsDosage.put("SOLU", "Bottle(s)");
		nameVsDosage.put("SERUM", "Bottle(s)");
		nameVsDosage.put("SHAMPOO", "Bottle(s)");
		nameVsDosage.put("SUSPEN", "Bottle(s)");
		nameVsDosage.put("SUSPENSION", "Bottle(s)");
		nameVsDosage.put("SUSP", "Bottle(s)");
		nameVsDosage.put("SUS", "Bottle(s)");
		nameVsDosage.put("LIQ", "Bottle(s)");
		nameVsDosage.put("BOTTLE", "Bottle(s)");

		nameVsDosage.put("INJ", "Injection(s)");
		nameVsDosage.put("VACCINE", "Injection(s)");
		nameVsDosage.put("DISPO PEN", "Injection(s)");
		nameVsDosage.put("DISPO PEN", "Injection(s)");

		nameVsDosage.put("DROP", "Drop(s)");
		nameVsDosage.put("DROPS", "Drop(s)");

		nameVsDosage.put("POWDER", "Gram(s)");
		nameVsDosage.put("POWD", "Gram(s)");

		nameVsDosage.put("SOAP", "Bar(s)");
		nameVsDosage.put("BAR", "Bar(s)");

		nameVsDosage.put("SACHETS", "Sachet(s)");
		nameVsDosage.put("SACHET", "Sachet(s)");

		nameVsDosage.put("VIAL", "Vial(s)");
		nameVsDosage.put("VAIL", "Vial(s)");

		nameVsDosage.put("INHALER", "Inhaler(s)");
		nameVsDosage.put("TRANSHALER", "Inhaler(s)");

		nameVsDosage.put("CARTRIDGES", "Catridge(s)");
		nameVsDosage.put("CARTRIDGE", "Catridge(s)");
		nameVsDosage.put("CORTRIDGE", "Catridge(s)");
		nameVsDosage.put("CARTIDGE", "Catridge(s)");

		nameVsDosage.put("Spray", "Spray(s)");
		nameVsDosage.put("Spary", "Spray(s)");

		nameVsDosage.put("Patch", "Patch(s)");

		nameVsDosage.put("Kit", "Kit(s)");

		nameVsDosage.put("RESPULES", "Repsule(s)");

		nameVsDosage.put("GRANULES", "Pack(s)");

		// nameVsDosage.put("" ,"" );

	}
	static String dispenseunit = null;
	static HashMap<String, Integer> countsMap = new HashMap<>();

	private static String getDispenseUnit(String itemName) {
		dispenseunit = null;
		if (StringUtils.endsWithIgnoreCase(itemName, "ML")) {
			dispenseunit = "Bottle(s)";
		} else {
			for (String dosageName : nameVsDosage.keySet()) {
				if (StringUtils.containsIgnoreCase(itemName, dosageName)) {
					dispenseunit = nameVsDosage.get(dosageName);
					break;
				}
			}
		}
		if (countsMap.containsKey(dispenseunit)) {
			countsMap.put(dispenseunit, countsMap.get(dispenseunit) + 1);
		} else {
			countsMap.put(dispenseunit, 1);
		}
		return dispenseunit;
	}
}
