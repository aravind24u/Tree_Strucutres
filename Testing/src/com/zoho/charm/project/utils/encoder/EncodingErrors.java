package com.zoho.charm.project.utils.encoder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EncodingErrors {

	private static Pattern pattern = Pattern.compile("<%=(.*?)%>");

	public static void main(String[] args) throws Exception {
		//
		BufferedReader reader;
		StringBuilder sb = new StringBuilder();
		// Execute the following grep to get the output txt
		// find $PWD -iname "*.jsp*" -exec grep -Hn "<%=\|<c:out" {} \; >
		// ~/Desktop/Output.txt

		File inFile = new File("/home/local/ZOHOCORP/aravind-5939/Desktop/Output.txt");
		File outFile = new File("/home/local/ZOHOCORP/aravind-5939/Desktop/Out.html");
		try {
			reader = new BufferedReader(new FileReader(inFile));
			String line = reader.readLine();
			String cls = "odd", f = "";
			while (line != null) {
				//
				int firstPos = line.indexOf(":");
				int secondPos = firstPos + 1;

				while (!line.substring(secondPos, secondPos + 1).equals(":")) {
					secondPos++;
				}

				String file = line.substring(0, firstPos);
				String lineNo = line.substring(firstPos + 1, secondPos);
				String txt = line.substring(secondPos + 1).trim();

				if (!f.equals(file)) {
					f = file;
					if (cls.equals("odd")) {
						cls = "even";
					} else {
						cls = "odd";
					}
				}

				if (isOutputEncodingNeeded(txt)) {
					sb.append("<tr class='" + cls + "'><td>" + file + "</td><td>" + lineNo + "</td><td>"
							+ txt.replaceAll("<", "&lt;").replaceAll(">", "&gt;") + "</td></tr>");
				}

				// read next line
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (outFile.exists()) {
			outFile.delete();
			outFile.createNewFile();
		}

		try (FileWriter file = new FileWriter(outFile)) {
			file.write(
					"<html><head><style type='text/css'>table {border-collapse: collapse;} table, th, td {border: none;}.odd{border-bottom:1px solid #eff0f1; background-color:white;}.even{background-color:#eff0f1; border-bottom:1px solid white;}</style></head><body><table><tr><th>File</th><th>Line</th><th>Text</th></tr>"
							+ sb.toString() + "</table></body></html>");
			System.out.println("Successfully Copied to File...");
		}
	}

	private static boolean isOutputEncodingNeeded(String str) {
		//
		if (str.startsWith("<%--") || str.contains("<%-- NO OUTPUTENCODING --%>")) {
			return false;
		}

		ArrayList<String> list = getPatterns(str);
		boolean isOutputEncodingNeeded = false;

		if (list.size() == 0) {
			return true;
		}

		for (String s : list) {
			//
			s = s.trim().toLowerCase();
			if (!s.contains(".getmsg(") && !s.startsWith("server_path") && !s.startsWith("iamencoder")
					&& !s.startsWith("i18n") && !s.startsWith("resourcepath") && !s.startsWith("securityutil")
					&& !s.startsWith("csrf") && !s.startsWith("dateformat") && !s.startsWith("charmutil")) {
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

}