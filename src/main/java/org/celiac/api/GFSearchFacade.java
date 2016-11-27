package org.celiac.api;

import java.util.Iterator;
import java.util.Set;

import org.celiac.util.Logger;
import org.celiac.util.ResponseMaker;
import org.celiac.util.TemplateReader;
import org.celiac.util.database.DBQueryFactory;

public class GFSearchFacade {

	public String product = null;
	public String company = null;
	public String category = null;
	SearchFlowControl gfsearch = null;

	public String search(String _sms) {

		String theOutput = null;
		String sms = _sms;

		// Check if empty SMS
		if (sms.trim().equals(""))
			return ResponseMaker.noMatchFoundResponse(null, null, null);

		// Capital letters
		sms = sms.toUpperCase();

		// First try AS IS search
		gfsearch = new SearchFlowControl();

		// check if this is a company:
		boolean isACompany = false;
		try {
			isACompany = DBQueryFactory.getDBHandler().isACompany(sms.trim());
		} catch (Exception e) {
			e.printStackTrace();
			Logger.print("Error checking if sms is a sigle company word at GlutenFreeAPI");
		}

		if (isACompany) {
			theOutput = gfsearch.search(sms, true, false);
			if (theOutput != null)
				return theOutput;
			// Multiple:
			theOutput = gfsearch.search(sms, true, true);
			if (theOutput != null)
				return theOutput;
		}

		theOutput = gfsearch.search(sms, false, false);

		if (theOutput != null)
			return theOutput;

		// First get the original argument
		product = gfsearch.product;
		company = gfsearch.company;
		category = gfsearch.category;

		// Maybe this is product and company without dot between them
		if (theOutput == null) {
			if ((_sms.trim().indexOf(" ") > -1) && !(_sms.trim().indexOf(".") > -1)) {
				sms = _sms;
				// First check if there is a company name in the string
				Set<String> result = null;
				try {
					result = DBQueryFactory.getDBHandler().listOfCompany();
				} catch (Exception e) {
					e.printStackTrace();
					Logger.print("ERROR - Failed to get list of company in GlutenFreeAPI");
				}
				if (result != null) {
					Iterator<String> iter = result.iterator();
					String tmpCompany = null;
					while (iter.hasNext()) {
						tmpCompany = (String) iter.next();
						if (sms.indexOf(tmpCompany) > -1) {
							sms = sms.replaceAll(tmpCompany, "");
							theOutput = gfsearch.search(sms + "." + tmpCompany, false, false);
							break;
						}
					}
					if (theOutput == null) {
						// Maybe to look by Company only
						// theOutput = gfsearch.search(tmpCompany, true,false);
					}
				}
			}
		}

		// Before cutting the first word, lets try to see if this is a company
		// and category
		if (theOutput == null) {
			if (_sms.trim().indexOf(" ") > 0) {
				String firstWordAsACompany = _sms.trim().substring(0, _sms.trim().indexOf(" "));
				String secondWordAsACategory = _sms.trim().substring(_sms.trim().indexOf(" "), _sms.trim().length());
				try {
					if (DBQueryFactory.getDBHandler().isACompany(firstWordAsACompany.trim())) {
						theOutput = gfsearch.search("? " + secondWordAsACategory + "." + firstWordAsACompany , false, false);
					}
				} catch (Exception e) {
					e.printStackTrace();
					Logger.print("Error checking if sms is a sigle company word at GlutenFreeAPI");
				}
			}
		}

		// If return null, try to cut the first word and search again
		// If we got so far and still null, try to cut the word BETAHAM

		// last check for a company

		/*
		 * else { try{ theOutput =
		 * gfsearch.search(DBQueryFactory.getDBHandler().likeACompany(sms),
		 * true,false); }catch (Exception e){ e.printStackTrace();
		 * Logger.print("Error checking if sms is a part of single company word
		 * at GlutenFreeAPI"); } }
		 * 
		 * //Look for multiple on companies
		 * 
		 * if (theOutput == null) { if (isACompany) { theOutput =
		 * gfsearch.search(sms, false,false); } else { try{ theOutput =
		 * gfsearch.search(DBQueryFactory.getDBHandler().likeACompany(sms),
		 * false,false); }catch (Exception e){ e.printStackTrace();
		 * Logger.print("Error checking if sms is a part of single company word
		 * at GlutenFreeAPI"); } } }
		 */

		if (theOutput == null) {
			String BETAHAM = TemplateReader.getHebrewMapWord("BETAHAM") + " ";
			if (sms.indexOf(BETAHAM) > -1)
				theOutput = gfsearch.search(sms.replaceAll(BETAHAM, ""), false, false);
		}

		if (theOutput == null) {
			theOutput = searchCutLast2Characters(sms);
		}

		// Single word
		if ((theOutput == null) && !(sms.indexOf(".") > 0) && (sms.trim().indexOf(" ") > 0)) {
			String firstWordCutSMS = null;

			firstWordCutSMS = sms.substring(sms.indexOf(" ") + 1, sms.length());
			theOutput = gfsearch.search(firstWordCutSMS, false, false);

			// If the word length is more then 6 letters, substruct the last
			// 3 characters
			if (theOutput == null)
				theOutput = searchCutLast2Characters(firstWordCutSMS);

		}

		// more then 1 word
		if ((theOutput == null) && (sms.indexOf(".") > 0)) {
			String firstWordCutSMS = null;
			String firstPart = sms.substring(0, sms.indexOf("."));
			String secondPart = sms.substring(sms.indexOf("."), sms.length());
			if (firstPart.trim().indexOf(" ") > 0) {
				firstWordCutSMS = firstPart.substring(firstPart.indexOf(" ") + 1, firstPart.length());
				theOutput = gfsearch.search(firstWordCutSMS + secondPart, false, false);
			}

		}

		// Check for multiple
		if (theOutput == null) {
			theOutput = gfsearch.search(sms, false, true);
		}

		// Maybe the product is actually a company name
		if (theOutput == null) {
			theOutput = gfsearch.search(sms, true, false);
		}

		// Maybe the product is actually a company name and this is Multiple
		if (theOutput == null) {
			theOutput = gfsearch.search(sms, true, true);
		}

		return theOutput;

	}

	private String searchCutLast2Characters(String sms) {
		String output = null;
		String firstPart = sms.trim();
		String secondPart = "";

		if (sms.trim().indexOf(".") > 0) {

			firstPart = sms.trim().substring(0, sms.trim().indexOf("."));
			secondPart = sms.trim().substring(sms.trim().indexOf("."), sms.trim().length());
		}

		if (firstPart.trim().indexOf(" ") > 0) {

			// Logger.print("FFFF " + (firstPart.lastIndexOf(" ") - 1));
			// Check that the last word is with length > 5
			String innerFirstPart = firstPart.trim().substring(0, firstPart.trim().lastIndexOf(" "));
			String innerSecondPart = firstPart.substring(firstPart.trim().lastIndexOf(" "), firstPart.trim().length());
			if ((innerSecondPart.trim().length() > 5) && isFemaleOrMaleEnd(innerSecondPart)) {
				String cutWord = innerSecondPart.trim().substring(0, innerSecondPart.trim().length() - 2);
				// Logger.print(innerFirstPart.trim() + " " + cutWord.trim());

				output = gfsearch.search(innerFirstPart.trim() + " " + cutWord.trim() + secondPart, false, false);

			}
		} else if ((firstPart.length() > 5) && isFemaleOrMaleEnd(firstPart)) {
			String cutWord = firstPart.trim().substring(0, firstPart.length() - 2);
			output = gfsearch.search(cutWord + secondPart, false, false);
		}
		return output;

	}

	public boolean isFemaleOrMaleEnd(String input) {

		String endFemale = TemplateReader.getHebrewMapWord("END_FEMALE");
		String endMale = TemplateReader.getHebrewMapWord("END_MALE");

		if ((input.indexOf(endFemale) + 2 == input.length()) || (input.indexOf(endMale) + 2 == input.length())) {
			return true;
		}

		return false;
	}

}
