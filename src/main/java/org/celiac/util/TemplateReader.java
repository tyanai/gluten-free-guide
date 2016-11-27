package org.celiac.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.celiac.util.database.DBQueryFactory;

public class TemplateReader {

	
	static Map<String, String> templateMap = new HashMap<String, String>();

	static Map<String, String> hebrewMap = null;
	static Map<String, String> hebrewMapFromDB = null;
	
	static List<String> keyWords = null;

	static public String getTemplate(String template) {

		String output = (String) templateMap.get(template);
		if (output != null)
			return output;
		String text = "";

		try {

			InputStreamReader in = null;
			InputStream fin = TemplateReader.class.getResourceAsStream(PropertiesLoader.getSystemPropertiesLocation()
					+ "/" + template);
			BufferedInputStream bis = new BufferedInputStream(fin);

			in = new InputStreamReader(bis, "ISO8859_8");
			BufferedReader br = new BufferedReader(in);
			String theInput;

			while ((theInput = br.readLine()) != null) {
				text = text + theInput;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		templateMap.put(template, text.trim());
		return text.trim();

	}
	
	static public List<String> getKeyWords(String template) {

		List<String> output = null;
		if (keyWords != null)
			return output;
		

		try {

			InputStreamReader in = null;
			InputStream fin = TemplateReader.class.getResourceAsStream(PropertiesLoader.getSystemPropertiesLocation()
					+ "/" + template);
			BufferedInputStream bis = new BufferedInputStream(fin);

			in = new InputStreamReader(bis, "ISO8859_8");
			BufferedReader br = new BufferedReader(in);
			String theInput;

			output = new ArrayList<String>();
			while ((theInput = br.readLine()) != null) {
				if (theInput.trim().equals("")) continue;
				output.add(theInput);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		
		return output;

	}

	private static Map<String, String> loadHebrewMap() {
		String text = "";
		Map<String, String> output = new HashMap<String, String>();
		
		try {
			InputStreamReader in = null;
			InputStream fin = TemplateReader.class.getResourceAsStream(PropertiesLoader.getSystemPropertiesLocation()
					+ "/hebrew.map");
			BufferedInputStream bis = new BufferedInputStream(fin);

			in = new InputStreamReader(bis, "ISO8859_8");
			BufferedReader br = new BufferedReader(in);
			String theInput;

			while ((theInput = br.readLine()) != null) {
				text = text + theInput;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Logger.print(text);
		StringTokenizer strToken = new StringTokenizer(text,"|");
		String propertie = null;
		String key = null;
		String value = null;
		Logger.print("Loading map of hebrew words:");
		while (strToken.hasMoreTokens()){
			propertie = strToken.nextToken();
			key = propertie.substring(0,propertie.indexOf("="));
			Logger.print("'" + key + "'");
			value = propertie.substring(propertie.indexOf("=")+1,propertie.length());
			output.put(key,value);
		}
		Logger.print("");
		
		return output;
	}
	
	private static Map<String, String> loadHebrewMapFromDB()  {
			
		Map<String, String> output = null;
		try{
			
			output = DBQueryFactory.getDBHandler().getCodeDescriptionList();
			
		}catch (Exception e){
			
			Logger.error("Failed to load data desc from database", e);
			e.printStackTrace();
			output = new HashMap<String, String>();
		}
		
		return output;
	}
	
	static public String getHebrewMapWord(String word) {
		if (hebrewMap == null) {
			hebrewMap = loadHebrewMap();
		}
		
		if (hebrewMapFromDB == null) {
			hebrewMapFromDB = loadHebrewMapFromDB();
		}
		
		String output = null;
		Object codeDesc = null;
		
		try{
		  codeDesc = hebrewMap.get(word);
	      if (codeDesc == null) codeDesc = hebrewMapFromDB.get(word);
		  output =  ((String)codeDesc).trim();
		}catch (Exception e){
			e.printStackTrace();
		}
		return output;
		
	}
	
	static public void resetHebrewMapFromDB(){
		hebrewMapFromDB = null;
	}

}
