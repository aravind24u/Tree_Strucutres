package com.zoho.charm.project.utils.encoder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

public class Encode {
	public static final Logger LOGGER = Logger.getLogger(Encode.class.getName());

	public static Boolean includeTld;
	public static Boolean includeIAM;
	public static Boolean noOutputEncodingComment;

	public static void encodeFile(String fileName, StringBuilder changes) throws Exception {

		BufferedReader reader;
		StringBuilder sb = new StringBuilder();
		System.out.println("Input file :" + fileName);
		File inFile = new File(fileName);
		File outFile = new File(fileName);
		includeTld = Boolean.FALSE;
		includeIAM = Boolean.FALSE;

		Integer lineNo = 1;

		try {
			reader = new BufferedReader(new FileReader(inFile));
			String line = reader.readLine();
			while (line != null) {

				noOutputEncodingComment = Boolean.FALSE;

				String result = line;

				if (EncodingConstants.ENABLE_AUTOMATED_ENCODING) {
					result = AutomatedEncoding.doOutputEncoding(line);
				}
				if (!SemiAutomatedEncoding.exit && EncodingConstants.ENABLE_SEMI_AUTOMATED_ENCODING) {
					result = SemiAutomatedEncoding.manuallyEncodeScripplets(fileName, result, lineNo);
					result = SemiAutomatedEncoding.manuallyEncodeCOutTag(fileName, result, lineNo);
				}
				if (noOutputEncodingComment && !result.contains("<%--NO OUTPUTENCODING--%>")) {
					result = result.concat("<%--NO OUTPUTENCODING--%>");
				}

				sb.append(result);
				sb.append(System.lineSeparator());

				if (!result.equals(line)) {
					changes.append("<tr><td style='padding-top: 40px;'>"
							+ fileName.replace(EncodingConstants.WORKSPACE_LOCATION, "")
							+ "</td><td style='padding-top: 40px;'>" + lineNo
							+ "</td><td style='padding-top: 40px;white-space: nowrap;'>"
							+ line.replaceAll("<", "&lt;").replaceAll(">", "&gt;").trim() + "</td></tr>");
					changes.append("<tr class='odd'><td style='text-align:right'>" + "" + "</td><td>" + ""
							+ "</td><td class='even' style='white-space: nowrap;'>"
							+ result.replaceAll("<", "&lt;").replaceAll(">", "&gt;").trim() + "</td></tr>");
				}
				// read next line
				line = reader.readLine();
				lineNo++;
			}
			reader.close();
		} catch (IOException e) {
			System.out.println(System.lineSeparator());
			System.out.println(System.lineSeparator());
			LOGGER.severe(
					"Error While processing file : " + fileName.replace(EncodingConstants.WORKSPACE_LOCATION, ""));

			LOGGER.severe("Exception : " + e.getMessage());
			System.out.println(System.lineSeparator());
			System.out.println(System.lineSeparator());
		}

		if (outFile.exists()) {
			outFile.delete();
			outFile.createNewFile();
		}

		try (BufferedWriter file = new BufferedWriter(new FileWriter(outFile))) {
			StringBuilder includesLine = new StringBuilder();
			if (includeTld) {
				if (sb.indexOf(EncodingConstants.TAG_LIB_INCLUDE) == -1) {
					includesLine.append(System.lineSeparator());
					includesLine.append(EncodingConstants.TAG_LIB_INCLUDE);
					includesLine.append(System.lineSeparator());
				}
			}
			if (includeIAM) {
				if (sb.indexOf(EncodingConstants.IAM_JAR_INCLUDE) == -1) {
					includesLine.append(System.lineSeparator());
					includesLine.append(EncodingConstants.IAM_JAR_INCLUDE);
					includesLine.append(System.lineSeparator());
				}
			}
			String comment = "--%>";
			Integer commentEnd = sb.indexOf(comment);
			String firstLine = sb.substring(0, commentEnd + comment.length());
			String secondLIne = includesLine.toString();
			String remainingFile = sb.substring(commentEnd + comment.length(), sb.length());

			String result = firstLine.concat(secondLIne.concat(remainingFile));
			file.write(result);
			System.out.println("Successfully Copied File : " + outFile.getName());
		}
	}

}
