package com.zoho.charm.project.utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;

public class ProcessPatches {
	public static String sourceFolder = "/home/local/ZOHOCORP/aravind-5939/My_Branch/charmehr/";
	public static String destinationFolder = "/home/local/ZOHOCORP/aravind-5939/My_Branch/charmehr_old_UI/";
	
	public static void main(String[] args) {
		File file = new File("/home/local/ZOHOCORP/aravind-5939/Desktop/Pricing/MergePatches");
		String command = "cp /home/local/ZOHOCORP/aravind-5939/My_Branch/charmehr/{0} /home/local/ZOHOCORP/aravind-5939/My_Branch/charmehr_old_UI/{0}";
		List<File> files = new ArrayList<>();

		populateFileNames(file, files, ".patch");

		List<String> lines = getListFromFiles(files);
		Set<String> uniqieNames = new HashSet<>();

		lines.forEach(line -> {
			if (line != null && line.startsWith("diff -r ")) {
				uniqieNames.add(line.substring(37));
			}
		});

		uniqieNames.forEach(line -> {
			//System.out.println(MessageFormat.format(command, line));
			try {
				copyFile(line);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		
	}

	private static List<String> getListFromFiles(List<File> files) {
		List<String> lines = new ArrayList<>();

		files.forEach(file -> {
			try {
				lines.addAll(IOUtils.readLines(new FileReader(file)));
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		return lines;
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
					fileNames.add(file);
				}
			}
		}
	}
	
	public static void copyFile(String fileName) throws IOException{
		//fileName = fileName.replace("webapps/ehr/common/", "webapps/ehr/common/pricing/");
		System.out.println("Copying File : " + fileName);
		String destination = destinationFolder.concat(fileName);
		checkDestination(destination.substring(0, destination.lastIndexOf("/")));
		Files.copy(new File(sourceFolder.concat(fileName)).toPath(),
				new File(destination).toPath(), StandardCopyOption.REPLACE_EXISTING);
	}

	private static void checkDestination(String path) {
		File file = new File(path);
		
		if(!file.exists()) {
			file.mkdirs();
		}
	}
}
