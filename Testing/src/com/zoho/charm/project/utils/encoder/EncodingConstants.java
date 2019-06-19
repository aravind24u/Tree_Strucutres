package com.zoho.charm.project.utils.encoder;

public interface EncodingConstants {

	/**
	 * ############################################Begin_User_Constants############################################
	 */

	// The file name would contain the remaining path
	public static final String WORKSPACE_LOCATION = "/home/local/ZOHOCORP/aravind-5939/My_Branch/charmehr/";

	// Create this directory , or an exception would be thrown
	public static final String OUTPUT_FILES_FOLDER = "/home/local/ZOHOCORP/aravind-5939/Desktop/Encoding/";

	/**
	 * The below constants are to specify the ways of loading the file names that
	 * are to be encoded
	 */
	// If you want to specify the folder check here
	public static final String FOLDER_LOCATION = "webapps/ehr/";

	// If you wish to give the file names in a file rather than the array specify it
	// here
	public static final String FILE_NAMES_LOCATION = null;// OUTPUT_FILES_FOLDER.concat("fileNames.txt");

	// If you want to give the list of files instead of the folder , start the
	// address from webapps/
	public static String FILE_NAMES[] = { "webapps/charm/physician/reviews/ReviewLabsList.jsp" };

	/**
	 * ############################################End_User_Constants############################################
	 */

	/**
	 * ############################################Program_Constants#############################################
	 * Change the following constants with caution
	 */

	// Set this to false if you don't want to resolve the issues that are'nt
	// resolved automatically during runtime.
	public static final Boolean ENABLE_AUTOMATED_ENCODING = Boolean.FALSE;

	// Set this to false if you don't want to resolve the issues that are'nt
	// resolved automatically during runtime.
	public static final Boolean ENABLE_SEMI_AUTOMATED_ENCODING = Boolean.TRUE;

	// Set this to true to print the options every time a input is asked
	public static final Boolean SHOULD_PRINT_ENCODING_OPTIONS = Boolean.TRUE;

	// Set this to true if you want an confirmation before writing a manually
	// encoded line
	public static final Boolean IS_CHECK_REQUIRED = Boolean.TRUE;

	// Should we print the list of ignored lines , if so set true
	public static final Boolean PRINT_IGNORED = Boolean.TRUE;

	public static final String TAG_LIB_INCLUDE = "<%@ taglib prefix = \"enc\" uri = \"/WEB-INF/CharmEncoder.tld\"%>";
	public static final String IAM_JAR_INCLUDE = "<%@page import=\"com.adventnet.iam.xss.IAMEncoder\"%>";
	public static final String DELIMITER = "~";
	public static final String IGNORED_FILES = "Ignored_Files.txt";
	public static final String DATE_FORMAT = "dd-MM-yy-HH-mm-ss";
}