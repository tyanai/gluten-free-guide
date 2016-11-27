package org.celiac.api;

import java.util.StringTokenizer;

import org.celiac.business.GFS;
import org.celiac.business.GFSByCategoryAndCompanyImpl;
import org.celiac.business.GFSByCategoryImpl;
import org.celiac.business.GFSByCompanyImpl;
import org.celiac.business.GFSByNameAndCompanyAndCategoryImpl;
import org.celiac.business.GFSByNameAndCompanyImpl;
import org.celiac.business.GFSByNameImpl;
import org.celiac.util.Logger;
import org.celiac.util.PropertiesLoader;
import org.celiac.util.TemplateReader;

public class SearchFlowControl {
	public String product = null;
	public String company = null;
	public String category = null;
	
	public String search(String smsInput, boolean treatAsCompany, boolean lookForMultiple) {

		String theOutput = null;
		String sms = smsInput;
		GFS gfs = null;
		
		
		try {

			StringTokenizer strToken = new StringTokenizer(sms, ".");
			boolean lookedByCategory = false;
			
			//Remove dashes
			sms = sms.replaceAll(TemplateReader.getHebrewMapWord("DASHE"), "");
			
			
			if (sms.startsWith("?")) {
				sms = sms.substring(sms.indexOf("?") + 1, sms.length());
				lookedByCategory = true;
				if (strToken.countTokens() == 1){
					gfs = new GFSByCategoryImpl();
					category = sms.trim();
				}
				else if (strToken.countTokens() == 2){
					gfs = new GFSByCategoryAndCompanyImpl();
					StringTokenizer strToken2 = new StringTokenizer(sms,".");
					category = strToken2.nextToken().trim();
					company = strToken2.nextToken().trim();
				} else if (strToken.countTokens() == 3){
					
					gfs = new GFSByNameAndCompanyAndCategoryImpl();
					StringTokenizer strToken2 = new StringTokenizer(sms,".");
					category = strToken2.nextToken().trim();
					company = strToken2.nextToken().trim();
					product = strToken2.nextToken().trim();
					sms = product + "," + company + "," + category;
				}
			} else if (treatAsCompany){
				if (strToken.countTokens() == 1){
					gfs = new GFSByCompanyImpl();
					product = strToken.nextToken().trim();
					
				}
				else if (strToken.countTokens() == 2){
					gfs = new GFSByCompanyImpl();
					product = strToken.nextToken().trim();
					sms = strToken.nextToken().trim();
				}
				else if (strToken.countTokens() == 3){
					gfs = new GFSByCategoryAndCompanyImpl();
					product = strToken.nextToken().trim();
					company = strToken.nextToken().trim();
					category = strToken.nextToken().trim();
					sms = category + "." + company;
				}
			} 
			
			else {

				// First try by product
				if (strToken.countTokens() == 1){
					gfs = new GFSByNameImpl();
					product = strToken.nextToken().trim();
					
				}
				else if (strToken.countTokens() == 2){
					gfs = new GFSByNameAndCompanyImpl();
					product = strToken.nextToken().trim();
					company = strToken.nextToken().trim();
				}
				else if (strToken.countTokens() == 3){
					gfs = new GFSByNameAndCompanyAndCategoryImpl();
					product = strToken.nextToken().trim();
					company = strToken.nextToken().trim();
					category = strToken.nextToken().trim();
				}
			}

			if (gfs != null) {
				if (lookForMultiple) theOutput = gfs.searchMULTIPLE(sms.trim());
				else theOutput = gfs.search(sms.trim());
	
				if ((theOutput == null) && !lookedByCategory) {
	
					// second try by Category
					if (strToken.countTokens() == 1)
						gfs = new GFSByCategoryImpl();
					else if (strToken.countTokens() == 2)
						gfs = new GFSByCategoryAndCompanyImpl();
					
					if (lookForMultiple) theOutput = gfs.searchMULTIPLE(sms.trim());
					else theOutput = gfs.search(sms.trim());
				}
			}
			else {
				Logger.print("could not parse SMS");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		
		
		int numOfCharacters = Integer.parseInt(PropertiesLoader.getProperties("number.of.sms.characters").trim());
		
		if ((theOutput != null) && theOutput.length() >  numOfCharacters) return theOutput.substring(0,numOfCharacters);
		else return theOutput;

	}
}
