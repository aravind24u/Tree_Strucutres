package com.zoho.charm.project.utils.encoder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckEncoding {

	public static final Logger LOGGER = Logger.getLogger(CheckEncoding.class.getName());

	private static Pattern pattern = Pattern.compile("<%=(.*?)%>");

	public static Integer checkEncode(String folderName, String fileName, StringBuilder sb, Integer fileCounter,
			List<String> names) throws Exception {
		//
		BufferedReader reader;
		File inFile = new File(folderName + fileName);
		Boolean isWritten = Boolean.FALSE;

		try {
			reader = new BufferedReader(new FileReader(inFile));
			String line = reader.readLine();
			String cls = "odd";
			Integer count = 0;

			Integer lineNo = 1;
			while (line != null) {
				//
				String file = fileName;
				String txt = line;

				if (isOutputEncodingNeeded(txt)) {
					if (fileCounter % 2 == 0) {
						cls = "even";
					} else {
						cls = "odd";
					}
					isWritten = Boolean.TRUE;
					sb.append("<tr class='" + cls + "'><td>" + file + "</td><td>" + lineNo + "</td><td>"
							+ txt.replaceAll("<", "&lt;").replaceAll(">", "&gt;") + "</td></tr>");
					count++;
					names.add(txt);
				}

				// read next line
				line = reader.readLine();
				lineNo++;
			}
			reader.close();
			System.out.print(count + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (isWritten) {
				fileCounter++;
			}
		}
		return fileCounter;
	}

	public static boolean isOutputEncodingNeeded(String str) {
		//
		if (str.trim().startsWith("<%--") || (str.contains("NO OUTPUTENCODING"))) {
			return false;
		} else if (str.contains("c:out")) {
			return true;
		}

		ArrayList<String> list = getPatterns(str);
		boolean isOutputEncodingNeeded = false;

		if (list.size() == 0) {
			return false;
		}

		for (String s : list) {
			//
			s = s.trim().toLowerCase();
			if (!s.startsWith("server_path") && !s.startsWith("iamencoder") && !s.startsWith("i18n")
					&& !s.startsWith("resourcepath") && !s.startsWith("securityutil") && !s.startsWith("csrf")
					&& !s.startsWith("dateformat") && !s.startsWith("charmutil") && !s.contains(".getmsg")) {
				isOutputEncodingNeeded = true;
			}

		}

		return isOutputEncodingNeeded;

	}

	private static ArrayList<String> getPatterns(String str) {
		//
		ArrayList<String> list = new ArrayList<String>();
		Matcher matcher = pattern.matcher(str);

		while (matcher.find()) {
			list.add(matcher.group(1));
		}

		return list;
	}

	public static boolean isScripletEncodingNeeded(String str) {
		//
		if (str.trim().startsWith("<%--") || str.contains("NO OUTPUTENCODING")) {
			return false;
		}

		ArrayList<String> list = getPatterns(str);
		boolean isOutputEncodingNeeded = false;

		if (list.size() == 0) {
			return false;
		}

		for (String s : list) {
			//
			s = s.trim().toLowerCase();
			if (!s.startsWith("server_path") && !s.startsWith("iamencoder") && !s.startsWith("i18n")
					&& !s.startsWith("resourcepath") && !s.startsWith("securityutil") && !s.startsWith("csrf")
					&& !s.startsWith("dateformat") && !s.startsWith("charmutil") && !s.contains(".getmsg")) {
				isOutputEncodingNeeded = true;
			}

		}

		return isOutputEncodingNeeded;

	}
}
