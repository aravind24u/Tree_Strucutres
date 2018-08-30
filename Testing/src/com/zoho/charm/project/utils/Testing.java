package com.zoho.charm.project.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.adventnet.iam.xss.IAMEncoder;
import com.zoho.charm.project.utils.encoder.AdditionalUtils;
import com.zoho.charm.project.utils.encoder.CheckEncoding;
import com.zoho.charm.project.utils.encoder.EncodingConstants;

public class Testing {

	public static void main(String[] args) throws Exception {


		String field = "\\n";
		System.out.println(IAMEncoder.encodeHTML(field).replace("\\n", "br"));
		
//		while(field.contains("\n")) {
//			field = field.replace("\n", "");
//		}
		
		//		Pattern pattern = Pattern.compile("(IAMEncoder.encode([^<%=]*)\\?)");
//		System.out.println(pattern.toString());
//		System.out.println(AutomatedEncoding.pickScriplets.toString());
		
		//checkEncoding();
		// AutomatedEncoding.doOutputEncoding("<div class=\"flt \" id=\"modifiersDiv\"
		// style=\"display:<%=(userDetails.getCountry()==null||userDetails.getCountry().equals(\"us\"))?\"block\":\"none\"%>\">");
	}

	public static void checkJars() throws IOException {
		String location = "/home/local/ZOHOCORP/aravind-5939/Build_Downloads/Pharmacy_Build/AdventNet/Sas/tomcat/webapps/ROOT/WEB-INF/lib/";

		File file = new File("/home/local/ZOHOCORP/aravind-5939/Desktop/List of JARs.txt");

		BufferedReader reader = new BufferedReader(new FileReader(file));

		String line = reader.readLine();

		while (line != null) {
			File jarFile = new File(location.concat(line));
			if (!jarFile.exists()) {
				System.out.println(line);
			}
			line = reader.readLine();
		}
		reader.close();
	}

	public static void checkEncoding() throws Exception {

		String path = "/home/local/ZOHOCORP/aravind-5939/My_Branch/charmphr/webapps/phr";
		List<File> files = new ArrayList<>();
		AdditionalUtils.populateFileNames(path, files, ".jsp");
		AdditionalUtils.populateFileNames(path, files, ".jspf");

		String[] fileNames = new String[files.size()];
		int i = 0;
		for (File file : files) {
			fileNames[i++] = file.getAbsolutePath().replace(path, "");
		}

		File errorFile = new File(EncodingConstants.OUTPUT_FILES_FOLDER + "M21 PHR.html");
		StringBuilder errorsBuilder = new StringBuilder();

		Integer styleCounter = 1;
		List<String> encodingList = new ArrayList<>();

		for (String fileName : fileNames) {
			styleCounter = CheckEncoding.checkEncode(path, fileName, errorsBuilder,
					styleCounter, encodingList);
		}
		System.out.println("\nWriting Errors Before Encoding");
		AdditionalUtils.writeFile(errorFile, errorsBuilder);
		System.out.println("\n\nTotal Number of Errors Before encoding : " + encodingList.size());
	}
}
