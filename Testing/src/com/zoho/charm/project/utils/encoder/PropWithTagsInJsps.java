package com.zoho.charm.project.utils.encoder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;

public class PropWithTagsInJsps {

	public static final Logger LOGGER = Logger.getLogger(PropWithTagsInJsps.class.getSimpleName());

	private static String[] directoryPath = { EncodingConstants.WORKSPACE_LOCATION + "/webapps/charm",
			EncodingConstants.WORKSPACE_LOCATION + "/source/com/" };

	public static void main(String[] args) throws Exception {

		Long start = System.currentTimeMillis();
		Properties properties = new Properties();

		properties.load(new FileReader(EncodingConstants.WORKSPACE_LOCATION
				+ "webapps/charm/WEB-INF/classes/resources/MessageResources.properties"));

		ArrayList<String> keys = new ArrayList<>();
		int i = 1;
		for (Object key : properties.keySet()) {
			if ((properties.getProperty(key.toString()).contains("<")
					&& properties.getProperty(key.toString()).contains(">"))
					|| (properties.getProperty(key.toString()).contains("</"))) {
				keys.add(key.toString());
				System.out.println(i++ + ". \t" + key.toString() + "\t" + properties.getProperty(key.toString()));
			}
		}
		System.out.println(System.lineSeparator());
		System.out.println(System.lineSeparator());
		System.out.println(System.lineSeparator());

		for (String directoryPath : directoryPath) {
			File directory = new File(directoryPath);

			if (directory == null || !directory.exists()) {
				System.out.println("Directory doesn’t exists!!!");
				return;
			}

			searchInFolder(directory, keys);
		}
		System.out.println("Total keys : " + keys.size());
		System.out.println("Total used in keys : " + uniqueProps.size());

		System.out.println("Time taken : " + (System.currentTimeMillis() - start));
	}

	public static void searchInFolder(File directory, ArrayList<String> keys) {

		File[] filesAndDirs = directory.listFiles();

		for (File file : filesAndDirs) {

			if (file.isFile()) {
				if (file.getName().endsWith(".jsp") || file.getName().endsWith(".jspf")) {
					searchInFile(file, keys);
				}
			} else {
				searchInFolder(file, keys);
			}
		}
	}

	static int i = 1;
	static Set<String> uniqueProps = new HashSet<String>();

	private static void searchInFile(File file, ArrayList<String> keys) {
		try {

			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			while (line != null) {
				for (String searchTerm : keys) {
					if (line.contains(searchTerm)) {
						System.out.println(i++ + ". " + file.getName() + " \tKey : " + line.trim());
						uniqueProps.add(searchTerm);
					}
				}
				line = reader.readLine();
			}
			reader.close();
		} catch (FileNotFoundException e) {
			System.err.println("Search File Not Found !!!!! ");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
