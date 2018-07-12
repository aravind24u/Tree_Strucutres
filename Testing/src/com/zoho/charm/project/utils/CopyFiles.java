package com.zoho.charm.project.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import com.zoho.charm.project.utils.encoder.EncodingConstants;

public class CopyFiles {

	public static String sourceFolder = "/home/local/ZOHOCORP/aravind-5939/My_Branch/charmehr/";
	public static String destinationFolder = "/home/local/ZOHOCORP/aravind-5939/My_Branch/charmehr_old_UI/";
	
	public static void main(String[] args) throws IOException {


		for (String fileName : EncodingConstants.FILE_NAMES) {
			copyFiles(fileName);
		}

	}
	public static void copyFiles(String fileName) throws IOException{
		System.out.println("Copying File : " + fileName);
		Files.copy(new File(sourceFolder.concat(fileName)).toPath(),
				new File(destinationFolder.concat(fileName)).toPath(), StandardCopyOption.REPLACE_EXISTING);
	}

}
