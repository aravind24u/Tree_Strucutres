package com.zoho.charm.project.utils.encoder;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import com.zoho.logs.apache.commons.lang3.StringUtils;

public class RunThisClass {

	public static final Logger LOGGER = Logger.getLogger(RunThisClass.class.getName());

	public static void main(String[] a) throws Exception {

		String[] fileNames = null;

		List<String> fileNamesList = AdditionalUtils.loadFileNames(EncodingConstants.FILE_NAMES_LOCATION);

		if (fileNamesList.size() > 0) {
			fileNames = new String[fileNamesList.size()];
			fileNames = fileNamesList.toArray(fileNames);
			System.out.println(
					"\n\nLoading the files names from the file : " + EncodingConstants.FILE_NAMES_LOCATION + "\n\n");
		} else if (EncodingConstants.FOLDER_LOCATION != null
				&& StringUtils.isNotEmpty(EncodingConstants.FOLDER_LOCATION)) {
			AdditionalUtils.populateJSPFileNames(
					EncodingConstants.WORKSPACE_LOCATION.concat(EncodingConstants.FOLDER_LOCATION), fileNamesList);
			fileNames = new String[fileNamesList.size()];
			fileNames = fileNamesList.toArray(fileNames);
			System.out.println("\n\nLoading the files from the folder : " + EncodingConstants.FOLDER_LOCATION + "\n\n");
		}

		if (fileNames == null || (fileNames != null && fileNames.length == 0)) {
			fileNames = EncodingConstants.FILE_NAMES;
			System.out.println("\n\nLoading the files names from file names constant in EncodingConstants.java\n\n");
		}

		File errorsBeforeEncoding = new File(EncodingConstants.OUTPUT_FILES_FOLDER + "before.html");
		StringBuilder errorsBefore = new StringBuilder();

		File errorsAfterEncoding = new File(EncodingConstants.OUTPUT_FILES_FOLDER + "after.html");
		StringBuilder errorsAfter = new StringBuilder();

		Date date = new Date();

		String fileMarker = new SimpleDateFormat(EncodingConstants.DATE_FORMAT).format(date);

		File changesMade = new File(
				EncodingConstants.OUTPUT_FILES_FOLDER + "/Changes/changes-".concat(fileMarker).concat(".html"));
		StringBuilder changes = new StringBuilder();

		Integer beforeStyleCounter = 1;
		Integer afterStyleCounter = 1;

		List<String> beforeEncoding = new ArrayList<>();
		List<String> afterEncoding = new ArrayList<>();

		// File encodesAfterEncoding = new File(EncodingConstants.OUTPUT_FILES_FOLDER +
		// "EncodesAfter.html");
		// StringBuilder cssEncoding = new StringBuilder();

		for (String fileName : fileNames) {

			try {

				System.out.print("\nTotal Number of Errors Before Encoding: ");
				beforeStyleCounter = CheckEncoding.checkEncode(EncodingConstants.WORKSPACE_LOCATION, fileName,
						errorsBefore, beforeStyleCounter, beforeEncoding);

				// Encode.encodeFile(EncodingConstants.WORKSPACE_LOCATION + fileName, changes);

				System.out.print("Total Number of Errors After encoding: ");
				afterStyleCounter = CheckEncoding.checkEncode(EncodingConstants.WORKSPACE_LOCATION, fileName,
						errorsAfter, afterStyleCounter, afterEncoding);

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

		AdditionalUtils.findDuplicates(fileNames);

		System.out.println("\nWriting Errors Before Encoding");
		AdditionalUtils.writeFile(errorsBeforeEncoding, errorsBefore);

		System.out.println("\nWriting Errors After Encoding");
		AdditionalUtils.writeFile(errorsAfterEncoding, errorsAfter);

		System.out.println("\nWriting the changes Made");
		AdditionalUtils.writeFile(changesMade, changes);

		// System.out.println("\nWriting the changes Made");
		// AdditionalUtils.writeFile(encodesAfterEncoding, cssEncoding);

		System.out.println("\n\nTotal Number of Errors Before encoding : " + beforeEncoding.size());
		System.out.println("Total Number of Errors After encoding : " + afterEncoding.size());

		SemiAutomatedEncoding.printIgnored();

	}
}