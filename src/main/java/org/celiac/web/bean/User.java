package org.celiac.web.bean;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.celiac.datatype.CodeDesc;
import org.celiac.datatype.CompanyTable;
import org.celiac.datatype.GFUser;
import org.celiac.datatype.StatisticsData;
import org.celiac.util.DBWriter;
import org.celiac.util.GuideDiff;
import org.celiac.util.Logger;
import org.celiac.util.Pop3Email;
import org.celiac.util.PropertiesLoader;
import org.celiac.util.ResponseMaker;
import org.celiac.util.TemplateReader;
import org.celiac.util.XLSReader;
import org.celiac.util.database.DBQueryFactory;

public class User implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8935987878150015138L;

	public GFUser theUser = null;

	public boolean validateInputFile(FileItem item) throws Exception {

		String userIdentity = null;
		try {
			System.out.println("File size is: " + item.getSize());

			XLSReader xLSReader = new XLSReader();
			
			userIdentity = theUser.getFIRST_NAME() + " " + theUser.getLAST_NAME() + " (" + theUser.getUSER_ID() + ")";

			Map<String, CompanyTable> intoDB = xLSReader.readWorkBook(item.getInputStream());

			// Iterator<String> it = intoDB.keySet().iterator();
			// String tableName = null;
			// CompanyTable companyTable = null;
			DBWriter dBWriter = new DBWriter();

			

			try {
				dBWriter.dropCompanyTable("TEMP_PRODUCT_INDEX");
			} catch (Exception e) {
				e.printStackTrace();
			}
			/*
			 * while (it.hasNext()){ tableName = it.next(); companyTable =
			 * intoDB.get(tableName);
			 * dBWriter.createCompanyTable(tableName,companyTable); }
			 */
			dBWriter.createProductIndexTable("TEMP_PRODUCT_INDEX");
			dBWriter.insertProductIndexTable(intoDB, "TEMP_PRODUCT_INDEX");

			//Now that we have the new (temp index table) and the old table in place we want to do  a diff between the two.
			
			GuideDiff guideDiff = new GuideDiff();
			guideDiff.createDiffReport();
			guideDiff.updateDiffReport();
			
			dBWriter = new DBWriter();
			try {
				dBWriter.dropCompanyTable("PRODUCT_INDEX");
			} catch (Exception e) {
				e.printStackTrace();
			}
			/*
			 * while (it.hasNext()){ tableName = it.next(); companyTable =
			 * intoDB.get(tableName);
			 * dBWriter.createCompanyTable(tableName,companyTable); }
			 */
			dBWriter.createProductIndexTable("PRODUCT_INDEX");
			dBWriter.insertProductIndexTable(intoDB, "PRODUCT_INDEX");
			
			org.celiac.util.AutoCompleteSearch.reload();
			
			try {
				Pop3Email.postMen(PropertiesLoader.getProperties("data.upload.email.notification"),
						"GFGuide - Database (merchandise) Update Alert",
						"Hi,<br><br>This is to let you know that GFGuide merchandise database was just been updated successfully by user: "
								+ userIdentity + ".<br><br>For your information.<br><br>Thanks,<br><br>GFGuide System.");
				Pop3Email.postMen(PropertiesLoader.getProperties("data.upload.email.notification.admin"),
						"GFGuide - Database (merchandise) Update Alert",
						"Hi,<br><br>This is to let you know that GFGuide merchandise database was just been updated successfully by user: "
								+ userIdentity + ".<br><br>For your information.<br><br>Thanks,<br><br>GFGuide System.");
			} catch (Exception e) {
				Logger.error("Failed to send email regard data update", e);
			}

		} catch (Exception e) {
			try {
				Pop3Email.postMen(PropertiesLoader.getProperties("data.upload.email.notification"),
						"GFGuide - Database (merchandise) Access Alert",
						"Hi,<br><br>This is to let you know that an attempt was made to update the GFGuide merchandise database by user: "
								+ userIdentity + ".<br><br>For your information.<br><br>Thanks,<br><br>GFGuide System.");
				Pop3Email.postMen(PropertiesLoader.getProperties("data.upload.email.notification.admin"),
						"GFGuide - Database (merchandise) Access Alert",
						"Hi,<br><br>This is to let you know that an attempt was made to update the GFGuide merchandise database by user: "
								+ userIdentity + ".<br><br>For your information.<br><br>Thanks,<br><br>GFGuide System.");
			} catch (Exception ee) {
				Logger.error("Failed to send email regard data update", ee);
			}
			throw e;
		}

		return true;
	}

	public void sendWelcomeLetter(GFUser welcomeUser) {

		String message = ResponseMaker.getWelcomeLetter(welcomeUser.getUSER_ID(), welcomeUser.getPASSWORD(), welcomeUser
				.getFIRST_NAME(), welcomeUser.getLAST_NAME());
		StringBuffer sb = new StringBuffer();
		sb.append("<html><p align=right>");
		message = message.replaceAll("\n", "<br>");
		sb.append(message);
		try {
			Pop3Email.postMen(welcomeUser.getEMAIL().trim(), "Gluten Free GFGuide - WELCOME", sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
			Logger.error("Failed to send email to user " + welcomeUser.getUSER_ID(), e);
		}

	}

	public boolean validateInputUserFile(FileItem item) throws Exception {

		String userIdentity = null;
		try {
			System.out.println("File size is: " + item.getSize());

			XLSReader xLSReader = new XLSReader();

			/*
			 * //Insert the users try{ dBWriter.dropCompanyTable("USERS");
			 * }catch (Exception e){ //do nothing } dBWriter.createUsersTable();
			 * GFUser[] usersTable = xLSReader.readUsersWorkBook();
			 * dBWriter.insertUsersTable(usersTable);
			 */

			userIdentity = theUser.getFIRST_NAME() + " " + theUser.getLAST_NAME() + " (" + theUser.getUSER_ID() + ")";
			
			GFUser[] intoDB = xLSReader.readUsersWorkBook(item.getInputStream());

			DBWriter dBWriter = new DBWriter();

			

			try {
				dBWriter.dropCompanyTable("TEMP_USERS");
			} catch (Exception e) {
				e.printStackTrace();
			}
			/*
			 * while (it.hasNext()){ tableName = it.next(); companyTable =
			 * intoDB.get(tableName);
			 * dBWriter.createCompanyTable(tableName,companyTable); }
			 */
			dBWriter.createUsersTable("TEMP_USERS");
			dBWriter.insertUsersTable(intoDB, "TEMP_USERS");

			dBWriter = new DBWriter();
			try {
				dBWriter.dropCompanyTable("USERS");
			} catch (Exception e) {
				e.printStackTrace();
			}
			/*
			 * while (it.hasNext()){ tableName = it.next(); companyTable =
			 * intoDB.get(tableName);
			 * dBWriter.createCompanyTable(tableName,companyTable); }
			 */

			dBWriter.createUsersTable("USERS");
			dBWriter.insertUsersTable(intoDB, "USERS");

			try {
				Pop3Email.postMen(PropertiesLoader.getProperties("data.upload.email.notification"),
						"GFGuide - Database (users) Update Alert",
						"Hi,<br><br>This is to let you know that GFGuide users database was just been updated successfully by user: "
								+ userIdentity + ".<br><br>For your information.<br><br>Thanks,<br><br>GFGuide System.");
				Pop3Email.postMen(PropertiesLoader.getProperties("data.upload.email.notification.admin"),
						"GFGuide - Database (users) Update Alert",
						"Hi,<br><br>This is to let you know that GFGuide users database was just been updated successfully by user: "
								+ userIdentity + ".<br><br>For your information.<br><br>Thanks,<br><br>GFGuide System.");
			} catch (Exception e) {
				Logger.error("Failed to send email regard data update", e);
			}

		} catch (Exception e) {
			try {
				Pop3Email.postMen(PropertiesLoader.getProperties("data.upload.email.notification"),
						"GFGuide - Database (users) Access Alert",
						"Hi,<br><br>This is to let you know that an attempt was made to update the GFGuide users database by user: "
								+ userIdentity + ".<br><br>For your information.<br><br>Thanks,<br><br>GFGuide System.");
				Pop3Email.postMen(PropertiesLoader.getProperties("data.upload.email.notification.admin"),
						"GFGuide - Database (users) Access Alert",
						"Hi,<br><br>This is to let you know that an attempt was made to update the GFGuide users database by user: "
								+ userIdentity + ".<br><br>For your information.<br><br>Thanks,<br><br>GFGuide System.");
			} catch (Exception ee) {
				Logger.error("Failed to send email regard data update", ee);
			}
			throw e;
		}

		return true;
	}

	public boolean validateInputCodeDescFile(FileItem item) throws Exception {

		String userIdentity = null;

		try {

			System.out.println("File size is: " + item.getSize());

			XLSReader xLSReader = new XLSReader();
			
			userIdentity = theUser.getFIRST_NAME() + " " + theUser.getLAST_NAME() + " (" + theUser.getUSER_ID() + ")";

			CodeDesc[] intoDB = xLSReader.readCodeDescWorkBook(item.getInputStream());

			DBWriter dBWriter = new DBWriter();

			

			try {
				dBWriter.dropCompanyTable("TEMP_CODE_DESC");
			} catch (Exception e) {
				e.printStackTrace();
			}
			/*
			 * while (it.hasNext()){ tableName = it.next(); companyTable =
			 * intoDB.get(tableName);
			 * dBWriter.createCompanyTable(tableName,companyTable); }
			 */
			dBWriter.createCodeDescTable("TEMP_CODE_DESC");
			dBWriter.insertCodeDescTable(intoDB, "TEMP_CODE_DESC");

			dBWriter = new DBWriter();
			try {
				dBWriter.dropCompanyTable("CODE_DESC");
			} catch (Exception e) {
				e.printStackTrace();
			}
			/*
			 * while (it.hasNext()){ tableName = it.next(); companyTable =
			 * intoDB.get(tableName);
			 * dBWriter.createCompanyTable(tableName,companyTable); }
			 */

			dBWriter.createCodeDescTable("CODE_DESC");
			dBWriter.insertCodeDescTable(intoDB, "CODE_DESC");
			TemplateReader.resetHebrewMapFromDB();

			try {
				Pop3Email.postMen(PropertiesLoader.getProperties("data.upload.email.notification"),
						"GFGuide - Database (code description) Update Alert",
						"Hi,<br><br>This is to let you know that GFGuide Code Description database was just been updated successfully by user: "
								+ userIdentity + ".<br><br>For your information.<br><br>Thanks,<br><br>GFGuide System.");
				Pop3Email.postMen(PropertiesLoader.getProperties("data.upload.email.notification.admin"),
						"GFGuide - Database (code description) Update Alert",
						"Hi,<br><br>This is to let you know that GFGuide Code Description database was just been updated successfully by user: "
								+ userIdentity + ".<br><br>For your information.<br><br>Thanks,<br><br>GFGuide System.");
			} catch (Exception e) {
				Logger.error("Failed to send email regard data update", e);
			}

		} catch (Exception e) {
			try {
				Pop3Email.postMen(PropertiesLoader.getProperties("data.upload.email.notification"),
						"GFGuide - Database (code description) Access Alert",
						"Hi,<br><br>This is to let you know that an attempt was made to update the GFGuide Code Description database by user: "
								+ userIdentity + ".<br><br>For your information.<br><br>Thanks,<br><br>GFGuide System.");
				Pop3Email.postMen(PropertiesLoader.getProperties("data.upload.email.notification.admin"),
						"GFGuide - Database (code description) Access Alert",
						"Hi,<br><br>This is to let you know that an attempt was made to update the GFGuide Code Description database by user: "
								+ userIdentity + ".<br><br>For your information.<br><br>Thanks,<br><br>GFGuide System.");
			} catch (Exception ee) {
				Logger.error("Failed to send email regard data update", ee);
			}
			throw e;
		}

		return true;
	}

	public void log(String message, Throwable exc) {
		Logger.error(message, exc);
	}

	public boolean loadUser(String user, String password) {
		GFUser gfUser = null;

		try {
			gfUser = DBQueryFactory.getDBHandler().getUserByUserID(user.trim());

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (gfUser == null)
			return false;

		if (gfUser.getPASSWORD().equals(password)) {
			this.theUser = gfUser;
			return true;
		}
		return false;

	}
	
	public boolean maybeUserIsNoExpire(){
		return org.celiac.util.UserValidationFromWeb.isUserValid(this.theUser.getCELIAC_MEMBER_ID(), this.theUser.getUSER_TZ());
	}

	public GFUser getUser() {
		return theUser;
	}

	public void setTempUser() {
		this.theUser = new GFUser();
		this.theUser.setUSER_ID("tmpUser");
	}

	public StatisticsData[] getStatistics(String from, String to) throws Exception {

		if ((from == null) || from.trim().equals(""))
			return null;
		if ((to == null) || to.trim().equals(""))
			return null;

		int startDay = new Integer(from.trim().substring(0, from.trim().indexOf("/"))).intValue();
		int startMonth = new Integer(from.trim().substring(from.trim().indexOf("/") + 1, from.trim().lastIndexOf("/"))).intValue() - 1;
		int startYear = new Integer(from.trim().substring(from.trim().lastIndexOf("/") + 1, from.trim().length())).intValue();

		int endDay = new Integer(to.trim().substring(0, to.trim().indexOf("/"))).intValue();
		int endMonth = new Integer(to.trim().substring(to.trim().indexOf("/") + 1, to.trim().lastIndexOf("/"))).intValue() - 1;
		int endYear = new Integer(to.trim().substring(to.trim().lastIndexOf("/") + 1, to.trim().length())).intValue();

		GregorianCalendar startCal = new GregorianCalendar(startYear, startMonth, startDay);
		GregorianCalendar endCal = new GregorianCalendar(endYear, endMonth, endDay);

		StatisticsData[] output = DBQueryFactory.getDBHandler().getStatisticsData(startCal.getTime(), endCal.getTime());
		return output;

	}

	public Date getDateFromString(String from) throws Exception {

		if ((from == null) || from.trim().equals(""))
			return null;

		int startDay = new Integer(from.trim().substring(0, from.trim().indexOf("/"))).intValue();
		int startMonth = new Integer(from.trim().substring(from.trim().indexOf("/") + 1, from.trim().lastIndexOf("/"))).intValue() - 1;
		int startYear = new Integer(from.trim().substring(from.trim().lastIndexOf("/") + 1, from.trim().length())).intValue();
		GregorianCalendar startCal = new GregorianCalendar(startYear, startMonth, startDay);
		return startCal.getTime();

	}
	
	public void validateUser(String celiacUserId, String tz) throws Exception {

		GFUser gfUser = null;
		try {
			gfUser = DBQueryFactory.getDBHandler().getUserByGeneric(celiacUserId, "CELIAC_MEMBER_ID");
		} catch (Exception e) {
			Logger.error("Failed to find users " + celiacUserId, e);
			throw e;
		}
		
		if (gfUser == null){
			try{
			//Check maybe the user exist in the web but was not yet updated in gfguide
			if (org.celiac.util.UserValidationFromWeb.isUserValid(celiacUserId, tz)){
				//User exist - create a temporary user in the DB with an expiration of 6 months
				GFUser tempUser = new GFUser();
				tempUser.setCELIAC_MEMBER_ID(celiacUserId);
				tempUser.setUSER_TZ(tz);
				tempUser.setDID_BUY_THE_BOOK("false");
				java.util.Calendar cal = java.util.Calendar.getInstance();
				cal.add(java.util.Calendar.MONTH, +6);	//Adding 6 month to current date
				long theTime = cal.getTimeInMillis();
				Date tempDate = new Date();
				tempDate.setTime(theTime);
				tempUser.setEXPIRATION_DATE(tempDate);
				this.insertUser(tempUser);
				
			}
			}catch (Exception e){
				Logger.error("Failed to insert user " + celiacUserId + " based on celiac external web", e);
			}
			
		}
		
		
	}

	public GFUser getUser(String celiacUserId) throws Exception {

		GFUser gfUser = null;
		try {
			gfUser = DBQueryFactory.getDBHandler().getUserByGeneric(celiacUserId, "CELIAC_MEMBER_ID");
		} catch (Exception e) {
			Logger.error("Failed to find users " + celiacUserId, e);
			throw e;
		}
		
		if ((gfUser != null) && (this.theUser == null)) this.theUser = gfUser;
		
		
		
		return gfUser;

	}

	public void insertUser(GFUser gfUser) throws Exception {

		try {
			DBQueryFactory.getDBHandler().insertUser(gfUser);
		} catch (Exception e) {
			Logger.error("Failed to insert users", e);
			throw e;
		}

	}

	public void insertFullUser(GFUser gfUser) throws Exception {

		try {
			DBQueryFactory.getDBHandler().insertFullUser(gfUser);
		} catch (Exception e) {
			Logger.error("Failed to insert users", e);
			throw e;
		}

	}

	public void updateUser(GFUser gfUser) throws Exception {

		try {
			DBQueryFactory.getDBHandler().updateUser(gfUser);
		} catch (Exception e) {
			Logger.error("Failed to update users", e);
			throw e;
		}

	}

	public void deleteUser(GFUser gfUser) throws Exception {

		try {
			DBQueryFactory.getDBHandler().deleteUser(gfUser);
		} catch (Exception e) {
			Logger.error("Failed to update users", e);
			throw e;
		}

	}

	public void updateBasicUser(GFUser gfUser) throws Exception {

		try {
			DBQueryFactory.getDBHandler().updateBasicUser(gfUser);
		} catch (Exception e) {
			Logger.error("Failed to update users", e);
			throw e;
		}

	}

	public boolean validateString(String input) {

		int charToInt = 0;
		// (charToInt >= 48 && charToInt <= 57) ||
		for (int i = 0; i < input.length(); i++) {
			charToInt = input.charAt(i);
			if (!((charToInt >= 48 && charToInt <= 57) || charToInt >= 65 && charToInt <= 90 || charToInt >= 96 && charToInt <= 122 || charToInt == 39)) {
				if (!((charToInt == (char) '@') || (charToInt == (char) '.'))) {
					// System.out.println(charToInt);
					return false;
				}

			}

		}
		return true;
	}

	public String cutLeadingZero(String input) {

		int charToInt = 0;
		String output = input.trim();
		for (int i = 0; i < input.trim().length(); i++) {
			charToInt = input.trim().charAt(i);
			if (charToInt == 48) {
				output = output.substring(1);
			} else {
				break;
			}

		}
		return output;
	}

}
