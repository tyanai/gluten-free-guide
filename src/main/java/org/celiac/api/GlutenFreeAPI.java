package org.celiac.api;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.celiac.datatype.GFUser;
import org.celiac.util.ActivityLogger;
import org.celiac.util.MessageCounter;
import org.celiac.util.ResponseMaker;
import org.celiac.util.TemplateReader;
import org.celiac.util.database.DBQueryFactory;
import org.celiac.util.database.GFQuery;

public class GlutenFreeAPI {

	public String product = null;
	public String company = null;
	public String category = null;
	GFSearchFacade gfsearch = null;

	public String search(String _sms, String cell, boolean validateCellNum) {

		String sms = _sms;
		boolean isAWebUser = false;
		GFUser gfUser = null;
		GFQuery gfQuery = DBQueryFactory.getDBHandler();
		if (("FROM-CELL").equals(cell)){
			//To Do Nothing
		} else if (validateCellNum) {
			String userId = "NO_WEB";
			//String cellNum = cell;
			
			
			try {
				
				/*
				if (!isNumeric(cell)) {
					userId = cell;
					cellNum = "WEB_USER";
					isAWebUser = true;
					gfUser = gfQuery.getUserByUserID(userId.trim());
				} else {
					gfUser = gfQuery.getUser(cell.trim());
				}*/
				
				//***********************************************************************************
				// The section below need to be deleted (and the above to be open when activating SMS
				userId = cell;
				//cellNum = "WEB_USER";
				isAWebUser = true;
				gfUser = gfQuery.getUserByUserID(userId.trim());
				//************************************************************************************

				// User does not exist
				// Create user and return for acknowledge
				if (gfUser == null) {
					// Create User
					
					
					//gfQuery.insertUser(cellNum, userId);
					String acknowledge= ResponseMaker.needUserAcknowledgment();
					MessageCounter.setReturned((acknowledge.length() % 70) + 1);
					return acknowledge;
				} else if (gfUser.getACKNOWLEDGE_USE().equals("FALSE")){
					userId = gfUser.getUSER_ID();
					//cellNum = gfUser.getCELL_NUM();
					if ((sms != null) && (sms.trim().equals(TemplateReader.getHebrewMapWord("ISHUR")))){
						//gfQuery.updateUser(cellNum, gfUser.getFIRST_NAME(), gfUser.getLAST_NAME(), userId, gfUser.getPASSWORD(), gfUser.getPERMISSIONS(), new Date(), "TRUE");
						
						String acknowledge =  ResponseMaker.userAcknowledged();
						MessageCounter.setReturned((acknowledge.length() % 70) + 1);
						return acknowledge;
					} else {
						String acknowledge =   ResponseMaker.needUserAcknowledgment();
						MessageCounter.setReturned((acknowledge.length() % 70) + 1);
						return acknowledge;
					}
				}
				
				
				//Update user usage date
				//gfQuery.updateUser(gfUser.getCELL_NUM(), gfUser.getFIRST_NAME(), gfUser.getLAST_NAME(), gfUser.getUSER_ID(), gfUser.getPASSWORD(), gfUser.getPERMISSIONS(), new Date(), gfUser.getACKNOWLEDGE_USE());

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		
		
		
		// Validate sms
		sms = validateSMS(sms);

		String theOutput = null;
		gfsearch = new GFSearchFacade();

		
		//First check for reserved words

		List<String> keyWords = TemplateReader.getKeyWords("key_words.properties");
		Iterator<String> words = keyWords.iterator();
		while (words.hasNext()){
			if (((String)(words.next())).equals(sms.trim())){
				theOutput = gfsearch.search("? " + sms);
			}
		}
		
		
		if (theOutput == null) {
			theOutput = gfsearch.search(sms);
		}

		// First get the original argument
		product = gfsearch.product;
		company = gfsearch.company;
		category = gfsearch.category;

		// Try to switch the order of product and company
		if (theOutput == null) {
			if (sms.indexOf(".") > -1)
				sms = switchOrderProductCompany(sms);
			theOutput = gfsearch.search(sms);
		}

		// maybe this is a category
		if (theOutput == null) {
			if (!(_sms.indexOf(".") > -1))
				theOutput = gfsearch.search("# " + _sms);
		}

		// without dot between them.

		// Maybe this a category the was submitted as YACHID and not RABIM as
		// female
		if (theOutput == null) {
			if (_sms.endsWith(TemplateReader.getHebrewMapWord("HE"))) {
				theOutput = gfsearch.search("# " + _sms.substring(0, _sms.length() - 1)
						+ TemplateReader.getHebrewMapWord("END_FEMALE"));
			}
		}

		if (theOutput == null) {
			theOutput = ResponseMaker.noMatchFoundResponse(product, company, category);
		}

		String theUser = null;
		if (validateCellNum && (gfUser != null)) {
			theUser = gfUser.getUSER_ID() + "(" + gfUser.getFIRST_NAME() + " " + gfUser.getLAST_NAME() + ")";
		} else {
			theUser = "test-user";
		}

		ActivityLogger.print(new Date().toString() + " - USER: '" + theUser + "' REQUEST : '" + _sms + "' ");
		ActivityLogger.print(theOutput);

		if (isAWebUser) MessageCounter.setWebUserCount();
		else MessageCounter.setReturned((theOutput.length() / 70) + 1);
		
		return theOutput;
	}

	private String switchOrderProductCompany(String sms) {
		StringTokenizer strToken = new StringTokenizer(sms, ".");

		if (strToken.countTokens() == 2) {

			product = strToken.nextToken().trim();
			company = strToken.nextToken().trim();
			return company + "." + product;
		} else if (strToken.countTokens() == 3) {

			product = strToken.nextToken().trim();
			company = strToken.nextToken().trim();
			category = strToken.nextToken().trim();
			return company + "." + product + "." + category;
		}

		return "";

	}

	private String validateSMS(String _sms) {

		String sms = _sms;

		// remove unesassery spaces
		while (sms.indexOf("  ") > -1) {
			sms = sms.replaceFirst("  ", " ");
		}

		// remove unesassery dots
		String fp = null;
		String sp = null;
		while (sms.indexOf("..") > -1) {
			fp = sms.substring(0, sms.indexOf(".."));
			sp = sms.substring(sms.indexOf("..") + 1, sms.length());
			sms = fp + sp;
		}

		if (sms.trim().lastIndexOf(".") == sms.length() - 1)
			sms = sms.substring(0, sms.length() - 1);
		if (sms.trim().indexOf(".") == 0)
			sms = sms.substring(1, sms.length());

		return sms;
	}

	public boolean isNumeric(String str) {

		int ascii = 0;
		for (int i = 0; i < str.length(); i++) {
			ascii = (int) str.charAt(i);
			if (!(ascii >= 48 && ascii <= 57))
				return false;
		}

		return true;
	}

}
