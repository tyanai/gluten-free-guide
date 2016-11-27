package org.celiac.job;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.celiac.util.Logger;
import org.celiac.util.PropertiesLoader;


public class JobTemplateReader {

	private String propName = null;
	Map<String, String> templateMap = null;

	JobTemplateReader(String tempName) {
		propName = tempName;
		loadTemplate(tempName);
		//loadProperties(tempName);
	}

	
	private static final String DEFAULT_PROPERTIES_LOCATION = "/properties";

	public static String getSystemPropertiesLocation() {
		String fileName = System.getProperty("system.properties.location");
		if (fileName == null) {
			fileName = DEFAULT_PROPERTIES_LOCATION;
		}
		return fileName;
	}

	

	public String getProperties(String key) {

		if (templateMap.get(key) == null) {
			Logger.print("Missing properties for entry '" + key + "' in "
					+ propName + ". Please fix before running again.");

			return null;
		} else {
			return templateMap.get(key).trim();
		}
	}
	
	private void loadTemplate(String template) {

		
		if (templateMap != null) return;
		
		templateMap = new HashMap<String, String>();
		
		String key = "";
		String value = "";

		try {

			InputStreamReader in = null;
			InputStream fin = JobTemplateReader.class.getResourceAsStream(PropertiesLoader.getSystemPropertiesLocation()
					+ "/" + template);
			BufferedInputStream bis = new BufferedInputStream(fin);

			in = new InputStreamReader(bis, "ISO8859_8");
			BufferedReader br = new BufferedReader(in);
			String theInput;

			while ((theInput = br.readLine()) != null) {
				key = theInput.substring(0,theInput.indexOf("="));
				value = theInput.substring(theInput.indexOf("=")+1,theInput.length());
				templateMap.put(key.trim(), value.trim());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	

	}

}
