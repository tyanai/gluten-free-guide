package org.celiac.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

	static Properties prop = null;
	private static final String DEFAULT_PROPERTIES_LOCATION = "/properties";
	
	public static String getSystemPropertiesLocation() {
		String fileName = System.getProperty("system.properties.location");
		if (fileName == null) {
			fileName = DEFAULT_PROPERTIES_LOCATION;
		}
		return fileName;
	}
	private static void loadProperties() {
		// Read properties file.
		
		
		try {		
			prop = new Properties();
			InputStream is = PropertiesLoader.class.getResourceAsStream(getSystemPropertiesLocation()+"/glutenfree.properties"); 
			prop.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static String getProperties(String key) {
	
		if (prop == null) loadProperties();
		if (prop.getProperty(key) == null) {
			Logger.print("Missing properties for entry '" + key +"' Exiting program. Please fix before running again.");
			System.exit(1);
			return null;
		} else {
			return prop.getProperty(key).trim();
		}
	}
}
