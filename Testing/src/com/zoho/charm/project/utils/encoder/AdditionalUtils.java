package com.zoho.charm.project.utils.encoder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

public class AdditionalUtils {

	public static final Logger LOGGER = Logger.getLogger(AdditionalUtils.class.getName());

	public static ArrayList<String> loadFileNames(String fileName) {
		ArrayList<String> fileNames = new ArrayList<>();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(fileName));
			String line = reader.readLine();
			while (line != null) {
				if (StringUtils.isNotEmpty(line.trim())) {
					fileNames.add(line.trim());
				}
				line = reader.readLine();

			}
		} catch (Exception e) {
			System.out.println("File with the list of file names : " + EncodingConstants.FILE_NAMES_LOCATION
					+ " is not found , so moving on to the next method to get file names\n\n");
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
				}
			}
		}

		return fileNames;
	}

	public static void writeFile(File errorFile, StringBuilder errors) {

		BufferedWriter writer = null;

		try {
			if (errorFile.exists()) {
				errorFile.delete();
				errorFile.createNewFile();
			}

			writer = new BufferedWriter(new FileWriter(errorFile));
			if (errors.length() > 0) {

				writer.write(
						"<html><head><style type='text/css'>table {border-collapse: collapse;} table, th, td {border: none;}.odd{border-bottom:1px solid #ccc; background-color:white;}.even{background-color:#ccc; border-bottom:1px solid white;}</style></head><body><table style='line-height: 2.5;'><tr><th>File</th><th>Line</th><th>Text</th></tr>"
								+ errors.toString() + "</table></body></html>");
			} else {
				writer.write("<h2 style='position: fixed; left: 40%;top: 50%;'>Hooray ! No Errors Were Found</h2>");
			}
			System.out.println("Successfully Copied Errors to File : " + errorFile.getAbsolutePath());
		} catch (Exception e) {
			System.out.println("Error While writing file : " + errorFile.getName());
		} finally {
			if (writer != null) {
				try {
					writer.flush();
					writer.close();
				} catch (Exception e) {
					System.out.println("Error while closing the writer while writing " + errorFile.getName());
				}

			}
		}

	}

	public static void removeSpaceBetweenIam(String fileName) throws IOException {

		BufferedReader reader;
		StringBuilder sb = new StringBuilder();
		System.out.println("Input file :" + fileName);
		File inFile = new File(fileName);
		File outFile = new File(fileName);

		reader = new BufferedReader(new FileReader(inFile));
		String line = reader.readLine();
		while (line != null) {

			sb.append(line.replaceAll("IAMEncoder. encode", "IAMEncoder.encode"));
			sb.append(System.lineSeparator());

			line = reader.readLine();
		}
		reader.close();
		if (outFile.exists()) {
			outFile.delete();
			outFile.createNewFile();
		}

		try (BufferedWriter file = new BufferedWriter(new FileWriter(outFile))) {
			file.write(sb.toString());
			System.out.println("Fixed File :" + inFile.getName());
		}
	}

	public static void replaceBackToCout(String fileName) throws IOException {

		BufferedReader reader;
		StringBuilder sb = new StringBuilder();
		System.out.println("Input file :" + fileName);
		File inFile = new File(fileName);
		File outFile = new File(fileName);

		reader = new BufferedReader(new FileReader(inFile));
		String line = reader.readLine();
		while (line != null) {

			line = line.replace("<enc:css", "<c:out");
			line = line.replace("<enc:js", "<c:out");
			line = line.replace("<enc:htmlAttr", "<c:out");
			line = line.replace("<enc:html", "<c:out");
			line = line.replace("<enc:url", "<c:out");
			sb.append(line);
			sb.append(System.lineSeparator());

			line = reader.readLine();
		}
		reader.close();
		if (outFile.exists()) {
			outFile.delete();
			outFile.createNewFile();
		}

		try (BufferedWriter file = new BufferedWriter(new FileWriter(outFile))) {
			file.write(sb.toString());
			System.out.println("Fixed File :" + inFile.getName());
		}
	}

	public static void checkIfEncodingIsPresent(String fileName) {
		try {
			String content = FileUtils.readFileToString(new File(fileName), StandardCharsets.UTF_8);
			if (!content.contains("IAMEncoder.") && !content.contains("<enc:")) {
				System.out.println(fileName.replace(EncodingConstants.WORKSPACE_LOCATION, ""));
			}
		} catch (Exception e) {
			System.out.println("Error while reading file : " + fileName);
		}

	}

	public static void checkIfEncodingWasAdded(String fileName) {
		String m21WorkspaceLocation = "/home/local/ZOHOCORP/aravind-5939/My_Branch/charmehr/";
		try {
			String encodedContent = FileUtils.readFileToString(new File(fileName), StandardCharsets.UTF_8);
			if (!encodedContent.contains("enc:")) {
				String m21Content = FileUtils.readFileToString(
						new File(fileName.replace(EncodingConstants.WORKSPACE_LOCATION, m21WorkspaceLocation)),
						StandardCharsets.UTF_8);
				if (StringUtils.countMatches(encodedContent, "IAMEncoder.") == StringUtils.countMatches(m21Content,
						"IAMEncoder.")) {
					if (m21Content.contains("IAMEncoder.")) {
						System.out.println(fileName.replace(EncodingConstants.WORKSPACE_LOCATION, ""));
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Error while reading file : " + fileName);
		}
	}

	public static void findDuplicates(String[] fileNames) {
		try {
			File outFile = new File(EncodingConstants.OUTPUT_FILES_FOLDER + "regexIssues.html");

			StringBuilder issuesBuilder = new StringBuilder();
			for (String fileName : fileNames) {
				checkForDuplicateReplacement(EncodingConstants.WORKSPACE_LOCATION + fileName, issuesBuilder);
			}
			writeFile(outFile, issuesBuilder);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void checkForDuplicateReplacement(String fileName, StringBuilder issuesBuilder) throws Exception {
		BufferedReader reader;
		File inFile = new File(fileName);

		reader = new BufferedReader(new FileReader(inFile));
		String line = reader.readLine();
		int lineNo = 1;
		while (line != null) {
			HashMap<String, Integer> map = new HashMap<>();
			Matcher matcher = AutomatedEncoding.pickScriplets.matcher(line);

			while (matcher.find()) {
				String matchedText = matcher.group(1).trim();
				if (map.containsKey(matchedText)) {
					map.put(matchedText, map.get(matchedText) + 1);
				} else {
					map.put(matchedText, 1);
				}
			}
			for (String key : map.keySet()) {
				if (map.get(key) > 1) {
					key = key.replace("IAMEncoder.encodeHTML(", "");
					key = key.replace("IAMEncoder.encodeHTMLAttribute(", "");
					key = key.replace("IAMEncoder.encodeURL(", "");
					key = key.replace("IAMEncoder.encodeCSS(", "");
					key = key.replace("IAMEncoder.encodeJavaScript(", "");
					key = key.replace("String.valueOf(", "");
					key = key.replace(")", "");
					key = key.replace(")", "");

					if (key.trim().length() < 4) {
						String cls = (lineCounter++ % 2) == 0 ? "even" : "odd";
						issuesBuilder.append("<tr class='" + cls + "'><td>"
								+ fileName.replace(EncodingConstants.WORKSPACE_LOCATION, "") + "</td><td>" + lineNo
								+ "</td><td>" + line.trim().replaceAll("<", "&lt;").replaceAll(">", "&gt;")
								+ "</td></tr>");

						// CopyFiles.copyFiles(fileName.replace(EncodingConstants.WORKSPACE_LOCATION,
						// ""));
					}
				}
			}
			line = reader.readLine();
			lineNo++;
		}
		reader.close();
	}

	static int lineCounter = 0;

	public static void pullCSSEncoding(String fileName, StringBuilder cssEncoding, StringBuilder changes) {

		BufferedReader reader = null;

		System.out.println("Reading file : " + fileName.replace(EncodingConstants.WORKSPACE_LOCATION, ""));

		StringBuilder file = new StringBuilder();

		try {
			reader = new BufferedReader(new FileReader(fileName));

			String line = reader.readLine();
			Integer lineNo = 1;
			while (line != null) {
				if (line.contains("enc:js") || line.contains(".encodeJava")) {
					String result = line;
					String cssClass = (lineCounter++ % 2) == 0 ? "even" : "odd";
					cssEncoding.append("<tr class='" + cssClass + "'><td>"
							+ fileName.replace(EncodingConstants.WORKSPACE_LOCATION, "") + "</td><td>" + lineNo
							+ "</td><td>" + line.replaceAll("<", "&lt;").replaceAll(">", "&gt;") + "</td></tr>");

					line = checkAndReplaceCSS(line);

					if (!result.equals(line)) {
						changes.append("<tr><td style='padding-top: 40px;'>"
								+ fileName.replace(EncodingConstants.WORKSPACE_LOCATION, "")
								+ "</td><td style='padding-top: 40px;'>" + lineNo
								+ "</td><td style='padding-top: 40px;white-space: nowrap;'>"
								+ result.replaceAll("<", "&lt;").replaceAll(">", "&gt;").trim() + "</td></tr>");
						changes.append("<tr class='odd'><td style='text-align:right'>" + "" + "</td><td>" + ""
								+ "</td><td class='even' style='white-space: nowrap;'>"
								+ line.replaceAll("<", "&lt;").replaceAll(">", "&gt;").trim() + "</td></tr>");
					}

				}
				lineNo++;
				file.append(line);
				file.append(System.lineSeparator());
				line = reader.readLine();

			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

			writer.write(file.toString());
			writer.flush();
			writer.close();
		} catch (Exception e) {
			LOGGER.severe(e.getMessage());
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e) {
				}
				;
			}
		}

	}

	private static Scanner scanner = new Scanner(System.in);

	private static String checkAndReplaceCSS(String line) {

		Pattern[] patterns = { AutomatedEncoding.scriptletHref1, AutomatedEncoding.scriptletHref2 };
		Matcher matcher = null;
		for (Pattern pattern : patterns) {
			matcher = pattern.matcher(line);

			while (matcher.find()) {
				String match = matcher.group(1);
				if (match.contains("encodeHTMLAttribute")) {
					System.out.println(System.lineSeparator());
					System.out.println(System.lineSeparator());
					System.out.println(System.lineSeparator());
					System.out.println(line.trim());
					System.out.println(match + "\n1 - replaces , 2 - skips\nYour Input : ");
					Integer choice = null;
					if (line.contains("\"<%=" + match + "%>") || line.contains(";<%=" + match + "%>")
							|| line.contains("\" <%=" + match + "%>") || line.contains("; <%=" + match + "%>")
							|| line.contains("><%=" + match + "%>") || line.contains("> <%=" + match + "%>")) {
						choice = 1;
						System.out.println("Setting choice as 1");
					} else {
						choice = scanner.nextInt();
					}
					if (choice == 1) {
						String replacedtext = match.replace("encodeCSS", "encodeHTMLAttribute");
						line = line.replace(match, replacedtext);
					}
				}
			}

		}
		Pattern pickEncCss = Pattern.compile("<enc([^<]*)/>");

		matcher = pickEncCss.matcher(line);
		while (matcher.find()) {
			String match = matcher.group(1);
			if (match.contains(":css")) {
				System.out.println(System.lineSeparator());
				System.out.println(System.lineSeparator());
				System.out.println(System.lineSeparator());
				System.out.println(line.trim());
				System.out.println(match + "\n1 - replaces , 2 - skips\nYour Input : ");
				Integer choice = null;
				if (line.contains("\"<enc" + match + "/>") || line.contains(";<enc" + match + "/>")
						|| line.contains("\" <enc" + match + "/>") || line.contains("; <enc" + match + "/>")
						|| line.contains("><enc" + match + "/>") || line.contains("> <enc" + match + "/>")) {
					choice = 1;
					System.out.println("Setting choice as 1");
				} else {
					choice = scanner.nextInt();
				}
				if (choice == 1) {
					String replacedtext = match.replace(":css", ":htmlAttr");
					line = line.replace(match, replacedtext);
				}
			}
		}

		System.out.println("Result : " + line.trim());
		return line;
	}

	public static void hasStyleAsString(String fileName, StringBuilder errors) {

		BufferedReader reader = null;

		System.out.println("Reading file : " + fileName.replace(EncodingConstants.WORKSPACE_LOCATION, ""));

		try {
			reader = new BufferedReader(new FileReader(fileName));

			String line = reader.readLine();
			Integer lineNo = 1;
			while (line != null) {
				if (hasStyleAsString(line)) {
					String cls = (lineCounter++ % 2) == 0 ? "even" : "odd";
					errors.append("<tr class='" + cls + "'><td>"
							+ fileName.replace(EncodingConstants.WORKSPACE_LOCATION, "") + "</td><td>" + lineNo
							+ "</td><td>" + line.trim().replaceAll("<", "&lt;").replaceAll(">", "&gt;") + "</td></tr>");
				}
				lineNo++;
				line = reader.readLine();

			}

		} catch (Exception e) {
			LOGGER.severe(e.getMessage());
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e) {
				}
			}
		}

	}

	public static Boolean hasStyleAsString(String line) {

		String[] patterns = { "=\"style=", "=\" style=", "= \"style=", "= \" style=", "=\"style =", "=\" style =",
				"= \"style =", "= \" style =" };

		for (String pattern : patterns) {
			if (line.toLowerCase().contains(pattern)) {
				return Boolean.TRUE;
			}
		}

		return Boolean.FALSE;
	}

	public static void hasComponentWithEncoding(String fileName, StringBuilder errors) {

		BufferedReader reader = null;

		System.out.println("Reading file : " + fileName.replace(EncodingConstants.WORKSPACE_LOCATION, ""));

		try {
			reader = new BufferedReader(new FileReader(fileName));

			String line = reader.readLine();
			Integer lineNo = 1;
			while (line != null) {
				if (hasComponent(line)) {
					String cls = (lineCounter++ % 2) == 0 ? "even" : "odd";
					errors.append("<tr class='" + cls + "'><td>"
							+ fileName.replace(EncodingConstants.WORKSPACE_LOCATION, "") + "</td><td>" + lineNo
							+ "</td><td>" + line.trim().replaceAll("<", "&lt;").replaceAll(">", "&gt;") + "</td></tr>");
				}
				lineNo++;
				line = reader.readLine();

			}

		} catch (Exception e) {
			LOGGER.severe(e.getMessage());
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e) {
				}
			}
		}

	}

	public static Boolean hasComponent(String line) {

		if (line != null) {
			line = line.toLowerCase();

			String[] attributeTypes = { " = \"", "=\"", "= \"", " =\"", " = \" ", "=\" ", "= \" ", " =\" ", " = '",
					"='", "= '", " ='", " = ' ", "=' ", "= ' ", " =' " };

			String componentName = "src";
			for (String attribute : attributeTypes) {
				String[] patterns = { "<%= iamencoder.encodeurl", "<enc:url" };
				for (String patten : patterns) {
					if (line.contains(componentName.concat(attribute).concat(patten))) {

						return true;
						// Integer startindex = line.indexOf(componentName);
						// startindex += line.substring(startindex).indexOf("(");
						// int count = 0;
						// Boolean hasended = Boolean.FALSE;
						// Integer endIndex = null;
						// for (endIndex = startindex + 1; endIndex < line.length(); endIndex++) {
						// Character character = line.charAt(endIndex);
						// if(count == 0 && character == ')'){
						// hasended = Boolean.TRUE;
						// break;
						// }
						// if(character == ')') {
						// count--;
						// }
						// if(character == '(') {
						// count++;
						// }
						// }
						// if(hasended) {
						// String subString = line.substring(startindex , endIndex+1);
						// if(subString.contains("getelement") || subString.contains(".encode")) {
						// return Boolean.TRUE;
						// }
						// }
						// if(endIndex != line.length()) {
						// return hasDoubleEncoding(line.substring(endIndex));
						// }
					}
				}
			}
		}
		return Boolean.FALSE;

	}

	public static void checkJavaScript() throws IOException {

		String pathName = EncodingConstants.WORKSPACE_LOCATION.concat("webapps/charm/js/");

		List<File> files = getJSFileNameNames(pathName);

		File encodingInJS = new File(EncodingConstants.OUTPUT_FILES_FOLDER + "encodingInJS.html");
		StringBuilder errors = new StringBuilder();

		BufferedReader reader = null;
		try {
			for (File file : files) {
				reader = new BufferedReader(new FileReader(file));
				String line = reader.readLine();
				Integer lineNo = 0;
				while (line != null) {
					if (line.toLowerCase().contains("encodeuricomponent")) {
						String cls = (lineCounter++ % 2) == 0 ? "even" : "odd";
						errors.append("<tr class='" + cls + "'><td>" + file.getPath().replace(pathName, "")
								+ "</td><td>" + lineNo + "</td><td>"
								+ line.trim().replaceAll("<", "&lt;").replaceAll(">", "&gt;") + "</td></tr>");
					}
					lineNo++;
					line = reader.readLine();
				}
			}
			System.out.println("\nWriting JS Encoding Components");
			AdditionalUtils.writeFile(encodingInJS, errors);
			System.out.println("\nToal Number of lines : " + lineCounter);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
	}

	public static List<File> getJSFileNameNames(String pathName) {
		List<File> fileNames = new ArrayList<File>();

		File file = new File(pathName);

		populateFileNames(file, fileNames, ".js");

		System.out.println("\nTotal JS Files : " + fileNames.size());

		return fileNames;

	}

	public static void populateJSPFileNames(String path, List<String> fileNames) {
		List<File> files = new ArrayList<>();
		populateFileNames(path, files, "jsp");
		populateFileNames(path, files, "jspf");
		if (files != null && files.size() > 0) {
			for (File file : files) {
				fileNames.add(file.getAbsolutePath().replace(EncodingConstants.WORKSPACE_LOCATION, ""));
			}
		}
	}

	public static void populateFileNames(String path, List<File> fileNames, String fileType) {

		File file = new File(path);

		populateFileNames(file, fileNames, fileType);
	}

	public static void populateFileNames(File file, List<File> fileNames, String fileType) {
		if (file != null) {
			if (file.isDirectory()) {
				File[] files = file.listFiles();
				for (File file1 : files) {
					populateFileNames(file1, fileNames, fileType);
				}
			} else {
				if (file.getName().endsWith(fileType)) {
					System.out.println(
							"Adding File : " + file.getPath().replace(EncodingConstants.WORKSPACE_LOCATION, ""));
					fileNames.add(file);
				}
			}
		}
	}

	public static String encodeAllTagsInAttribute(String line, String attribute, String encodingType, Boolean isJSTL) {

		String[] attributeTypes = { " = \"", "=\"", "= \"", " =\"", " = '", "='", "= '", " ='" };

		for (String type : attributeTypes) {
			String result = AutomatedEncoding.getAttribute(line, attribute.concat(type));
			if (result != null) {
				System.out.println(result);
			}
		}
		return line;
	}

}
