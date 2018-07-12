package com.zoho.charm.project.utils.encoder;

import java.util.ArrayList;
import java.util.logging.Logger;

public class RunThisClass {

	public static final Logger LOGGER = Logger.getLogger(RunThisClass.class.getName());

	public static void main(String[] a) throws Exception {

		String[] fileNames = null;

		ArrayList<String> fileNamesList = AdditionalUtils.loadFileNames(EncodingConstants.FILE_NAMES_LOCATION);

		if (fileNamesList.size() > 0) {
			fileNames = new String[fileNamesList.size()];
			fileNames = fileNamesList.toArray(fileNames);
		} else {
			fileNames = EncodingConstants.FILE_NAMES;
		}

		// File errorsBeforeEncoding = new File(EncodingConstants.OUTPUT_FILES_FOLDER +
		// "before.html");
		// StringBuilder errorsBefore = new StringBuilder();

		// File errorsAfterEncoding = new File(EncodingConstants.OUTPUT_FILES_FOLDER +
		// "src.html");
		// StringBuilder errorsAfter = new StringBuilder();

		// Date date = new Date();
		//
		// String fileMarker = new
		// SimpleDateFormat(EncodingConstants.DATE_FORMAT).format(date);

		// File changesMade = new File(
		// EncodingConstants.OUTPUT_FILES_FOLDER +
		// "/Changes/CSSchanges-".concat(fileMarker).concat(".html"));
		// StringBuilder changes = new StringBuilder();
		//
		// Integer beforeStyleCounter = 1;
		// Integer afterStyleCounter = 1;
		//
		// List<String> beforeEncoding = new ArrayList<>();
		// List<String> afterEncoding = new ArrayList<>();

		// StringBuilder cssEncoding = new StringBuilder();

		for (String fileName : fileNames) {

			try {

				// System.out.print("\nTotal Number of Errors Before Encoding: ");
				// beforeStyleCounter =
				// CheckEncoding.checkEncode(EncodingConstants.WORKSPACE_LOCATION, fileName,
				// errorsBefore, beforeStyleCounter, beforeEncoding);
				//
				// Encode.encodeFile(EncodingConstants.WORKSPACE_LOCATION + fileName, changes);
				//
				// System.out.print("Total Number of Errors After encoding: ");
				// afterStyleCounter =
				// CheckEncoding.checkEncode(EncodingConstants.WORKSPACE_LOCATION, fileName,
				// errorsAfter, afterStyleCounter, afterEncoding);

				// Below Code is used to call functions to either revert the changes or to find
				// the list of changed files
				// Go through each function before calling them

				// ###################################################################

				// AdditionalUtils.removeSpaceBetweenIam(EncodingConstants.WORKSPACE_LOCATION +
				// fileName);

				// AdditionalUtils.replaceBackToCout(EncodingConstants.WORKSPACE_LOCATION +
				// fileName);

				// AdditionalUtils.checkIfEncodingIsPresent(EncodingConstants.WORKSPACE_LOCATION
				// +
				// fileName);

				// AdditionalUtils.checkIfEncodingWasAdded(EncodingConstants.WORKSPACE_LOCATION
				// +
				// fileName);

				// AdditionalUtils.pullCSSEncoding(EncodingConstants.WORKSPACE_LOCATION.concat(fileName),
				// cssEncoding,
				// changes);

				// AdditionalUtils.hasStyleAsString(EncodingConstants.WORKSPACE_LOCATION.concat(fileName),
				// errorsAfter);

				// AdditionalUtils.hasComponentWithEncoding(EncodingConstants.WORKSPACE_LOCATION.concat(fileName),
				// errorsAfter);

				// ###################################################################

			} catch (Exception e) {
				LOGGER.severe(e.getMessage());
			}
		}

		// AdditionalUtils.findDuplicates(fileNames);
		//
		// System.out.println("\nWriting Errors Before Encoding");
		// AdditionalUtils.writeFile(errorsBeforeEncoding, errorsBefore);

		// System.out.println("\nWriting Errors After Encoding");
		// AdditionalUtils.writeFile(errorsAfterEncoding, errorsAfter);

		// System.out.println("\nWriting the changes Made");
		// AdditionalUtils.writeFile(changesMade, changes);
		//
		// System.out.println("\n\nTotal Number of Errors Before encoding : " +
		// beforeEncoding.size());
		// System.out.println("Total Number of Errors After encoding : " +
		// afterEncoding.size());

		// SemiAutomatedEncoding.printIgnored();

	}
}