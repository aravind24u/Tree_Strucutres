package com.zoho.charm.project.utils;

import java.text.MessageFormat;
import java.util.List;

public class FetchPatches {
	public static void main(String[] args) {
		String command = "hg export -o ~/Desktop/Pricing/MergePatches/{0}.patch -r  {1}";
		List<String> changeSets = CommonUtils.loadFile("/home/local/ZOHOCORP/aravind-5939/Desktop/ChangeSets.txt");

		changeSets.forEach(line -> {
			String[] values = line.split(":");
			System.out.println(MessageFormat.format(command, values[0].trim(), values[1].trim()));
		});
	}

}
