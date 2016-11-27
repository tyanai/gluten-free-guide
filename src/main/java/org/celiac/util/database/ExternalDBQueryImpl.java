package org.celiac.util.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.celiac.datatype.GFUser;
import org.celiac.datatype.DiffData;
import org.celiac.datatype.IndexRow;
import org.celiac.datatype.JobDetails;
import org.celiac.datatype.StatisticsData;
import org.celiac.datatype.UsersTable;
import org.celiac.util.ImageUtil;
import org.celiac.util.Logger;
import org.celiac.util.MySQLDriverManager;

public class ExternalDBQueryImpl implements GFQuery {

	public Set<IndexRow> productByName(String text) throws Exception {
		return executeQueryAndExtractIndexRow("SELECT PRODUCT, COMPANY, CATEGORY, GLUTEN_FREE FROM PRODUCT_INDEX WHERE PRODUCT LIKE '%"
				+ text.trim() + "%';");

	}

	public Set<IndexRow> productByCompany(String text, boolean checkForMultiple)
			throws Exception {
		String multipleChoise = null;
		if (checkForMultiple)
			multipleChoise = "=";
		else
			multipleChoise = "<>";
		return executeQueryAndExtractIndexRow("SELECT PRODUCT, COMPANY, CATEGORY, GLUTEN_FREE FROM PRODUCT_INDEX WHERE COMPANY LIKE '%"
				+ text.trim()
				+ "%' AND PRODUCT "
				+ multipleChoise
				+ " 'MULTIPLE';");

	}

	public Set<IndexRow> containGF(String category) throws Exception {
		return executeQueryAndExtractIndexRow("SELECT PRODUCT, COMPANY, CATEGORY, GLUTEN_FREE FROM PRODUCT_INDEX WHERE GLUTEN_FREE = 'NO' AND CATEGORY LIKE '%"
				+ category.trim() + "%' ;");

	}

	public Set<IndexRow> notContainGF(String category) throws Exception {
		return executeQueryAndExtractIndexRow("SELECT PRODUCT, COMPANY, CATEGORY, GLUTEN_FREE FROM PRODUCT_INDEX WHERE GLUTEN_FREE <> 'NO' AND CATEGORY LIKE '%"
				+ category.trim() + "%' ;");

	}

	public Set<String> listOfCompany() throws Exception {

		Set<String> nameList = new HashSet<String>();
		Set<IndexRow> result = new HashSet<IndexRow>();
		result = executeQueryAndExtractIndexRow("SELECT PRODUCT, COMPANY, CATEGORY, GLUTEN_FREE FROM PRODUCT_INDEX;");

		Iterator<IndexRow> iter = result.iterator();

		while (iter.hasNext()) {

			nameList.add(iter.next().getCompany());

		}

		return nameList;

	}

	public Map<String, String> getCodeDescriptionList() throws Exception {

		Map<String, String> output = new HashMap<String, String>();

		Connection con = MySQLDriverManager.getConnection();
		Statement st = null;
		ResultSet rs = null;
		String theQ = "SELECT CODE, DESCRIPTION FROM CODE_DESC;";

		try {
			try {
				st = con.createStatement();
				rs = st.executeQuery(theQ);
			} catch (Exception e) {
				con = MySQLDriverManager.getConnectionAgain();
				st = con.createStatement();
				rs = st.executeQuery(theQ);
			}

			while (rs.next()) {

				output.put(rs.getString(1), rs.getString(2));

			}

		} catch (Exception e) {
			Logger.print("Exception: " + e.getMessage());
			e.printStackTrace();
		} finally {
			doFinally(st, rs);
		}

		return output;

	}

	public String gfNOByCompanyAndCategory(String company, String category)
			throws Exception {

		Set<IndexRow> tmpSet = executeQueryAndExtractIndexRow("SELECT PRODUCT, COMPANY, CATEGORY, GLUTEN_FREE FROM PRODUCT_INDEX WHERE COMPANY = '"
				+ company.trim()
				+ "' AND CATEGORY = '"
				+ category.trim()
				+ "';");

		String output = null;
		Iterator<IndexRow> iter = tmpSet.iterator();
		while (iter.hasNext()) {
			IndexRow indexRow = iter.next();
			if ((indexRow.getCompany().trim().equals(company))
					&& ((indexRow.getCategory().trim().equals(category)))) {
				if (indexRow.getProduct().equals("MULTIPLE")
						|| !indexRow.getGlutenFree().equals("NO")) {
					// do nothing
				} else {
					if (output == null)
						output = indexRow.getProduct();
					else
						output = output + "," + indexRow.getProduct();
				}
			}

		}
		return output;
	}

	public boolean isACompany(String company) throws Exception {

		Set<IndexRow> companyList = executeQueryAndExtractIndexRow("SELECT PRODUCT, COMPANY, CATEGORY, GLUTEN_FREE FROM PRODUCT_INDEX WHERE COMPANY like '%"
				+ company.trim() + "%';");

		if (companyList.size() > 0)
			return true;
		else
			return false;

	}

	public String likeACompany(String company) throws Exception {

		Set<IndexRow> companyList = executeQueryAndExtractIndexRow("SELECT PRODUCT, COMPANY, CATEGORY, GLUTEN_FREE FROM PRODUCT_INDEX WHERE COMPANY like '%"
				+ company.trim() + "%';");

		if (companyList.size() > 0)
			return ((IndexRow) companyList.toArray()[0]).getCompany();
		else
			return company;

	}

	private void doFinally(Statement st, ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Set<IndexRow> productByNameAndCompany(String aProduct,
			String aCompany) throws Exception {
		return executeQueryAndExtractIndexRow("SELECT PRODUCT, COMPANY, CATEGORY, GLUTEN_FREE FROM PRODUCT_INDEX WHERE PRODUCT LIKE '%"
				+ aProduct.trim()
				+ "%' AND COMPANY LIKE '%"
				+ aCompany.trim()
				+ "%';");

	}

	public Set<IndexRow> productByNameAndCompanyAndCategory(String aProduct,
			String aCompany, String aCategory) throws Exception {
		return executeQueryAndExtractIndexRow("SELECT PRODUCT, COMPANY, CATEGORY, GLUTEN_FREE FROM PRODUCT_INDEX WHERE PRODUCT LIKE '%"
				+ aProduct.trim()
				+ "%' AND COMPANY LIKE '%"
				+ aCompany.trim()
				+ "%' AND CATEGORY LIKE '%" + aCategory.trim() + "%';");
	}

	public Set<IndexRow> companyByCategory(String aCategory) throws Exception {
		return executeQueryAndExtractIndexRow("SELECT  PRODUCT, COMPANY, CATEGORY, GLUTEN_FREE FROM PRODUCT_INDEX WHERE CATEGORY LIKE '%"
				+ aCategory.trim() + "%';");
	}

	private Set<IndexRow> executeQueryAndExtractIndexRow(String query)
			throws Exception {
		Connection con = MySQLDriverManager.getConnection();
		Statement st = null;
		ResultSet rs = null;
		Set<IndexRow> result = new HashSet<IndexRow>();

		try {
			try {
				st = con.createStatement();
				rs = st.executeQuery(query);
			} catch (Exception e) {
				con = MySQLDriverManager.getConnectionAgain();
				st = con.createStatement();
				rs = st.executeQuery(query);
			}
			result = extractIndexRow(rs);

		} catch (Exception e) {
			Logger.print("Exception: " + e.getMessage());
			e.printStackTrace();
		} finally {
			doFinally(st, rs);
		}

		return result;
	}

	public Set<IndexRow> companyByCategoryAndCompany(String aCategory,
			String aCompany) throws Exception {
		return executeQueryAndExtractIndexRow("SELECT  PRODUCT, COMPANY, CATEGORY, GLUTEN_FREE FROM PRODUCT_INDEX WHERE CATEGORY LIKE '%"
				+ aCategory.trim()
				+ "%' AND COMPANY LIKE '%"
				+ aCompany.trim()
				+ "%';");
	}

	private Set<IndexRow> extractIndexRow(ResultSet rs) throws Exception {

		Set<IndexRow> output = new HashSet<IndexRow>();

		IndexRow indexRow = null;
		while (rs.next()) {
			indexRow = new IndexRow();
			indexRow.setProduct(rs.getString(1));
			indexRow.setCompany(rs.getString(2));
			indexRow.setCategory(rs.getString(3));
			indexRow.setGlutenFree(rs.getString(4));
			output.add(indexRow);
		}

		return output;

	}

	public GFUser getUser(String cellNum) throws Exception {
		/*
		 * String cell = cellNum; if (cell.startsWith("972")) cell = "0" +
		 * cell.substring(3); else cell = "0" + cell;
		 */
		return getUser(cellNum, "CELL_NUM");
	}

	public GFUser getUserByUserID(String userId) throws Exception {

		return getUser(userId, "USER_ID");
	}

	public GFUser getUserByGeneric(String userId, String type) throws Exception {

		return getUser(userId, type);
	}

	private GFUser getUser(String input, String field) throws Exception {

		Connection con = MySQLDriverManager.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		GFUser gfUser = null;

		String userDetailsquery = null;

		String userQuery = null;
		String fieldCandidate = null;

		if ("USER_ID".equals(field)
				|| "CELIAC_MEMBER_ID_FROM_USER_DETAILS".equals(field)) {

			if ("USER_ID".equals(field))
				fieldCandidate = "USER_ID";
			if ("CELIAC_MEMBER_ID_FROM_USER_DETAILS".equals(field))
				fieldCandidate = "CELIAC_MEMBER_ID";

			userDetailsquery = "SELECT  CELL_NUM,FIRST_NAME, LAST_NAME, DATE_FORMAT(EFFECTIVE_DATE, '%Y-%m-%e'),DATE_FORMAT(LAST_LOGIN_DATE, '%Y-%m-%d'),ACKNOWLEDGE_USE,PERMISSIONS,USER_ID,PASSWORD, EMAIL,CELIAC_MEMBER_ID FROM USER_DETAILS WHERE "
					+ fieldCandidate + " = ? ";
			userQuery = "SELECT  CELIAC_MEMBER_ID,DID_BUY_THE_BOOK, USER_TZ, DATE_FORMAT(EXPIRATION_DATE, '%Y-%m-%d') FROM USERS WHERE CELIAC_MEMBER_ID = ? ";
			try {
				// First get from user_details
				try {
					st = con.prepareStatement(userDetailsquery);
					st.setString(1, input);
					rs = st.executeQuery();

				} catch (Exception e) {
					con = MySQLDriverManager.getConnectionAgain();
					st = con.prepareStatement(userDetailsquery);
					st.setString(1, input);
					rs = st.executeQuery();
				}

				while (rs.next()) {
					gfUser = new GFUser();
					gfUser.setCELL_NUM(rs.getString(1));
					gfUser.setFIRST_NAME(rs.getString(2));
					gfUser.setLAST_NAME(rs.getString(3));
					gfUser.setEFFECTIVE_DATE(UsersTable.MySqlStringToDate(rs
							.getString(4)));
					gfUser.setLAST_LOGIN_DATE(UsersTable.MySqlStringToDate(rs
							.getString(5)));
					gfUser.setACKNOWLEDGE_USE(rs.getString(6));
					gfUser.setPERMISSIONS(rs.getString(7));
					gfUser.setUSER_ID(rs.getString(8));
					gfUser.setPASSWORD(rs.getString(9));
					gfUser.setEMAIL(rs.getString(10));
					gfUser.setCELIAC_MEMBER_ID(rs.getString(11));

				}

				// Now get from Users
				if (gfUser != null) {
					try {
						st = con.prepareStatement(userQuery);
						st.setString(1, gfUser.getCELIAC_MEMBER_ID());
						rs = st.executeQuery();

					} catch (Exception e) {
						con = MySQLDriverManager.getConnectionAgain();
						st = con.prepareStatement(userQuery);
						st.setString(1, gfUser.getCELIAC_MEMBER_ID());
						rs = st.executeQuery();
					}
					while (rs.next()) {
						gfUser.setCELIAC_MEMBER_ID(rs.getString(1));
						gfUser.setDID_BUY_THE_BOOK(rs.getString(2));
						gfUser.setUSER_TZ(rs.getString(3));
						gfUser.setEXPIRATION_DATE(UsersTable
								.MySqlStringToDate(rs.getString(4)));
					}

				}

			} catch (Exception e) {
				Logger.print("Exception: " + e.getMessage());
				e.printStackTrace();
			} finally {
				doFinally(st, rs);
			}
		} else {
			userDetailsquery = "SELECT  CELL_NUM,FIRST_NAME, LAST_NAME, DATE_FORMAT(EFFECTIVE_DATE, '%Y-%m-%e'),DATE_FORMAT(LAST_LOGIN_DATE, '%Y-%m-%d'),ACKNOWLEDGE_USE,PERMISSIONS,USER_ID,PASSWORD, EMAIL FROM USER_DETAILS WHERE CELIAC_MEMBER_ID = ? ";
			userQuery = "SELECT  CELIAC_MEMBER_ID,DID_BUY_THE_BOOK, USER_TZ, DATE_FORMAT(EXPIRATION_DATE, '%Y-%m-%d') FROM USERS WHERE "
					+ field + " = ? ";
			try {
				// First get from users
				try {
					st = con.prepareStatement(userQuery);
					st.setString(1, input);
					rs = st.executeQuery();

				} catch (Exception e) {
					con = MySQLDriverManager.getConnectionAgain();
					st = con.prepareStatement(userQuery);
					st.setString(1, input);
					rs = st.executeQuery();
				}

				while (rs.next()) {
					gfUser = new GFUser();
					gfUser.setCELIAC_MEMBER_ID(rs.getString(1));
					gfUser.setDID_BUY_THE_BOOK(rs.getString(2));
					gfUser.setUSER_TZ(rs.getString(3));
					gfUser.setEXPIRATION_DATE(UsersTable.MySqlStringToDate(rs
							.getString(4)));

				}

				// Now get from User_details
				if (gfUser != null) {
					try {
						st = con.prepareStatement(userDetailsquery);
						st.setString(1, gfUser.getCELIAC_MEMBER_ID());
						rs = st.executeQuery();

					} catch (Exception e) {
						con = MySQLDriverManager.getConnectionAgain();
						st = con.prepareStatement(userDetailsquery);
						st.setString(1, gfUser.getCELIAC_MEMBER_ID());
						rs = st.executeQuery();
					}
					while (rs.next()) {
						gfUser.setCELL_NUM(rs.getString(1));
						gfUser.setFIRST_NAME(rs.getString(2));
						gfUser.setLAST_NAME(rs.getString(3));
						gfUser.setEFFECTIVE_DATE(UsersTable
								.MySqlStringToDate(rs.getString(4)));
						gfUser.setLAST_LOGIN_DATE(UsersTable
								.MySqlStringToDate(rs.getString(5)));
						gfUser.setACKNOWLEDGE_USE(rs.getString(6));
						gfUser.setPERMISSIONS(rs.getString(7));
						gfUser.setUSER_ID(rs.getString(8));
						gfUser.setPASSWORD(rs.getString(9));
						gfUser.setEMAIL(rs.getString(10));
					}

				}

			} catch (Exception e) {
				Logger.print("Exception: " + e.getMessage());
				e.printStackTrace();
			} finally {
				doFinally(st, rs);
			}
		}

		/*
		 * try {
		 * 
		 * try { st = con.createStatement(); rs = st.executeQuery(query); }
		 * catch (Exception e) { con = MySQLDriverManager.getConnectionAgain();
		 * st = con.createStatement(); rs = st.executeQuery(query); }
		 * 
		 * while (rs.next()) { gfUser = new GFUser();
		 * gfUser.setCELL_NUM(rs.getString(1));
		 * gfUser.setFIRST_NAME(rs.getString(2));
		 * gfUser.setLAST_NAME(rs.getString(3));
		 * gfUser.setEFFECTIVE_DATE(UsersTable
		 * .MySqlStringToDate(rs.getString(4)));
		 * gfUser.setEXPIRATION_DATE(UsersTable
		 * .MySqlStringToDate(rs.getString(5)));
		 * gfUser.setLAST_LOGIN_DATE(UsersTable
		 * .MySqlStringToDate(rs.getString(6)));
		 * gfUser.setACKNOWLEDGE_USE(rs.getString(7));
		 * gfUser.setPERMISSIONS(rs.getString(8));
		 * gfUser.setUSER_ID(rs.getString(9));
		 * gfUser.setPASSWORD(rs.getString(10));
		 * gfUser.setCELIAC_MEMBER_ID(rs.getString(11));
		 * gfUser.setDID_BUY_THE_BOOK(rs.getString(12));
		 * gfUser.setEMAIL(rs.getString(13));
		 * gfUser.setUSER_TZ(rs.getString(14)); } } catch (Exception e) {
		 * Logger.print("Exception: " + e.getMessage()); e.printStackTrace(); }
		 * finally { doFinally(st, rs); }
		 */

		return gfUser;
	}
	
	public GFUser getUserByEmail(String input) throws Exception {

		Connection con = MySQLDriverManager.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		GFUser gfUser = null;

		String userDetailsquery = null;

		String userQuery = null;
		String fieldCandidate = "EMAIL";

	
			userDetailsquery = "SELECT  CELL_NUM,FIRST_NAME, LAST_NAME, DATE_FORMAT(EFFECTIVE_DATE, '%Y-%m-%e'),DATE_FORMAT(LAST_LOGIN_DATE, '%Y-%m-%d'),ACKNOWLEDGE_USE,PERMISSIONS,USER_ID,PASSWORD, EMAIL,CELIAC_MEMBER_ID FROM USER_DETAILS WHERE "
					+ fieldCandidate + " = ? ";
			userQuery = "SELECT  CELIAC_MEMBER_ID,DID_BUY_THE_BOOK, USER_TZ, DATE_FORMAT(EXPIRATION_DATE, '%Y-%m-%d') FROM USERS WHERE CELIAC_MEMBER_ID = ? ";
			try {
				// First get from user_details
				try {
					st = con.prepareStatement(userDetailsquery);
					st.setString(1, input);
					rs = st.executeQuery();

				} catch (Exception e) {
					con = MySQLDriverManager.getConnectionAgain();
					st = con.prepareStatement(userDetailsquery);
					st.setString(1, input);
					rs = st.executeQuery();
				}

				while (rs.next()) {
					gfUser = new GFUser();
					gfUser.setCELL_NUM(rs.getString(1));
					gfUser.setFIRST_NAME(rs.getString(2));
					gfUser.setLAST_NAME(rs.getString(3));
					gfUser.setEFFECTIVE_DATE(UsersTable.MySqlStringToDate(rs
							.getString(4)));
					gfUser.setLAST_LOGIN_DATE(UsersTable.MySqlStringToDate(rs
							.getString(5)));
					gfUser.setACKNOWLEDGE_USE(rs.getString(6));
					gfUser.setPERMISSIONS(rs.getString(7));
					gfUser.setUSER_ID(rs.getString(8));
					gfUser.setPASSWORD(rs.getString(9));
					gfUser.setEMAIL(rs.getString(10));
					gfUser.setCELIAC_MEMBER_ID(rs.getString(11));

				}

				// Now get from Users
				if (gfUser != null) {
					try {
						st = con.prepareStatement(userQuery);
						st.setString(1, gfUser.getCELIAC_MEMBER_ID());
						rs = st.executeQuery();

					} catch (Exception e) {
						con = MySQLDriverManager.getConnectionAgain();
						st = con.prepareStatement(userQuery);
						st.setString(1, gfUser.getCELIAC_MEMBER_ID());
						rs = st.executeQuery();
					}
					while (rs.next()) {
						gfUser.setCELIAC_MEMBER_ID(rs.getString(1));
						gfUser.setDID_BUY_THE_BOOK(rs.getString(2));
						gfUser.setUSER_TZ(rs.getString(3));
						gfUser.setEXPIRATION_DATE(UsersTable
								.MySqlStringToDate(rs.getString(4)));
					}

				}

			} catch (Exception e) {
				Logger.print("Exception: " + e.getMessage());
				e.printStackTrace();
			} finally {
				doFinally(st, rs);
			}
		

		return gfUser;
	}

	public Set<GFUser> getAllUserDetails() throws Exception {

		Connection con = MySQLDriverManager.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		GFUser gfUser = null;
		Set<GFUser> output = new LinkedHashSet<GFUser>();

		String userDetailsquery = "SELECT  CELL_NUM,FIRST_NAME, LAST_NAME, DATE_FORMAT(EFFECTIVE_DATE, '%Y-%m-%e'),DATE_FORMAT(LAST_LOGIN_DATE, '%Y-%m-%d'),ACKNOWLEDGE_USE,PERMISSIONS,USER_ID,PASSWORD, EMAIL, CELIAC_MEMBER_ID FROM USER_DETAILS order by CELIAC_MEMBER_ID";

		try {

			try {
				st = con.prepareStatement(userDetailsquery);
				rs = st.executeQuery();

			} catch (Exception e) {
				con = MySQLDriverManager.getConnectionAgain();
				st = con.prepareStatement(userDetailsquery);
				rs = st.executeQuery();
			}
			while (rs.next()) {
				gfUser = new GFUser();
				gfUser.setCELL_NUM(rs.getString(1));
				gfUser.setFIRST_NAME(rs.getString(2));
				gfUser.setLAST_NAME(rs.getString(3));
				gfUser.setEFFECTIVE_DATE(UsersTable.MySqlStringToDate(rs
						.getString(4)));
				gfUser.setLAST_LOGIN_DATE(UsersTable.MySqlStringToDate(rs
						.getString(5)));
				gfUser.setACKNOWLEDGE_USE(rs.getString(6));
				gfUser.setPERMISSIONS(rs.getString(7));
				gfUser.setUSER_ID(rs.getString(8));
				gfUser.setPASSWORD(rs.getString(9));
				gfUser.setEMAIL(rs.getString(10));
				gfUser.setCELIAC_MEMBER_ID(rs.getString(11));
				output.add(gfUser);
			}

		} catch (Exception e) {
			Logger.print("Exception: " + e.getMessage());
			e.printStackTrace();
		} finally {
			doFinally(st, rs);
		}

		return output;
	}

	public void insertUser(GFUser theUser) throws Exception {

		Connection con = MySQLDriverManager.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;

		String query = "INSERT INTO `USERS` (CELIAC_MEMBER_ID,USER_TZ,DID_BUY_THE_BOOK,EXPIRATION_DATE) VALUES (?,?,?,?)";

		try {

			try {
				st = con.prepareStatement(query);
				st.setString(1, theUser.getCELIAC_MEMBER_ID());
				st.setString(2, theUser.getUSER_TZ());
				st.setString(3, theUser.getDID_BUY_THE_BOOK());
				st.setDate(4, new java.sql.Date(theUser.getEXPIRATION_DATE()
						.getTime()));
				st.execute();
			} catch (Exception e) {
				con = MySQLDriverManager.getConnectionAgain();
				st = con.prepareStatement(query);
				st.setString(1, theUser.getCELIAC_MEMBER_ID());
				st.setString(2, theUser.getUSER_TZ());
				st.setString(3, theUser.getDID_BUY_THE_BOOK());
				st.setDate(4, new java.sql.Date(theUser.getEXPIRATION_DATE()
						.getTime()));
				st.execute();
			}

			// con.commit();

		} catch (Exception e) {
			Logger.error("Exception: " + e.getMessage());
			e.printStackTrace();
		} finally {
			doFinally(st, rs);
		}

	}

	public void insertFullUser(GFUser theUser) throws Exception {

		insertUser(theUser);

		Connection con = MySQLDriverManager.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;

		String query = "INSERT INTO `USER_DETAILS` (EFFECTIVE_DATE,CELL_NUM,FIRST_NAME,LAST_NAME,USER_ID,PASSWORD,PERMISSIONS,LAST_LOGIN_DATE,ACKNOWLEDGE_USE,EMAIL,CELIAC_MEMBER_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?)";

		try {

			try {
				st = con.prepareStatement(query);
				st.setDate(1, new java.sql.Date(new Date().getTime()));
				st.setString(2, theUser.getCELL_NUM());
				st.setString(3, theUser.getFIRST_NAME());
				st.setString(4, theUser.getLAST_NAME());
				st.setString(5, theUser.getUSER_ID());
				st.setString(6, theUser.getPASSWORD());
				st.setString(7, theUser.getPERMISSIONS());
				st.setDate(8, new java.sql.Date(theUser.getLAST_LOGIN_DATE()
						.getTime()));
				st.setString(9, theUser.getACKNOWLEDGE_USE());
				st.setString(10, theUser.getEMAIL());
				st.setString(11, theUser.getCELIAC_MEMBER_ID());
				st.execute();
			} catch (Exception e) {
				con = MySQLDriverManager.getConnectionAgain();
				st = con.prepareStatement(query);
				st.setDate(1, new java.sql.Date(new Date().getTime()));
				st.setString(2, theUser.getCELL_NUM());
				st.setString(3, theUser.getFIRST_NAME());
				st.setString(4, theUser.getLAST_NAME());
				st.setString(5, theUser.getUSER_ID());
				st.setString(6, theUser.getPASSWORD());
				st.setString(7, theUser.getPERMISSIONS());
				st.setDate(8, new java.sql.Date(theUser.getLAST_LOGIN_DATE()
						.getTime()));
				st.setString(9, theUser.getACKNOWLEDGE_USE());
				st.setString(10, theUser.getEMAIL());
				st.setString(11, theUser.getCELIAC_MEMBER_ID());
				st.execute();
			}

			// con.commit();

		} catch (Exception e) {
			Logger.error("Exception: " + e.getMessage());
			e.printStackTrace();
		} finally {
			doFinally(st, rs);
		}

	}

	public void deleteUser(GFUser theUser) throws Exception {

		Connection con = MySQLDriverManager.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;

		String query = "DELETE FROM `USER_DETAILS` WHERE USER_ID=? AND CELIAC_MEMBER_ID=?";

		try {

			try {
				st = con.prepareStatement(query);
				st.setString(1, theUser.getUSER_ID());
				st.setString(2, theUser.getCELIAC_MEMBER_ID());
				st.execute();
			} catch (Exception e) {
				con = MySQLDriverManager.getConnectionAgain();
				st = con.prepareStatement(query);
				st.setString(1, theUser.getUSER_ID());
				st.setString(2, theUser.getCELIAC_MEMBER_ID());
				st.execute();
			}

			// con.commit();

		} catch (Exception e) {
			Logger.error("Exception: " + e.getMessage());
			e.printStackTrace();
		} finally {
			doFinally(st, rs);
		}

	}

	public void updateUser(GFUser gfUser) throws Exception {

		updateBasicUser(gfUser);

		String query = null;
		boolean isUpdate = true;

		if (this.getUser(gfUser.getCELIAC_MEMBER_ID(),
				"CELIAC_MEMBER_ID_FROM_USER_DETAILS") == null) {
			// User don't have an entry in user_details yet, need to insert, not
			// update
			query = "INSERT INTO `USER_DETAILS` (FIRST_NAME,LAST_NAME,PASSWORD,PERMISSIONS,LAST_LOGIN_DATE,ACKNOWLEDGE_USE,CELL_NUM,USER_ID,EFFECTIVE_DATE,EMAIL, CELIAC_MEMBER_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
			isUpdate = false;
		} else {

			query = "UPDATE `USER_DETAILS` set FIRST_NAME = ?,LAST_NAME = ? ,PASSWORD = ?,PERMISSIONS = ?, LAST_LOGIN_DATE = ?,ACKNOWLEDGE_USE = ?, CELL_NUM=?, USER_ID=?, EFFECTIVE_DATE=?, EMAIL=?, CELIAC_MEMBER_ID=? where CELIAC_MEMBER_ID = ? ";
		}

		Connection con = MySQLDriverManager.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;

		try {

			try {
				st = con.prepareStatement(query);
				st.setString(1, gfUser.getFIRST_NAME());
				st.setString(2, gfUser.getLAST_NAME());
				st.setString(3, gfUser.getPASSWORD());
				st.setString(4, gfUser.getPERMISSIONS());
				st.setDate(5, new java.sql.Date(gfUser.getLAST_LOGIN_DATE()
						.getTime()));
				st.setString(6, gfUser.getACKNOWLEDGE_USE());
				st.setString(7, gfUser.getCELL_NUM());
				st.setString(8, gfUser.getUSER_ID());
				st.setDate(9, new java.sql.Date(gfUser.getEFFECTIVE_DATE()
						.getTime()));
				st.setString(10, gfUser.getEMAIL());
				st.setString(11, gfUser.getCELIAC_MEMBER_ID());

				if (isUpdate) {
					st.setString(12, gfUser.getCELIAC_MEMBER_ID());
					st.executeUpdate();
				} else {
					st.execute();
				}

			} catch (Exception e) {
				con = MySQLDriverManager.getConnectionAgain();
				st = con.prepareStatement(query);
				st.setString(1, gfUser.getFIRST_NAME());
				st.setString(2, gfUser.getLAST_NAME());
				st.setString(3, gfUser.getPASSWORD());
				st.setString(4, gfUser.getPERMISSIONS());
				st.setDate(5, new java.sql.Date(gfUser.getLAST_LOGIN_DATE()
						.getTime()));
				st.setString(6, gfUser.getACKNOWLEDGE_USE());
				st.setString(7, gfUser.getCELL_NUM());
				st.setString(8, gfUser.getUSER_ID());
				st.setDate(9, new java.sql.Date(gfUser.getEFFECTIVE_DATE()
						.getTime()));
				st.setString(10, gfUser.getEMAIL());
				st.setString(11, gfUser.getCELIAC_MEMBER_ID());
				if (isUpdate) {
					st.setString(12, gfUser.getCELIAC_MEMBER_ID());
					st.executeUpdate();
				} else {
					st.execute();
				}
			}

		} catch (Exception e) {
			Logger.print("Exception: " + e.getMessage());
			e.printStackTrace();
		} finally {
			doFinally(st, rs);
		}

	}

	public void updateBasicUser(GFUser gfUser) throws Exception {

		Connection con = MySQLDriverManager.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;

		String query = "UPDATE `USERS` set  CELIAC_MEMBER_ID=?, USER_TZ=?, DID_BUY_THE_BOOK=?, EXPIRATION_DATE=? where CELIAC_MEMBER_ID = ? ";

		try {

			try {
				st = con.prepareStatement(query);

				st.setString(1, gfUser.getCELIAC_MEMBER_ID());
				st.setString(2, gfUser.getUSER_TZ());
				st.setString(3, gfUser.getDID_BUY_THE_BOOK());
				st.setDate(4, new java.sql.Date(gfUser.getEXPIRATION_DATE()
						.getTime()));
				st.setString(5, gfUser.getCELIAC_MEMBER_ID());

				st.executeUpdate();
			} catch (Exception e) {
				con = MySQLDriverManager.getConnectionAgain();
				st = con.prepareStatement(query);
				st.setString(1, gfUser.getCELIAC_MEMBER_ID());
				st.setString(2, gfUser.getUSER_TZ());
				st.setString(3, gfUser.getDID_BUY_THE_BOOK());
				st.setDate(4, new java.sql.Date(gfUser.getEXPIRATION_DATE()
						.getTime()));
				st.setString(5, gfUser.getCELIAC_MEMBER_ID());
				st.executeUpdate();
			}

		} catch (Exception e) {
			Logger.print("Exception: " + e.getMessage());
			e.printStackTrace();
		} finally {
			doFinally(st, rs);
		}

	}

	public String getDateofCompany(String company) throws Exception {

		Connection con = MySQLDriverManager.getConnection();
		Statement st = null;
		ResultSet rs = null;
		String output = "";

		String query = "SELECT DATE FROM PRODUCT_INDEX_DATES WHERE COMPANY = '"
				+ company.trim() + "';";

		try {

			try {
				st = con.createStatement();
				rs = st.executeQuery(query);
			} catch (Exception e) {
				con = MySQLDriverManager.getConnectionAgain();
				st = con.createStatement();
				rs = st.executeQuery(query);
			}

			while (rs.next()) {
				output = rs.getString(1);
			}

		} catch (Exception e) {
			Logger.print("Exception: " + e.getMessage());
			e.printStackTrace();
		} finally {
			doFinally(st, rs);
		}
		return output;
	}

	public StatisticsData[] getStatisticsData(Date aStart, Date aEnd)
			throws Exception {

		Date start = aStart;
		Date end = aEnd;
		Connection con = MySQLDriverManager.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		StatisticsData[] output = null;
		List<StatisticsData> results = new ArrayList<StatisticsData>();
		StatisticsData statisticsData = null;

		String query = "SELECT DATE,RETURNED_1,RETURNED_2,RETURNED_3,RETURNED_4,RETURNED_5,WEB_USER FROM STATISTICS where DATE between ? AND ? ";

		if (start == null || end == null) {
			start = new Date();
			end = new Date(start.getTime() + 1000 * 60 * 60 * 24);
		}

		try {

			try {
				st = con.prepareStatement(query);
				st.setDate(1, new java.sql.Date(start.getTime()));
				st.setDate(2, new java.sql.Date(end.getTime()));
				rs = st.executeQuery();
			} catch (Exception e) {
				con = MySQLDriverManager.getConnectionAgain();
				st = con.prepareStatement(query);
				st.setDate(1, new java.sql.Date(start.getTime()));
				st.setDate(2, new java.sql.Date(end.getTime()));
				rs = st.executeQuery();
			}

			while (rs.next()) {
				statisticsData = new StatisticsData();
				statisticsData.setDate(rs.getDate(1));
				statisticsData.setReturned1(rs.getInt(2));
				statisticsData.setReturned2(rs.getInt(3));
				statisticsData.setReturned3(rs.getInt(4));
				statisticsData.setReturned4(rs.getInt(5));
				statisticsData.setReturned5(rs.getInt(6));
				statisticsData.setWebUserCount(rs.getInt(7));
				results.add(statisticsData);

			}

		} catch (Exception e) {
			Logger.print("Exception: " + e.getMessage());
			e.printStackTrace();
		} finally {
			doFinally(st, rs);
		}

		if (results.size() == 0) {
			output = new StatisticsData[1];
			output[0] = new StatisticsData();
		} else {
			output = new StatisticsData[results.size()];
			Iterator<StatisticsData> iter = results.iterator();
			int index = 0;
			while (iter.hasNext()) {
				output[index++] = (StatisticsData) iter.next();
			}
		}

		return output;

	}

	public void updateStatsticsData(StatisticsData data) throws Exception {

		// Get the current StatisticsData
		StatisticsData statisticsData = getStatisticsData(null, null)[0];
		statisticsData.setReturned1(statisticsData.getReturned1()
				+ data.getReturned1());
		statisticsData.setReturned2(statisticsData.getReturned2()
				+ data.getReturned2());
		statisticsData.setReturned3(statisticsData.getReturned3()
				+ data.getReturned3());
		statisticsData.setReturned4(statisticsData.getReturned4()
				+ data.getReturned4());
		statisticsData.setReturned5(statisticsData.getReturned5()
				+ data.getReturned5());
		statisticsData.setWebUserCount(statisticsData.getWebUserCount()
				+ data.getWebUserCount());

		Connection con = MySQLDriverManager.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			if (statisticsData.getDate() == null) {
				// A new date
				String query = "INSERT INTO `STATISTICS` (DATE,RETURNED_1,RETURNED_2,RETURNED_3,RETURNED_4,RETURNED_5,WEB_USER) VALUES (?,?,?,?,?,?,?)";
				try {
					st = con.prepareStatement(query);
					st.setDate(1, new java.sql.Date(new Date().getTime()));
					st.setInt(2, statisticsData.getReturned1());
					st.setInt(3, statisticsData.getReturned2());
					st.setInt(4, statisticsData.getReturned3());
					st.setInt(5, statisticsData.getReturned4());
					st.setInt(6, statisticsData.getReturned5());
					st.setInt(7, statisticsData.getWebUserCount());
					st.execute();
				} catch (Exception e) {
					con = MySQLDriverManager.getConnectionAgain();
					st = con.prepareStatement(query);
					st.setDate(1, new java.sql.Date(new Date().getTime()));
					st.setInt(2, statisticsData.getReturned1());
					st.setInt(3, statisticsData.getReturned2());
					st.setInt(4, statisticsData.getReturned3());
					st.setInt(5, statisticsData.getReturned4());
					st.setInt(6, statisticsData.getReturned5());
					st.setInt(7, statisticsData.getWebUserCount());
					st.execute();
				}
			} else {

				String query = "UPDATE `STATISTICS` set RETURNED_1 = ?,RETURNED_2 = ? ,RETURNED_3 = ?,RETURNED_4 = ?, RETURNED_5 = ?,WEB_USER = ? where DATE = ?";

				try {
					st = con.prepareStatement(query);
					st.setInt(1, statisticsData.getReturned1());
					st.setInt(2, statisticsData.getReturned2());
					st.setInt(3, statisticsData.getReturned3());
					st.setInt(4, statisticsData.getReturned4());
					st.setInt(5, statisticsData.getReturned5());
					st.setInt(6, statisticsData.getWebUserCount());
					st.setDate(7, new java.sql.Date(new Date().getTime()));
					st.executeUpdate();
				} catch (Exception e) {
					con = MySQLDriverManager.getConnectionAgain();
					st = con.prepareStatement(query);
					st.setInt(1, statisticsData.getReturned1());
					st.setInt(2, statisticsData.getReturned2());
					st.setInt(3, statisticsData.getReturned3());
					st.setInt(4, statisticsData.getReturned4());
					st.setInt(5, statisticsData.getReturned5());
					st.setInt(6, statisticsData.getWebUserCount());
					st.setDate(7, new java.sql.Date(new Date().getTime()));
					st.executeUpdate();
				}
			}

		} catch (Exception e) {
			Logger.print("Exception: " + e.getMessage());
			e.printStackTrace();
		} finally {
			doFinally(st, rs);
		}
	}

	private SortedSet<String> executeQueryAndExtractString(String query)
			throws Exception {

		// System.out.println(query);
		// Logger.print(query);

		Connection con = MySQLDriverManager.getConnection();
		Statement st = null;
		ResultSet rs = null;
		SortedSet<String> result = new TreeSet<String>();

		try {
			try {
				st = con.createStatement();
				rs = st.executeQuery(query);
			} catch (Exception e) {
				con = MySQLDriverManager.getConnectionAgain();
				st = con.createStatement();
				rs = st.executeQuery(query);
			}

			String row = null;
			while (rs.next()) {
				row = rs.getString(1);
				result.add(row);
			}

		} catch (Exception e) {
			Logger.print("Exception: " + e.getMessage());
			e.printStackTrace();
		} finally {
			doFinally(st, rs);
		}

		return result;
	}

	public SortedSet<String> getCompanyList() throws Exception {
		return executeQueryAndExtractString("SELECT DISTINCT(COMPANY) FROM PRODUCT_INDEX ;");

	}

	public SortedSet<String> getProductByCompanyList(String text)
			throws Exception {
		return executeQueryAndExtractString("SELECT PRODUCT FROM PRODUCT_INDEX WHERE COMPANY = '"
				+ text.trim() + "';");

	}

	public SortedSet<String> getProducts() throws Exception {
		return executeQueryAndExtractString("SELECT PRODUCT FROM PRODUCT_INDEX;");

	}

	public SortedSet<String> getCategoryByProductAndCompanyList(String product,
			String company) throws Exception {
		String tmp = product;
		if (product.equals("other"))
			tmp = "MULTIPLE";
		return executeQueryAndExtractString("SELECT CATEGORY FROM PRODUCT_INDEX WHERE COMPANY = '"
				+ company.trim() + "' AND PRODUCT = '" + tmp.trim() + "';");

	}

	public SortedSet<String> getCategoryList() throws Exception {
		return executeQueryAndExtractString("SELECT DISTINCT(CATEGORY) FROM PRODUCT_INDEX ;");

	}

	public SortedSet<String> getCategoryListThatContainsGluten()
			throws Exception {
		return executeQueryAndExtractString("SELECT DISTINCT(CATEGORY) FROM PRODUCT_INDEX where GLUTEN_FREE = 'NO' ;");

	}

	public SortedSet<String> getCategoryListThatDoNotContainsGluten()
			throws Exception {
		return executeQueryAndExtractString("SELECT DISTINCT(CATEGORY) FROM PRODUCT_INDEX where GLUTEN_FREE <> 'NO' ;");

	}

	public SortedSet<String> getCompanyByCategoryList(String text)
			throws Exception {
		return executeQueryAndExtractString("SELECT COMPANY FROM PRODUCT_INDEX WHERE CATEGORY like '%"
				+ text.trim() + "%';");

	}

	public SortedSet<String> getProductByCategoryAndCompanyList(
			String category, String company) throws Exception {

		return executeQueryAndExtractString("SELECT PRODUCT FROM PRODUCT_INDEX WHERE COMPANY = '"
				+ company.trim()
				+ "' AND CATEGORY like '%"
				+ category.trim()
				+ "%';");

	}

	public void insertImage(String product, String company, String category,
			byte[] image) throws Exception {

		// FileInputStream fis = new FileInputStream(image);
		Connection con = MySQLDriverManager.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;

		ImageUtil imageUtil = new ImageUtil();
		byte[] smallImage = null;
		byte[] mediumImage = null;
		try {
			smallImage = imageUtil.resizeImageAsJPG(image, 80);
			mediumImage = imageUtil.resizeImageAsJPG(image, 520);
		} catch (Exception e) {
			Logger.error("An upload try with no image select", e);
			return;
		}

		String query = "INSERT INTO `IMAGES` (PRODUCT,CATEGORY,COMPANY,IMAGE_ORIG,IMAGE_SMALL) VALUES (?,?,?,?,?)";

		try {

			try {
				st = con.prepareStatement(query);
				st.setString(1, product);
				st.setString(2, category);
				st.setString(3, company);
				st.setBytes(4, mediumImage);
				st.setBytes(5, smallImage);
				st.execute();
			} catch (Exception e) {
				con = MySQLDriverManager.getConnectionAgain();
				st = con.prepareStatement(query);
				st.setString(1, product);
				st.setString(2, category);
				st.setString(3, company);
				st.setBytes(4, mediumImage);
				st.setBytes(5, smallImage);
				st.execute();
			}

			// con.commit();

		} catch (Exception e) {
			Logger.error("Exception: " + e.getMessage());
			e.printStackTrace();
		} finally {
			doFinally(st, rs);
		}

	}

	public byte[] getSmallImage(String product, String company, String category)
			throws Exception {

		Connection con = MySQLDriverManager.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;

		String query = "SELECT IMAGE_SMALL FROM `IMAGES` WHERE PRODUCT = ? AND CATEGORY= ? AND COMPANY = ?";

		try {

			try {
				st = con.prepareStatement(query);
				st.setString(1, product);
				st.setString(2, category);
				st.setString(3, company);
				rs = st.executeQuery();
			} catch (Exception e) {
				con = MySQLDriverManager.getConnectionAgain();
				st = con.prepareStatement(query);
				st.setString(1, product);
				st.setString(2, category);
				st.setString(3, company);
				rs = st.executeQuery();

			}

			byte[] is = null;
			while (rs.next()) {

				is = (byte[]) rs.getBytes(1);

			}

			if (is == null) {
				return null;
			} else {
				return is;
			}

			// con.commit();

		} catch (Exception e) {
			Logger.error("Exception: " + e.getMessage());
			e.printStackTrace();
		} finally {
			doFinally(st, rs);
		}
		return null;
	}

	public byte[] getOrigImage(String product, String company, String category)
			throws Exception {
		Connection con = MySQLDriverManager.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;

		String query = "SELECT IMAGE_ORIG FROM `IMAGES` WHERE PRODUCT = ? AND CATEGORY= ? AND COMPANY = ?";

		try {

			try {
				st = con.prepareStatement(query);
				st.setString(1, product);
				st.setString(2, category);
				st.setString(3, company);
				rs = st.executeQuery();
			} catch (Exception e) {
				con = MySQLDriverManager.getConnectionAgain();
				st = con.prepareStatement(query);
				st.setString(1, product);
				st.setString(2, category);
				st.setString(3, company);
				rs = st.executeQuery();

			}

			byte[] is = null;
			while (rs.next()) {

				is = (byte[]) rs.getBytes(1);

			}

			if (is == null) {
				return null;
			} else {
				return is;
			}

			// con.commit();

		} catch (Exception e) {
			Logger.error("Exception: " + e.getMessage());
			e.printStackTrace();
		} finally {
			doFinally(st, rs);
		}
		return null;
	}

	public void updateImage(String product, String company, String category,
			byte[] image) throws Exception {

		// FileInputStream fis = new FileInputStream(image);
		Connection con = MySQLDriverManager.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;

		ImageUtil imageUtil = new ImageUtil();
		byte[] smallImage = null;
		byte[] mediumImage = null;
		try {
			smallImage = imageUtil.resizeImageAsJPG(image, 80);
			mediumImage = imageUtil.resizeImageAsJPG(image, 520);
		} catch (Exception e) {
			Logger.error("An upload try with no image select", e);
			return;
		}

		String query = "UPDATE `IMAGES` SET IMAGE_ORIG=?, IMAGE_SMALL=? WHERE PRODUCT = ? AND CATEGORY= ? AND COMPANY = ?";

		try {

			try {
				st = con.prepareStatement(query);
				st.setBytes(1, mediumImage);
				st.setBytes(2, smallImage);
				st.setString(3, product);
				st.setString(4, category);
				st.setString(5, company);

				st.executeUpdate();
			} catch (Exception e) {
				con = MySQLDriverManager.getConnectionAgain();
				st = con.prepareStatement(query);
				st.setBytes(1, mediumImage);
				st.setBytes(2, smallImage);
				st.setString(3, product);
				st.setString(4, category);
				st.setString(5, company);
				st.executeUpdate();
			}

			// con.commit();

		} catch (Exception e) {
			Logger.error("Exception: " + e.getMessage());
			e.printStackTrace();
		} finally {
			doFinally(st, rs);
		}

	}

	public void deleteImage(String product, String company, String category)
			throws Exception {

		Connection con = MySQLDriverManager.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;

		String query = "DELETE FROM `IMAGES` WHERE PRODUCT = ? AND CATEGORY= ? AND COMPANY = ?";

		try {

			try {
				st = con.prepareStatement(query);
				st.setString(1, product);
				st.setString(2, category);
				st.setString(3, company);
				st.execute();
			} catch (Exception e) {
				con = MySQLDriverManager.getConnectionAgain();
				st = con.prepareStatement(query);
				st.setString(1, product);
				st.setString(2, category);
				st.setString(3, company);
				st.execute();
			}

			// con.commit();

		} catch (Exception e) {
			Logger.error("Exception: " + e.getMessage());
			e.printStackTrace();
		} finally {
			doFinally(st, rs);
		}

	}

	public void deleteJob(String jobName) throws Exception {
		Connection con = MySQLDriverManager.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		String query = "DELETE FROM `JOBS` WHERE NAME = ?";

		try {

			try {
				st = con.prepareStatement(query);
				st.setString(1, jobName);
				st.execute();
			} catch (Exception e) {
				con = MySQLDriverManager.getConnectionAgain();
				st = con.prepareStatement(query);
				st.setString(1, jobName);
				st.execute();
			}

			// con.commit();

		} catch (Exception e) {
			Logger.error("Exception: " + e.getMessage());
			e.printStackTrace();
		} finally {
			doFinally(st, rs);
		}

	}

	public void updateJob(JobDetails details) throws Exception {

		Connection con = MySQLDriverManager.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		String query = "UPDATE `JOBS` SET DAY_OF_WEEK=?, DAY_OF_MONTH=?, JOB_MONTH=?, JOB_HOUR=?, JOB_MINUTE=?, JOB_REPEAT=?, TYPE_ARG_1=?, TYPE_ARG_2=?, TYPE_ARG_3=?, TYPE_ARG_4=?, ACTIVE=? WHERE NAME = ?";
		try {

			try {
				st = con.prepareStatement(query);

				st.setString(1, details.getDAY_OF_WEEK());
				st.setString(2, details.getDAY_OF_MONTH());
				st.setString(3, details.getMONTH());
				st.setString(4, details.getHOUR());
				st.setString(5, details.getMINUTE());
				st.setString(6, details.getREPEAT());
				st.setString(7, details.getTYPE_ARG_1());
				st.setString(8, details.getTYPE_ARG_2());
				st.setString(9, details.getTYPE_ARG_3());
				st.setString(10, details.getTYPE_ARG_4());
				st.setString(11, details.getACTIVE());
				st.setString(12, details.getNAME());

				st.executeUpdate();
			} catch (Exception e) {
				con = MySQLDriverManager.getConnectionAgain();
				st = con.prepareStatement(query);
				st.setString(1, details.getDAY_OF_WEEK());
				st.setString(2, details.getDAY_OF_MONTH());
				st.setString(3, details.getMONTH());
				st.setString(4, details.getHOUR());
				st.setString(5, details.getMINUTE());
				st.setString(6, details.getREPEAT());
				st.setString(7, details.getTYPE_ARG_1());
				st.setString(8, details.getTYPE_ARG_2());
				st.setString(9, details.getTYPE_ARG_3());
				st.setString(10, details.getTYPE_ARG_4());
				st.setString(11, details.getACTIVE());
				st.setString(12, details.getNAME());
				st.executeUpdate();
			}

			// con.commit();

		} catch (Exception e) {
			Logger.error("Exception: " + e.getMessage());
			e.printStackTrace();
		} finally {
			doFinally(st, rs);
		}
	}

	public void addJob(JobDetails details) throws Exception {

		Connection con = MySQLDriverManager.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;

		String query = "INSERT INTO `JOBS` (NAME,JOB_TEXT, DAY_OF_WEEK, DAY_OF_MONTH,JOB_MONTH,JOB_HOUR,JOB_MINUTE,JOB_REPEAT,JOB_TYPE, DETAILS, TYPE_ARG_1, TYPE_ARG_2, TYPE_ARG_3, TYPE_ARG_4, ACTIVE) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		try {

			try {
				st = con.prepareStatement(query);

				st.setString(1, details.getNAME());
				st.setString(2, details.getTEXT());
				st.setString(3, details.getDAY_OF_WEEK());
				st.setString(4, details.getDAY_OF_MONTH());
				st.setString(5, details.getMONTH());
				st.setString(6, details.getHOUR());
				st.setString(7, details.getMINUTE());
				st.setString(8, details.getREPEAT());
				st.setString(9, details.getTYPE());
				st.setString(10, details.getDETAILS());
				st.setString(11, details.getTYPE_ARG_1());
				st.setString(12, details.getTYPE_ARG_2());
				st.setString(13, details.getTYPE_ARG_3());
				st.setString(14, details.getTYPE_ARG_4());
				st.setString(15, details.getACTIVE());

				st.execute();
			} catch (Exception e) {
				con = MySQLDriverManager.getConnectionAgain();
				st = con.prepareStatement(query);
				st.setString(1, details.getNAME());
				st.setString(2, details.getTEXT());
				st.setString(3, details.getDAY_OF_WEEK());
				st.setString(4, details.getDAY_OF_MONTH());
				st.setString(5, details.getMONTH());
				st.setString(6, details.getHOUR());
				st.setString(7, details.getMINUTE());
				st.setString(8, details.getREPEAT());
				st.setString(9, details.getTYPE());
				st.setString(10, details.getDETAILS());
				st.setString(11, details.getTYPE_ARG_1());
				st.setString(12, details.getTYPE_ARG_2());
				st.setString(13, details.getTYPE_ARG_3());
				st.setString(14, details.getTYPE_ARG_4());
				st.setString(15, details.getACTIVE());
				st.execute();
			}

			// con.commit();

		} catch (Exception e) {
			Logger.error("Exception: " + e.getMessage());
			e.printStackTrace();
		} finally {
			doFinally(st, rs);
		}
	}

	public Set<JobDetails> getJobs() throws Exception {
		Connection con = MySQLDriverManager.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		JobDetails jobDetail = null;
		Set<JobDetails> output = new LinkedHashSet<JobDetails>();

		String userDetailsquery = "SELECT  NAME,JOB_TEXT, DAY_OF_WEEK, DAY_OF_MONTH,JOB_MONTH,JOB_HOUR,JOB_MINUTE,JOB_REPEAT,JOB_TYPE, DETAILS, TYPE_ARG_1, TYPE_ARG_2, TYPE_ARG_3, TYPE_ARG_4, ACTIVE FROM JOBS order by NAME";

		try {

			try {
				st = con.prepareStatement(userDetailsquery);
				rs = st.executeQuery();

			} catch (Exception e) {
				con = MySQLDriverManager.getConnectionAgain();
				st = con.prepareStatement(userDetailsquery);
				rs = st.executeQuery();
			}
			while (rs.next()) {
				jobDetail = new JobDetails();
				jobDetail.setNAME(rs.getString(1));
				jobDetail.setTEXT(rs.getString(2));
				jobDetail.setDAY_OF_WEEK(rs.getString(3));
				jobDetail.setDAY_OF_MONTH(rs.getString(4));
				jobDetail.setMONTH(rs.getString(5));
				jobDetail.setHOUR(rs.getString(6));
				jobDetail.setMINUTE(rs.getString(7));
				jobDetail.setREPEAT(rs.getString(8));
				jobDetail.setTYPE(rs.getString(9));
				jobDetail.setDETAILS(rs.getString(10));
				jobDetail.setTYPE_ARG_1(rs.getString(11));
				jobDetail.setTYPE_ARG_2(rs.getString(12));
				jobDetail.setTYPE_ARG_3(rs.getString(13));
				jobDetail.setTYPE_ARG_4(rs.getString(14));
				jobDetail.setACTIVE(rs.getString(15));

				output.add(jobDetail);
			}

		} catch (Exception e) {
			Logger.print("Exception: " + e.getMessage());
			throw e;
		} finally {
			doFinally(st, rs);
		}

		return output;
	}

	public JobDetails getJob(String jobName) throws Exception {
		Connection con = MySQLDriverManager.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		JobDetails jobDetail = null;

		String userDetailsquery = "SELECT  NAME,JOB_TEXT, DAY_OF_WEEK, DAY_OF_MONTH,JOB_MONTH,JOB_HOUR,JOB_MINUTE,JOB_REPEAT,JOB_TYPE, DETAILS, TYPE_ARG_1, TYPE_ARG_2, TYPE_ARG_3, TYPE_ARG_4, ACTIVE from JOBS where NAME = ?";

		try {

			try {
				st = con.prepareStatement(userDetailsquery);
				st.setString(1, jobName);
				rs = st.executeQuery();

			} catch (Exception e) {
				con = MySQLDriverManager.getConnectionAgain();
				st = con.prepareStatement(userDetailsquery);
				st.setString(1, jobName);
				rs = st.executeQuery();
			}
			while (rs.next()) {
				jobDetail = new JobDetails();
				jobDetail.setNAME(rs.getString(1));
				jobDetail.setTEXT(rs.getString(2));
				jobDetail.setDAY_OF_WEEK(rs.getString(3));
				jobDetail.setDAY_OF_MONTH(rs.getString(4));
				jobDetail.setMONTH(rs.getString(5));
				jobDetail.setHOUR(rs.getString(6));
				jobDetail.setMINUTE(rs.getString(7));
				jobDetail.setREPEAT(rs.getString(8));
				jobDetail.setTYPE(rs.getString(9));
				jobDetail.setDETAILS(rs.getString(10));
				jobDetail.setTYPE_ARG_1(rs.getString(11));
				jobDetail.setTYPE_ARG_2(rs.getString(12));
				jobDetail.setTYPE_ARG_3(rs.getString(13));
				jobDetail.setTYPE_ARG_4(rs.getString(14));
				jobDetail.setACTIVE(rs.getString(15));

			}

		} catch (Exception e) {
			Logger.print("Exception: " + e.getMessage());
			e.printStackTrace();
		} finally {
			doFinally(st, rs);
		}

		return jobDetail;
	}

	public Set<GFUser> getAllMembersExpirationsWhoRegisterToGFGuide()
			throws Exception {

		Connection con = MySQLDriverManager.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		GFUser gfUser = null;
		Set<GFUser> output = new LinkedHashSet<GFUser>();

		String userDetailsquery = "SELECT   us.CELIAC_MEMBER_ID,us.DID_BUY_THE_BOOK, us.USER_TZ, DATE_FORMAT(us.EXPIRATION_DATE, '%Y-%m-%d'), ud.FIRST_NAME, ud.LAST_NAME, ud.EMAIL, DATE_FORMAT(ud.EFFECTIVE_DATE, '%Y-%m-%d') FROM USERS us, USER_DETAILS ud where us.CELIAC_MEMBER_ID  = ud.CELIAC_MEMBER_ID";

		try {

			try {
				st = con.prepareStatement(userDetailsquery);
				rs = st.executeQuery();

			} catch (Exception e) {
				con = MySQLDriverManager.getConnectionAgain();
				st = con.prepareStatement(userDetailsquery);
				rs = st.executeQuery();
			}
			while (rs.next()) {
				gfUser = new GFUser();
				gfUser.setEXPIRATION_DATE(UsersTable.MySqlStringToDate(rs
						.getString(4)));
				gfUser.setCELIAC_MEMBER_ID(rs.getString(1));
				gfUser.setUSER_TZ(rs.getString(3));
				gfUser.setFIRST_NAME(rs.getString(5));
				gfUser.setLAST_NAME(rs.getString(6));
				gfUser.setEMAIL(rs.getString(7));
				gfUser.setEFFECTIVE_DATE(UsersTable.MySqlStringToDate(rs
						.getString(8)));
				output.add(gfUser);
			}

		} catch (Exception e) {
			Logger.print("Exception: " + e.getMessage());
			e.printStackTrace();
		} finally {
			doFinally(st, rs);
		}

		return output;

	}

	public Set<DiffData> getDiff(String query, String operation)
			throws Exception {
		Connection con = MySQLDriverManager.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		DiffData diffData = null;
		Set<DiffData> output = new LinkedHashSet<DiffData>();

		try {

			try {
				st = con.prepareStatement(query);
				rs = st.executeQuery();

			} catch (Exception e) {
				con = MySQLDriverManager.getConnectionAgain();
				st = con.prepareStatement(query);
				rs = st.executeQuery();
			}
			while (rs.next()) {
				diffData = new DiffData();
				diffData.setProduct(rs.getString(1));
				diffData.setCompany(rs.getString(2));
				diffData.setCategory(rs.getString(3));
				diffData.setGlutenFree(rs.getString(4));
				if ("CHANGE".equals(operation))
					diffData.setGlutenFreeOld(rs.getString(5));
				diffData.setOperation(operation);
				output.add(diffData);
			}

		} catch (Exception e) {
			Logger.print("Exception: " + e.getMessage());
			throw e;
		} finally {
			doFinally(st, rs);
		}

		return output;
	}

	public Set<DiffData> getDiffByDates(Date fromDate, String numberOfdays,
			String numberOfMonths, String numberOfYears) throws Exception {
		Connection con = MySQLDriverManager.getConnection();
		PreparedStatement st = null;
		ResultSet rs = null;
		DiffData diffData = null;
		Set<DiffData> output = new LinkedHashSet<DiffData>();
		Date end = new Date();
		Date start = new Date();
		String query = "select PRODUCT,CATEGORY,COMPANY,GLUTEN_FREE,GLUTEN_FREE_OLD,OPERATION,DATE from PRODUCT_DIFF where DATE between ? AND ? ";

		Calendar cal = Calendar.getInstance();

		end = new Date();
		cal.setTime(end);
		if (numberOfdays != null) {
			int days = new Integer(numberOfdays).intValue();
			cal.add(Calendar.DAY_OF_MONTH, days);
			start = cal.getTime();
			//start = new Date(end.getTime() - 1000 * 60 * 60 * 24 * new Integer(numberOfdays).intValue());
		} else if (numberOfMonths != null) {
			int months = new Integer(numberOfMonths).intValue();
			cal.add(Calendar.MONTH, months);
			start = cal.getTime();
		} else if (numberOfYears != null) {
			int years = new Integer(numberOfYears).intValue();
			cal.add(Calendar.YEAR, years);
			start = cal.getTime();
		} else if (fromDate != null) {
			start = fromDate;
		} else
			throw new Exception(
					"No value was set for starting date for diff query");

		
		try {

			try {
				st = con.prepareStatement(query);
				st.setDate(1, new java.sql.Date(start.getTime()));
				st.setDate(2, new java.sql.Date(end.getTime()));
				rs = st.executeQuery();

			} catch (Exception e) {
				con = MySQLDriverManager.getConnectionAgain();
				st = con.prepareStatement(query);
				st.setDate(1, new java.sql.Date(start.getTime()));
				st.setDate(2, new java.sql.Date(end.getTime()));
				rs = st.executeQuery();
			}
			while (rs.next()) {
				diffData = new DiffData();
				diffData.setProduct(rs.getString(1));
				diffData.setCategory(rs.getString(2));
				diffData.setCompany(rs.getString(3));
				diffData.setGlutenFree(rs.getString(4));
				diffData.setGlutenFreeOld(rs.getString(5));
				diffData.setOperation(rs.getString(6));
				diffData.setDate(rs.getDate(7));

				output.add(diffData);
			}

		} catch (Exception e) {
			Logger.print("Exception: " + e.getMessage());
			throw e;
		} finally {
			doFinally(st, rs);
		}

		return output;
	}

	public void updateDiff(Set<DiffData> gfUser) throws Exception {

		String query = "INSERT INTO `PRODUCT_DIFF` (PRODUCT,CATEGORY,COMPANY,GLUTEN_FREE,GLUTEN_FREE_OLD,OPERATION,DATE) VALUES (?, ?, ?, ?, ?, ?, ?)";

		Connection con = MySQLDriverManager.getConnection();
		con.setAutoCommit(false);
		PreparedStatement st = con.prepareStatement(query);

		Iterator<DiffData> iter = gfUser.iterator();
		DiffData tempDiffData = null;

		try {
			while (iter.hasNext()) {

				tempDiffData = iter.next();

				st.setString(1, tempDiffData.getProduct());
				st.setString(2, tempDiffData.getCategory());
				st.setString(3, tempDiffData.getCompany());
				st.setString(4, tempDiffData.getGlutenFree());
				st.setString(5, tempDiffData.getGlutenFreeOld());
				st.setString(6, tempDiffData.getOperation());
				st.setDate(7, new java.sql.Date(new Date().getTime()));

				st.addBatch();

			}

			int[] updateCounts = st.executeBatch();
			checkUpdateCounts(updateCounts);
			con.commit();

		} catch (Exception e) {
			Logger.print("Exception: " + e.getMessage());
			e.printStackTrace();
		} finally {
			con.setAutoCommit(true);
			doFinally(st, null);
		}

	}

	public static void checkUpdateCounts(int[] updateCounts) {
		for (int i = 0; i < updateCounts.length; i++) {
			if (updateCounts[i] >= 0) {
				System.out.println("Successfully executed; updateCount="
						+ updateCounts[i]);
			} else if (updateCounts[i] == Statement.SUCCESS_NO_INFO) {
				System.out
						.println("Successfully executed; updateCount=Statement.SUCCESS_NO_INFO");
			} else if (updateCounts[i] == Statement.EXECUTE_FAILED) {
				System.out
						.println("Failed to execute; updateCount=Statement.EXECUTE_FAILED");
			}
		}
	}

}
