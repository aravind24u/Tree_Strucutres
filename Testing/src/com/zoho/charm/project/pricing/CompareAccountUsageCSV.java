package com.zoho.charm.project.pricing;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.zoho.charm.project.utils.CommonUtils;

public class CompareAccountUsageCSV {

	private static String delimiter = ",";

	public static void main(String[] args) throws Exception {

		String month = "May";

		HashMap<String, Double> oldUsage = CommonUtils.getHashMapFromFile("Usage_" + month + "_2019_Old.csv");

		HashMap<String, Double> newUsage = CommonUtils.getHashMapFromFile("Usage_" + month + "_2019_New.csv");

		/*
		 * Set<String> mainList = oldUsage.keySet(); Set<String> subList =
		 * newUsage.keySet(); Integer count = 0; for(String practiceId : mainList) {
		 * if(! subList.contains(practiceId)) { count++; System.out.println(practiceId);
		 * } } System.out.println("Count : " + count);
		 */

		Set<String> practiceIds = new HashSet<>();

		practiceIds.addAll(oldUsage.keySet());
		System.out.println("Length post adding old ids : " + practiceIds.size());
		practiceIds.addAll(newUsage.keySet());
		System.out.println("Length post adding new ids : " + practiceIds.size());
		System.out.println("Length new ids : " + newUsage.keySet().size());
		BufferedWriter writer = new BufferedWriter(
				new FileWriter(CommonUtils.PRICING_HOME_DIR.concat("DifferenceInCharge.csv")));
		try {
			writer.write("Practice Id,New Cost,Old Cost,Difference(Old-New)");
			for (String practiceId : practiceIds) {
				Double newCost = newUsage.get(practiceId);
				Double oldCost = oldUsage.get(practiceId);

				if (isNonIndianPractice(practiceId) && (newCost == null || oldCost == null || oldCost - newCost != 0)) {
					writer.write(System.lineSeparator());
					writer.append(practiceId);

					writer.append(delimiter);
					writer.append(newCost == null ? "" : newCost.toString());
					writer.append(delimiter);
					writer.append(oldCost == null ? "" : oldCost.toString());
					writer.append(delimiter);
					oldCost = oldCost == null ? 0D : oldCost;
					newCost = newCost == null ? 0D : newCost;
					Double difference = oldCost - newCost;
					writer.append(difference.toString());
					writer.append(delimiter);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			writer.flush();
			writer.close();
		}
		System.out.println("Finished Comparing charges");
	}

	static String indianPractices = "257000001111010,257000002257012,257000001752010,257000001368009,257000007225155,257000001300029,257000000290001,257000000954001,257000000136001,257000000138001,257000000191003,257000000642001,257000000705005,257000000761001,257000000833001,257000000500005,257000000602001,257000001246015,257000001635009,257000007545007,257000007625009,257000007621001,257000007713009,257000007785111,257000005833043,257000008058017,257000008858001,257000009012037,257000009106169,257000009300011,257000009044081,257000009322005,257000009445121,257000009680057,257000009697059,257000009695075,257000009695083,257000009695079,257000009720005,257000009004225,257000009458051,257000009458089,257000009458313,257000009458317,257000009458321,257000009465111,257000009465115,257000009467129,257000009467133,257000009467153,257000009482029,257000009482033,257000009509067,257000009509071,257000009509075,257000009509079,257000009580087,257000009580091,257000009580095,257000009580099,257000009595009,257000009595017,257000009621015,257000009621019,257000009621023,257000009656001,257000009656005,257000009658001,257000009659051,257000009734015,257000009271043,257000009774061,257000009832039,257000009832035,257000009829045,257000009848045,257000009848049,257000009839171,257000009839291,257000009867069,257000009867083,257000009867079,257000009867075,257000010014159,257000010055001,257000010087001,257000010111045,257000009769001,257000009769005,257000009769103,257000009839297,257000009839301,257000009846075,257000009873077,257000009873133,257000009873137,257000009873141,257000009941277,257000009941281,257000010112007,257000010113045,257000010184227,257000010184235,257000010187203,257000010185083,257000010185151,257000010187207,257000010184231,257000010234007,257000010234011,257000010268121,257000010263071,257000010268115,257000010268125,257000010263035,257000010269025,257000010293047,257000010300301,257000010357027,257000010418121,257000010413831,257000010438101,257000011008003,257000011524079,257000005624005,257000012647069,257000012864071,257000012823027,257000012908079,257000012936401,257000013177049,257000013177049,257000013354005,257000013494737,257000013490061,257000013907009,257000013078073,257000013354005,257000014360045,257000014103783,257000014103753,257000014096057,257000014701231,257000014103829,257000014636761";

	static String[] indianPracticeIDS = indianPractices.split(",");

	static List<String> indianPracticeIds = Arrays.asList(indianPracticeIDS);

	public static Boolean isNonIndianPractice(String practiceId) {
		return !indianPracticeIds.contains(practiceId);
	}

}
