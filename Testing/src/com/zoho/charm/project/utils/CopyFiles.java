package com.zoho.charm.project.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import com.zoho.charm.project.utils.encoder.AdditionalUtils;
import com.zoho.charm.project.utils.encoder.EncodingConstants;

public class CopyFiles {

	public static String sourceFolder = "/home/local/ZOHOCORP/aravind-5939/My_Branch/charmehr/";
	public static String destinationFolder = "/home/local/ZOHOCORP/aravind-5939/My_Branch/charmehr_old_UI/";
	
	public static void main(String[] args) throws IOException {


		ArrayList<String> fileNamesList = AdditionalUtils.loadFileNames(EncodingConstants.FILE_NAMES_LOCATION);
		
		for(String fileName : fileNamesList) {
			copyFile(fileName);
		}


	}
	public static void copyFile(String fileName) throws IOException{
		System.out.println("Copying File : " + fileName);
		Files.copy(new File(sourceFolder.concat(fileName)).toPath(),
				new File(destinationFolder.concat(fileName)).toPath(), StandardCopyOption.REPLACE_EXISTING);
	}
	

}
