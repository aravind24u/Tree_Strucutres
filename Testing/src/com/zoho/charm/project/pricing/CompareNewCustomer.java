package com.zoho.charm.project.pricing;

import java.io.FileReader;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.tika.io.IOUtils;

import com.zoho.charm.project.utils.CommonUtils;

public class CompareNewCustomer {
	public static void main(String[] args) throws Exception {

		FileReader reader = new FileReader(CommonUtils.PRICING_HOME_DIR.concat("New_Practices_September_old.csv"));

		List<String> oldList = CommonUtils.getPracticeIdsToList(IOUtils.readLines(reader));

		reader = new FileReader(CommonUtils.PRICING_HOME_DIR.concat("New_Practices_September_new.csv"));

		List<String> newList = CommonUtils.getPracticeIdsToList(IOUtils.readLines(reader));

		Set<String> masterList = new HashSet<>();

		masterList.addAll(newList);
		masterList.addAll(oldList);

		masterList.forEach(practiceId -> {
			if(!newList.contains(practiceId)) {
				System.out.println(MessageFormat.format("Practice {0} is missing in new list", practiceId));
			}
			/*if(!oldList.contains(practiceId)) {
				System.out.println(MessageFormat.format("Practice {0} is missing in old list", practiceId));
			}*/
		});
	}
}
