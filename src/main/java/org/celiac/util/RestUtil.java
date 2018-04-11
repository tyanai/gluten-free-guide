package org.celiac.util;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.ws.rs.core.HttpHeaders;

import org.celiac.api.GlutenFreeAPI;
import org.celiac.datatype.ListDao;
import org.celiac.datatype.ResultDao;
import org.celiac.datatype.StringDao;
import org.celiac.util.database.DBQueryFactory;
import org.celiac.web.bean.User;
import org.celiac.web.rest.NotAuthorizedException;

import org.apache.commons.codec.binary.Base64;
import org.celiac.datatype.GFUser;

public class RestUtil implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8935987878250015138L;
	private User user = new User();

	public void validateRestUser(HttpHeaders headers) throws Exception {

		//System.out.println("Starting validating API user...");
		String username = null;
		String password = null;
		List<String> AuthHeader = headers.getRequestHeader(HttpHeaders.AUTHORIZATION);
		Iterator<String> itr = null;

		itr = AuthHeader.iterator();
		
		//BASE64Decoder decoder = new BASE64Decoder();
                
		String decodedString = null;
		while (itr.hasNext()) {
			String authToken = (String) itr.next();
			// System.out.println("authToken " + authToken);
			String userPassword = authToken.substring(6);
			byte[] decoded =  Base64.decodeBase64(userPassword);
			decodedString = new String(decoded);
			// System.out.println("decodedString " + decodedString);
			try{
				StringTokenizer strToken = new StringTokenizer(decodedString, ":");
				username = strToken.nextToken();
				password = strToken.nextToken();
			} catch (Exception e){
				username = "fffffffeeeerrrrr";
				password = "fffffffeeeerrrrr";
			}
		}

		boolean goodUser = false;
		try {
			goodUser = user.loadUser(username, password);
		} catch (Exception e) {
		}

		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.add(java.util.Calendar.MONTH, -1); // Adding 1 month to current date
		long theTime = cal.getTimeInMillis();

		if (!goodUser) {
			throw new NotAuthorizedException("USER_OR_PASS_INCORRECT");
		} else if (user.theUser.getEXPIRATION_DATE() == null) {
			throw new NotAuthorizedException("USER_NOT_EXIST");
		} else if (user.theUser.getACKNOWLEDGE_USE().equals("FALSE")) {
			throw new NotAuthorizedException("USER_NEED_TO_ACKNOWLEDGE_TOU");
		} else if ((user.theUser.getEXPIRATION_DATE().getTime() < theTime) && (!user.maybeUserIsNoExpire())) {
			throw new NotAuthorizedException("USER_EXPIRED");
		}

		// System.out.println("AuthHeader " + i + ": " + decodedString);

	}

	public Set<String> getCategoryList(String product, String manufacture) throws Exception {

		Set<String> output = null;
		if (!"null".equals(product) && !"null".equals(manufacture)){
			if ("APMTO".equals(product.trim())){
				product = "MULTIPLE";
			}
			output = DBQueryFactory.getDBHandler().getCategoryByProductAndCompanyList(product.trim(), manufacture.trim());
		} else {
			output = DBQueryFactory.getDBHandler().getCategoryList();
		}
		return output;
	}

	public Set<String> getManufactureList(String category) throws Exception {

		Set<String> output = null;

		if ("null".equals(category))
			output = DBQueryFactory.getDBHandler().getCompanyList();
		else
			output = DBQueryFactory.getDBHandler().getCompanyByCategoryList(category.trim());

		return output;
	}

	public Set<String> getProductList(String manufacture, String category) throws Exception {

		Set<String> output = null;

		if ("null".equals(category) && !"null".equals(manufacture))
			output = DBQueryFactory.getDBHandler().getProductByCompanyList(manufacture.trim());
		else if (!"null".equals(category) && !"null".equals(manufacture))
			output = DBQueryFactory.getDBHandler().getProductByCategoryAndCompanyList(category.trim(), manufacture.trim());

		if (output.contains("MULTIPLE")) {
			// System.out.println("product list contains multiple");
			//Check how many multiples
			Set<String> multiples = null;
			multiples = DBQueryFactory.getDBHandler().getCategoryByProductAndCompanyList("MULTIPLE", manufacture);

			if (output.size() > 1) {
				if (multiples.size()>1) output.add("OPMTO");
				else output.add("OP");
				
			} else {
				if (multiples.size()>1) output.add("APMTO");
				else output.add("AP");
				
			}

			output.remove("MULTIPLE");

		}
		
		

		return output;
	}

	public StringDao check(String product, String manufacture, String category) throws Exception {

		String searchPattern = "";
		if (!"null".equals(product) && "null".equals(manufacture) && "null".equals(category))
			searchPattern = product.trim();
		else if (!"null".equals(product) && !"null".equals(manufacture) && "null".equals(category))
			searchPattern = product.trim() + "." + manufacture.trim();
		else if (!"null".equals(product) && !"null".equals(manufacture) && !"null".equals(category))
			searchPattern = product.trim() + "." + manufacture.trim() + "." + category.trim();
		else if ("null".equals(product) && !"null".equals(manufacture) && "null".equals(category))
			searchPattern = "general" + "." + manufacture.trim();

		String output = new GlutenFreeAPI().search(searchPattern, user.theUser.getUSER_ID(), true);

		output = output.replaceFirst("<font color=\"#4CC417\">", "GFGRE-");
		output = output.replaceFirst("<font color=\"#FF0000\">", "CGRED-");
		output = output.replaceFirst("<font color=\"#0066FF\">", "GFBLU-");
		output = output.replaceFirst("</font>", "");

		StringDao sd = new StringDao();
		sd.setValue(output);

		return sd;

	}
	
	public ResultDao web_check(String product, String manufacture, String category) throws Exception {

		String searchPattern = "";
		if (!"null".equals(product) && "null".equals(manufacture) && "null".equals(category))
			searchPattern = product.trim();
		else if (!"null".equals(product) && !"null".equals(manufacture) && "null".equals(category))
			searchPattern = product.trim() + "." + manufacture.trim();
		else if (!"null".equals(product) && !"null".equals(manufacture) && !"null".equals(category))
			searchPattern = product.trim() + "." + manufacture.trim() + "." + category.trim();
		else if ("null".equals(product) && !"null".equals(manufacture) && "null".equals(category))
			searchPattern = "general" + "." + manufacture.trim();

		String output = new GlutenFreeAPI().search(searchPattern, user.theUser.getUSER_ID(), true);
		String result = null;
		if (output.indexOf("#4CC417") > 0){
			result = "GFGRE";
		} else if (output.indexOf("#FF0000") > 0){
			result = "CFRED";
		} else if (output.indexOf("#0066FF") > 0){
			result = "GFBLU";
		} else {
			result = "GFMISC";
		}
		
		output = output.replaceFirst("<font color=\"#4CC417\">", "");
		output = output.replaceFirst("<font color=\"#FF0000\">", "");
		output = output.replaceFirst("<font color=\"#0066FF\">", "");
		output = output.replaceFirst("</font>", "");
		
		output = output.replaceAll("\n", "</br>");

		ResultDao sd = new ResultDao();
		sd.setValue(output);
		sd.setResult(result);

		return sd;

	}
	
	public StringDao smallImage(String product, String manufacture, String category) throws Exception {

		byte[] image = null;
		image = DBQueryFactory.getDBHandler().getSmallImage(product, manufacture, category);
		String output = "EMPTY";
		if (image != null ) {
			//BASE64Encoder enc = new BASE64Encoder();
                        
			output = Base64.encodeBase64String(image);
		}
		StringDao sd = new StringDao();
		sd.setValue(output);

		return sd;

	}
	
	public StringDao largeImage(String product, String manufacture, String category) throws Exception {

		byte[] image = null;
		image = DBQueryFactory.getDBHandler().getOrigImage(product, manufacture, category);
		String output = "EMPTY";
		if (image != null ) {
			
			output = Base64.encodeBase64String(image);
		}
		StringDao sd = new StringDao();
		sd.setValue(output);

		return sd;

	}

	public ListDao list(Set<String> input) {

		//System.out.println("Android got output size: " + input.size());

		ListDao output = new ListDao();
		output.setItems(new LinkedList<StringDao>());
		Iterator<String> iter = input.iterator();
		
		String tmp = null;
		
		while (iter.hasNext()) {
			StringDao sd = new StringDao();
			tmp = (String) iter.next();
			if ("".equals(tmp)) continue;
			sd.setValue(tmp);
			output.getItems().add(sd);
		}

		return output;

	}

	public Set<String> search(String product, String manufacture) throws Exception {

		AutoCompleteSearch acs = new AutoCompleteSearch();
		return acs.search(product, manufacture);

	}
        
        public boolean validateInputUserFile(InputStream fileInputStream) throws Exception {

		String userIdentity = null;
		try {
			

			XLSReader xLSReader = new XLSReader();

			

			userIdentity = user.theUser.getFIRST_NAME() + " " + user.theUser.getLAST_NAME() + " (" + user.theUser.getUSER_ID() + ")";
			
			GFUser[] intoDB = xLSReader.readUsersWorkBook(fileInputStream);

			DBWriter dBWriter = new DBWriter();

			

			try {
				dBWriter.dropCompanyTable("TEMP_USERS");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			dBWriter.createUsersTable("TEMP_USERS");
			dBWriter.insertUsersTable(intoDB, "TEMP_USERS");

			dBWriter = new DBWriter();
			try {
				dBWriter.dropCompanyTable("USERS");
			} catch (Exception e) {
				e.printStackTrace();
			}
			

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

}
