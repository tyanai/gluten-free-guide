package org.celiac.datatype;

import java.util.Date;

public class GFUser implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1949253646572763666L;
	private String CELL_NUM = null;
	private String FIRST_NAME = null;
	private String LAST_NAME = null;
	private Date EFFECTIVE_DATE = null;
	private Date EXPIRATION_DATE = null;
	private String USER_ID = null;
	private String PASSWORD = null;
	private String PERMISSIONS = null;
	private Date LAST_LOGIN_DATE = null;
	private String ACKNOWLEDGE_USE = null;
	private String EMAIL = null;
	private String CELIAC_MEMBER_ID = null;
	private String USER_TZ = null;
	private String DID_BUY_THE_BOOK = null;
	

	public String getDID_BUY_THE_BOOK() {
		return DID_BUY_THE_BOOK;
		
	}

	public void setDID_BUY_THE_BOOK(String did_buy_the_book) {
		DID_BUY_THE_BOOK = did_buy_the_book;
	}

	public String getCELL_NUM() {
		return CELL_NUM;
	}

	public void setCELL_NUM(String cell_num) {
		CELL_NUM = cell_num;
	}

	public String getFIRST_NAME() {
		return FIRST_NAME;
	}

	public void setFIRST_NAME(String first_name) {
		FIRST_NAME = first_name;
	}

	public String getLAST_NAME() {
		return LAST_NAME;
	}

	public void setLAST_NAME(String last_name) {
		LAST_NAME = last_name;
	}

	public Date getEFFECTIVE_DATE() {
		return EFFECTIVE_DATE;
	}

	public void setEFFECTIVE_DATE(Date effective_date) {
		EFFECTIVE_DATE = effective_date;
	}

	public Date getEXPIRATION_DATE() {
		return EXPIRATION_DATE;
	}

	public void setEXPIRATION_DATE(Date expiration_date) {
		EXPIRATION_DATE = expiration_date;
	}

	
	public String getUSER_ID() {
		return USER_ID;
	}

	public void setUSER_ID(String user_id) {
		USER_ID = user_id;
	}

	public String getPASSWORD() {
		return PASSWORD;
	}

	public void setPASSWORD(String password) {
		PASSWORD = password;
	}

	public String getPERMISSIONS() {
		return PERMISSIONS;
	}

	public void setPERMISSIONS(String permissions) {
		PERMISSIONS = permissions;
	}

	

	public Date getLAST_LOGIN_DATE() {
		return LAST_LOGIN_DATE;
	}

	public void setLAST_LOGIN_DATE(Date last_login_date) {
		LAST_LOGIN_DATE = last_login_date;
	}

	public String getACKNOWLEDGE_USE() {
		return ACKNOWLEDGE_USE;
	}

	public void setACKNOWLEDGE_USE(String acknowledge_use) {
		ACKNOWLEDGE_USE = acknowledge_use;
	}

	public String getEMAIL() {
		return EMAIL;
	}

	public void setEMAIL(String email) {
		EMAIL = email;
	}

	public String getCELIAC_MEMBER_ID() {
		return CELIAC_MEMBER_ID;
	}

	public void setCELIAC_MEMBER_ID(String celiac_member_id) {
		CELIAC_MEMBER_ID = celiac_member_id;
	}

	public String getUSER_TZ() {
		return USER_TZ;
	}

	public void setUSER_TZ(String user_tz) {
		USER_TZ = user_tz;
	}
}
