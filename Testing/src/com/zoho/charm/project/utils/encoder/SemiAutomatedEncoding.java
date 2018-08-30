package com.zoho.charm.project.utils.encoder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.regex.Matcher;

public class SemiAutomatedEncoding {

	public static final Logger LOGGER = Logger.getLogger(SemiAutomatedEncoding.class.getName());

	public static Integer count = 1;
	private static Scanner scanner = new Scanner(System.in);
	public static Boolean exit = Boolean.FALSE;
	public static HashMap<String, ArrayList<String>> ignoreList = null;

	public static String manuallyEncodeScripplets(String fileName, String line, Integer lineNo) throws Exception {

		String result = line;

		if (ignoreList != null) {
			if (ignoreList.containsKey(fileName) && ignoreList.get(fileName).contains(line.trim())) {
				System.out.println("Ignoring line : \n" + line + "\n of File : \n" + fileName);
				return line;
			}
		} else {
			readIgnoreList();
		}

		if (CheckEncoding.isScripletEncodingNeeded(line)) {
			System.out.println(System.lineSeparator());
			System.out.println(System.lineSeparator());
			System.out.println(System.lineSeparator());
			System.out.println(System.lineSeparator());
			System.out.println(System.lineSeparator());
			System.out.println(System.lineSeparator());
			System.out.println(System.lineSeparator());
			System.out.println("Line : \n");
			System.out.println(line.trim());

			Matcher matcher = AutomatedEncoding.pickScriplets.matcher(line);

			while (matcher.find()) {
				if (exit) {
					break;
				}
				String text = matcher.group(1);
				String s1 = text.trim().toLowerCase();
				if (!s1.startsWith("server_path") && !s1.startsWith("iamencoder") && !s1.startsWith("i18n")
						&& !s1.startsWith("resourcepath") && !s1.startsWith("securityutil") && !s1.startsWith("csrf")
						&& !s1.startsWith("dateformat") && !s1.startsWith("charmutil") && !s1.contains(".getmsg")
						&& !s1.contains("stringescapeutils") && !s1.contains("urlencoder")) {
					count++;
					Integer choice = null;
					if (text.trim().length() == 1) {
						choice = 6;
					} else if (s1.contains("?") && s1.contains(":")) {
						String[] values = text.split("\\?")[1].split(":");
						String trueValue = values[0];
						String falseValue = values[1];
						if (trueValue.trim().startsWith("\"") && falseValue.trim().startsWith("\"")) {
							choice = 6;
						} else {
							choice = 7;
						}
					} else {
						System.out.println("\nScripplet Content : \t" + text.trim());
						if (EncodingConstants.SHOULD_PRINT_ENCODING_OPTIONS) {
							printEncodingOptions();
						}
						System.out.print("\nYour Input : ");
						choice = scanner.nextInt();
					}
					String encodingType = null;
					Boolean ignore = Boolean.FALSE;
					switch (choice) {

					case 1:
						encodingType = "encodeHTML";
						break;
					case 2:
						encodingType = "encodeHTMLAttribute";
						break;
					case 3:
						encodingType = "encodeCSS";
						break;
					case 4:
						encodingType = "encodeJavaScript";
						break;
					case 5:
						encodingType = "encodeURL";
						break;
					case 6:
						break;
					case 0:
						exit = Boolean.TRUE;
					default:
						ignore = Boolean.TRUE;

					}
					if (!ignore) {
						if (encodingType != null) {
							System.out.println("(" + encodingType + ")");
							String foundText = "<%=" + text + "%>";
							String templine = line.substring(0, line.indexOf(foundText) + foundText.length());
							String templine2 = templine.replace(foundText,
									"<%= IAMEncoder." + encodingType + "( String.valueOf( " + text.trim() + " )) %>");
							line = line.replace(templine, templine2);
							Encode.includeIAM = Boolean.TRUE;
						} else {
							System.out.println("Add no output Encoding");
							if (!line.contains("<%--NO OUTPUTENCODING--%>")) {
								line = line.concat("<%--NO OUTPUTENCODING--%>");
							}
						}
					} else {
						if (!exit) {
							System.out.println("Ignoring this instance");
							writeIgnore(fileName, line.trim());
						}
					}
				}
			}
			if (EncodingConstants.IS_CHECK_REQUIRED && !exit) {
				System.out.println("Your Result : ");
				System.out.println(line.trim());
				System.out.println("Hit 1 to confirm");
				Integer choice = scanner.nextInt();
				if (choice != 1) {
					line = result;
				}
			} else {
				System.out.println(line.trim());
			}

		}

		return line;
	}

	public static void writeIgnore(String ignoredFileName, String ignoreLine) throws Exception {

		if (ignoreList.containsKey(ignoredFileName)) {
			ignoreList.get(ignoredFileName).add(ignoreLine);
		} else {
			ArrayList<String> lines = new ArrayList<>();
			lines.add(ignoreLine);
			ignoreList.put(ignoredFileName, lines);
		}
		File ignoredFiles = new File(EncodingConstants.IGNORED_FILES);
		BufferedWriter writer = new BufferedWriter(new FileWriter(ignoredFiles, Boolean.TRUE));
		writer.write(
				ignoredFileName.concat(EncodingConstants.DELIMITER).concat(ignoreLine).concat(System.lineSeparator()));
		writer.close();
	}

	public static void readIgnoreList() throws IOException {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(EncodingConstants.IGNORED_FILES));
			ignoreList = new HashMap<>();
			String line = reader.readLine();
			while (line != null) {

				String[] words = line.split(EncodingConstants.DELIMITER);
				String fileName = words[0];
				String ignoredLine = words[1];
				if (ignoreList.containsKey(fileName)) {
					ignoreList.get(fileName).add(ignoredLine);
				} else {
					ArrayList<String> lines = new ArrayList<>();
					lines.add(ignoredLine);
					ignoreList.put(fileName, lines);
				}
				line = reader.readLine();
			}

		} catch (FileNotFoundException e) {
			LOGGER.severe("Ignore File not found.It will be created once a line is ignored");
			ignoreList = new HashMap<>();
		} catch (IOException e) {
			LOGGER.severe("Error While Reading the ignored list");
			System.out.println(System.lineSeparator());
			System.out.println(System.lineSeparator());
			LOGGER.severe("Exception : " + e.getMessage());
			System.out.println(System.lineSeparator());
			System.out.println(System.lineSeparator());
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
	}

	public static void printIgnored() {
		System.out.println(System.lineSeparator());
		System.out.println(System.lineSeparator());
		if (EncodingConstants.PRINT_IGNORED) {

			if (ignoreList != null && ignoreList.size() > 0) {
				System.out.println("Printing Ignored Files");
				for (String file : ignoreList.keySet()) {
					System.out.println(System.lineSeparator());
					System.out.println("File Name : " + file);
					for (String line : ignoreList.get(file)) {
						System.out.println(line);
					}
				}
			} else {
				System.out.println("No Ignored Files");
			}
		} else {
			System.out.println("Set print Ignored true in " + EncodingConstants.class.getSimpleName()
					+ ".java to print all the ignored lines");
		}
	}

	public static String manuallyEncodeCOutTag(String fileName, String line, Integer lineNo) throws Exception {

		String result = line;

		if (line.contains("/c:out")) {
			System.out.println(
					"the following line contains </c:out> tag , the program isnt programmed to handle this as handling this case would exponentially increase the complexity.There are totally less than 20 instances in charm context.");
			System.out.println("Hence adding it to the Ignored List");
			writeIgnore(fileName, line.trim());

		}

		if (ignoreList != null) {
			if (ignoreList.containsKey(fileName) && ignoreList.get(fileName).contains(line.trim())) {
				System.out.println("Ignoring line : \n" + line + "\n of File : \n" + fileName);
				return line;
			}
		} else {
			readIgnoreList();
		}

		if (line.contains("<c:out")) {
			System.out.println(System.lineSeparator());
			System.out.println(System.lineSeparator());
			System.out.println(System.lineSeparator());
			System.out.println(System.lineSeparator());
			System.out.println(System.lineSeparator());
			System.out.println(System.lineSeparator());
			System.out.println(System.lineSeparator());
			System.out.println("Line : \n");
			System.out.println(line.trim());

			line = encodeCOut(fileName, line);

			if (EncodingConstants.IS_CHECK_REQUIRED && !exit) {
				System.out.println("Your Result : ");
				System.out.println(line.trim());
				System.out.println("Hit 1 to confirm");
				Integer choice = scanner.nextInt();
				if (choice != 1) {
					line = result;
				}
			} else {
				System.out.println(line.trim());
			}
		}
		return line;
	}

	public static String encodeCOut(String fileName, String line) throws Exception {

		if (exit) {
			return line;
		}
		if (ignoreList != null) {
			if (ignoreList.containsKey(fileName) && ignoreList.get(fileName).contains(line.trim())) {
				System.out.println("Ignoring line : \n" + line + "\n of File : \n" + fileName);
				return line;
			}
		} else {
			readIgnoreList();
		}

		if (line.contains("c:out")) {
			count++;
			Integer beginIndex = line.indexOf("<c:out");
			Integer endIndex = line.substring(beginIndex).indexOf("/>") + beginIndex + 2;
			String subString = line.substring(beginIndex, endIndex);
			line = replaceWithUserInput(fileName, line, subString);
			line = encodeCOut(fileName, line);
		}

		return line;
	}

	public static String replaceWithUserInput(String fileName, String line, String subString) throws Exception {

		System.out.println("\nC Out Tag Content : \t" + subString.trim());
		if (EncodingConstants.SHOULD_PRINT_ENCODING_OPTIONS) {
			printEncodingOptions();
		}
		System.out.print("\nYour Input : ");
		Integer choice = scanner.nextInt();
		String encodingType = null;
		Boolean ignore = Boolean.FALSE;
		switch (choice) {

		case 1:
			encodingType = "html";
			break;
		case 2:
			encodingType = "htmlAttr";
			break;
		case 3:
			encodingType = "css";
			break;
		case 4:
			encodingType = "js";
			break;
		case 5:
			encodingType = "url";
			break;
		case 6:
			break;
		case 0:
			exit = Boolean.TRUE;
		default:
			ignore = Boolean.TRUE;

		}
		if (!ignore) {
			if (encodingType != null) {
				System.out.println("enc:" + encodingType);
				line = line.replace(subString, subString.replace("c:out", "enc:".concat(encodingType)));
				Encode.includeTld = Boolean.TRUE;
			} else {
				System.out.println("Add no output Encoding");
				if (!line.contains("<%--NO OUTPUTENCODING--%>")) {
					line = line.concat("<%--NO OUTPUTENCODING--%>");
				}
			}
		} else {
			if (!exit) {
				System.out.println("Ignoring this instance");
				writeIgnore(fileName, line.trim());
			}
		}

		return line;
	}

	public static void printEncodingOptions() {
		System.out.println("The pattern was unable to find the context,Please enter an option");
		System.out.print("1-HTML");
		System.out.print("\t2-HTML Attribute");
		System.out.print("\t3-CSS");
		System.out.print("\t4-JavaScript");
		System.out.println("\t5-URL");
		System.out.print("6-Add NO OUTPUTENCODING Comment");
		System.out.print("\t7-Add line to ignore list");
		System.out.print("\t0-Save & Quit( Auto encode Only )");
	}

}
