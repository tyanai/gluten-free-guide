package org.celiac.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.celiac.datatype.CodeDesc;
import org.celiac.datatype.CompanyTable;
import org.celiac.datatype.GFUser;

public class DBWriter {

	public void createCompanyTable(String tableName, CompanyTable companyTable) throws Exception {

		createCompanyTable(tableName);
		insertCompanyTable(tableName, companyTable);
	}
	
	

	private void insertCompanyTable(String tableName, CompanyTable companyTable) throws Exception {
		Logger.print("Inserting data into table '" + tableName + "'");
		Connection connection = MySQLDriverManager.getConnection();
		String schemaName = PropertiesLoader.getProperties("db.scheme");

		Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		connection.setAutoCommit(false);

		for (int j = 0; j < companyTable.getCATAGORY().length; j++) {
			if (companyTable.getCATAGORY()[j].trim().equals(""))
				continue;
			String query = "INSERT INTO `" + schemaName + "`.`" + tableName + "` (CATEGORY,PRODUCT,GLUTEN_FREE) VALUES('"
					+ companyTable.getCATAGORY()[j] + "','" + companyTable.getPRODUCT()[j] + "','"
					+ companyTable.getGLUTEN_FREE()[j] + "')";

			try {
				// Create a PreparedStatement based on the query in query
				stmt.addBatch(query);

			} catch (Exception e) {
				e.printStackTrace();

			}
		}

		stmt.executeBatch();
		connection.commit();
		connection.setAutoCommit(true);

	}

	public void insertUsersTable(GFUser[] usersTable, String tableName) throws Exception {
		Logger.print("Inserting data into table '" + tableName + "'");
		Connection connection = MySQLDriverManager.getConnection();
		String schemaName = PropertiesLoader.getProperties("db.scheme");

		Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		connection.setAutoCommit(false);
		int j=0;
		try{
		
		for (; j < usersTable.length; j++) {
			if (usersTable[j] == null) continue;
			if (usersTable[j].getCELIAC_MEMBER_ID() == null) continue;
			if (usersTable[j].getUSER_TZ() == null) continue;
			if (usersTable[j].getDID_BUY_THE_BOOK() == null) continue;
			if (usersTable[j].getEXPIRATION_DATE() == null) continue;
			if (usersTable[j].getCELIAC_MEMBER_ID().trim().equals("") || usersTable[j].getUSER_TZ().trim().equals("")) continue;
			
			
			
			String query = "INSERT INTO `" + schemaName
					+ "`.`" + tableName + "` (CELIAC_MEMBER_ID,USER_TZ,DID_BUY_THE_BOOK,EXPIRATION_DATE) VALUES('"
					+ usersTable[j].getCELIAC_MEMBER_ID() + "','" + usersTable[j].getUSER_TZ() + "','" + usersTable[j].getDID_BUY_THE_BOOK()
				    + "','" + new java.sql.Date(usersTable[j].getEXPIRATION_DATE().getTime()) + "')";

			
			try {
				// Create a PreparedStatement based on the query in query
				stmt.addBatch(query);

			} catch (Exception e) {
				e.printStackTrace();

			}
		}
		}catch  (Exception e){
			e.printStackTrace();
			Logger.error("Failed to read users data in line "+j, e);
			throw new Exception("Failed to read users data in line" + j);
		}

		stmt.executeBatch();
		connection.commit();
		connection.setAutoCommit(true);
		Logger.print("Inserting data into table 'USERS' - Done.");

	}
	
	public void insertCodeDescTable(CodeDesc[] codeDesc, String tableName) throws Exception {
		Logger.print("Inserting data into table '" + tableName + "'");
		Connection connection = MySQLDriverManager.getConnection();
		String schemaName = PropertiesLoader.getProperties("db.scheme");

		Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		connection.setAutoCommit(false);
		int j=0;
		try{
		
		for (; j < codeDesc.length; j++) {
			System.out.println(""+j);
			if ((codeDesc[j] == null) && (codeDesc[j] == null))  continue;
			if ((codeDesc[j].getCODE() == null) && (codeDesc[j].getDESC() != null))  throw new Exception("Found line without description. Line: " + j);
			if ((codeDesc[j].getCODE() != null) && (codeDesc[j].getDESC() == null))  throw new Exception("Found line without code. Line: " + j);
			if ((codeDesc[j].getCODE() == null) && (codeDesc[j].getDESC() == null))  continue;
			if ((codeDesc[j].getCODE().trim().equals("") && codeDesc[j].getCODE().trim().equals(""))) continue;
			
			
			
			String query = "INSERT INTO `" + schemaName
					+ "`.`" + tableName + "` (CODE,DESCRIPTION) VALUES('"
					+ codeDesc[j].getCODE() + "','" + codeDesc[j].getDESC() + "')";

			
			try {
				// Create a PreparedStatement based on the query in query
				stmt.addBatch(query);

			} catch (Exception e) {
				e.printStackTrace();

			}
		}
		}catch  (Exception e){
			e.printStackTrace();
			Logger.error("Failed to read code description data in line "+j, e);
			throw new Exception("Failed to read code description data in line" + j);
		}

		stmt.executeBatch();
		connection.commit();
		connection.setAutoCommit(true);
		Logger.print("Inserting data into table '"+  tableName + "' - Done.");

	}

	private void createCompanyTable(String tableName) throws Exception {

		Logger.print("Creating table '" + tableName + "'");
		Connection connection = MySQLDriverManager.getConnection();
		String schemaName = PropertiesLoader.getProperties("db.scheme");

		// Hebrew tables
		String query = "CREATE TABLE `"
				+ schemaName
				+ "`.`"
				+ tableName
				+ "` (`CATEGORY` VARCHAR(100) CHARACTER SET hebrew COLLATE hebrew_general_ci NOT NULL,`PRODUCT` VARCHAR(100) CHARACTER SET hebrew COLLATE hebrew_general_ci NOT NULL,`GLUTEN_FREE` VARCHAR(100) CHARACTER SET hebrew COLLATE hebrew_general_ci NOT NULL,INDEX `CATEGORY`(`CATEGORY`)) ENGINE = InnoDB;";

		try {
			// Create a PreparedStatement based on the query in query
			PreparedStatement pst = connection.prepareStatement(query);
			pst.execute();
			pst.close();

		} catch (Exception e) {
			e.printStackTrace();

		}

	}
	
	
	
	public void createStatisticsTable() throws Exception {

		Logger.print("Creating table 'STATISTICS'");
		Connection connection = MySQLDriverManager.getConnection();
		String schemaName = PropertiesLoader.getProperties("db.scheme");

		String query = "CREATE TABLE `" + schemaName + "`.`STATISTICS` ("
			  + " `DATE` DATETIME NOT NULL,"
			  + " `RETURNED_1` INTEGER UNSIGNED NOT NULL,"
			  + " `RETURNED_2` INTEGER UNSIGNED NOT NULL,"
			  + " `RETURNED_3` INTEGER UNSIGNED NOT NULL,"
			  + " `RETURNED_4` INTEGER UNSIGNED NOT NULL,"
			  + " `RETURNED_5` INTEGER UNSIGNED NOT NULL,"
			  + " `WEB_USER` INTEGER UNSIGNED NOT NULL,"
			  + " PRIMARY KEY (`DATE`)"
			  + " )"
			  + " ENGINE = InnoDB;";

		try {
			// Create a PreparedStatement based on the query in query
			PreparedStatement pst = connection.prepareStatement(query);
			pst.execute();
			pst.close();

		} catch (Exception e) {
			e.printStackTrace();

		}

	}
	
	

	public void createProductIndexTable(String theTableName) throws Exception {

		Logger.print("Creating table '" + theTableName + "'");
		Connection connection = MySQLDriverManager.getConnection();
		String schemaName = PropertiesLoader.getProperties("db.scheme");

		/*
		 * // Hebrew tables String query = "CREATE TABLE `" + schemaName +
		 * "`.`PRODUCT_INDEX` (`PRODUCT` VARCHAR(100) CHARACTER SET hebrew
		 * COLLATE hebrew_general_ci NOT NULL," + "`CATEGORY` VARCHAR(100)
		 * CHARACTER SET hebrew COLLATE hebrew_general_ci NOT NULL," +
		 * "`COMPANY` VARCHAR(100) CHARACTER SET hebrew COLLATE
		 * hebrew_general_ci NOT NULL," + "`GLUTEN_FREE` VARCHAR(100) CHARACTER
		 * SET hebrew COLLATE hebrew_general_ci NOT NULL," + "INDEX
		 * `PRODUCT`(`PRODUCT`, `CATEGORY`, `COMPANY` )) ENGINE = InnoDB;";
		 */
		String query = "CREATE TABLE `" + schemaName
				+ "`.`" +  theTableName + "` (`PRODUCT` VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,"
				+ "`CATEGORY` VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,"
				+ "`COMPANY` VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,"
				+ "`GLUTEN_FREE` VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,"
				+ "INDEX `PRODUCT`(`PRODUCT`, `CATEGORY`, `COMPANY` )) ENGINE = InnoDB;";

		try {
			// Create a PreparedStatement based on the query in query
			PreparedStatement pst = connection.prepareStatement(query);
			pst.execute();
			pst.close();

		} catch (Exception e) {
			e.printStackTrace();

		}
		
		//Treat the product_index_dates table
		try{
				//First drop the dates table
				Logger.print("Dropping table '" + theTableName +"_DATES'");
				query = "DROP TABLE `" + schemaName + "`.`" + theTableName +"_DATES";
				try {
					// Create a PreparedStatement based on the query in query
					PreparedStatement pst = connection.prepareStatement(query);
					pst.execute();
					pst.close();

				} catch (Exception e) {
					Logger.print("ERROR - Failed to dump table '" + theTableName +"_DATES'");
					e.printStackTrace();

				}
				
				//Next build it from begining:
				query = "CREATE TABLE `" + schemaName + "`.`" + theTableName + "_DATES` (`COMPANY` VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci,"
				+ "`DATE` VARCHAR(10) CHARACTER SET utf8 COLLATE utf8_general_ci,"
				+ " PRIMARY KEY (`COMPANY`)) ENGINE = InnoDB;";
				
				try {
					// Create a PreparedStatement based on the query in query
					PreparedStatement pst = connection.prepareStatement(query);
					pst.execute();
					pst.close();

				} catch (Exception e) {
					Logger.print("ERROR - Failed to create table '" + theTableName +"_DATES'");
					e.printStackTrace();

				}
				
		}catch (Exception e){
			Logger.error("Error creating table '" + theTableName +"_DATES'", e);
		}

	}
	
	public void createDiffTable(String theTableName) throws Exception {

		Logger.print("Creating table '" + theTableName + "'");
		Connection connection = MySQLDriverManager.getConnection();
		String schemaName = PropertiesLoader.getProperties("db.scheme");

		/*
		 * // Hebrew tables String query = "CREATE TABLE `" + schemaName +
		 * "`.`PRODUCT_INDEX` (`PRODUCT` VARCHAR(100) CHARACTER SET hebrew
		 * COLLATE hebrew_general_ci NOT NULL," + "`CATEGORY` VARCHAR(100)
		 * CHARACTER SET hebrew COLLATE hebrew_general_ci NOT NULL," +
		 * "`COMPANY` VARCHAR(100) CHARACTER SET hebrew COLLATE
		 * hebrew_general_ci NOT NULL," + "`GLUTEN_FREE` VARCHAR(100) CHARACTER
		 * SET hebrew COLLATE hebrew_general_ci NOT NULL," + "INDEX
		 * `PRODUCT`(`PRODUCT`, `CATEGORY`, `COMPANY` )) ENGINE = InnoDB;";
		 */
		String query = "CREATE TABLE `" + schemaName
				+ "`.`" +  theTableName + "` (`PRODUCT` VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,"
				+ "`CATEGORY` VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,"
				+ "`COMPANY` VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,"
				+ "`GLUTEN_FREE` VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,"
				+ "`GLUTEN_FREE_OLD` VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,"
				+ "`OPERATION` VARCHAR(25) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,"
				+ "`DATE` DATETIME NOT NULL,"
				+ "INDEX `PRODUCT`(`PRODUCT`, `CATEGORY`, `COMPANY`, `DATE` )) ENGINE = InnoDB;";

		try {
			// Create a PreparedStatement based on the query in query
			PreparedStatement pst = connection.prepareStatement(query);
			pst.execute();
			pst.close();

		} catch (Exception e) {
			e.printStackTrace();

		}
		
		

	}
	
	public void createProductImageTable(String theTableName) throws Exception {

		//Logger.print("Creating table '" + theTableName + "'");
		System.out.println("Creating table '" + theTableName + "'");
		Connection connection = MySQLDriverManager.getConnection();
		String schemaName = PropertiesLoader.getProperties("db.scheme");

		/*
		 * // Hebrew tables String query = "CREATE TABLE `" + schemaName +
		 * "`.`PRODUCT_INDEX` (`PRODUCT` VARCHAR(100) CHARACTER SET hebrew
		 * COLLATE hebrew_general_ci NOT NULL," + "`CATEGORY` VARCHAR(100)
		 * CHARACTER SET hebrew COLLATE hebrew_general_ci NOT NULL," +
		 * "`COMPANY` VARCHAR(100) CHARACTER SET hebrew COLLATE
		 * hebrew_general_ci NOT NULL," + "`GLUTEN_FREE` VARCHAR(100) CHARACTER
		 * SET hebrew COLLATE hebrew_general_ci NOT NULL," + "INDEX
		 * `PRODUCT`(`PRODUCT`, `CATEGORY`, `COMPANY` )) ENGINE = InnoDB;";
		 */
		String query = "CREATE TABLE `" + schemaName
				+ "`.`" +  theTableName + "` (`PRODUCT` VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,"
				+ "`CATEGORY` VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,"
				+ "`COMPANY` VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,"
				+ "`IMAGE_ORIG` LONGBLOB  DEFAULT NULL,"
				+ "`IMAGE_SMALL` LONGBLOB DEFAULT NULL,"
				+ "INDEX `PRODUCT`(`PRODUCT`, `CATEGORY`, `COMPANY` )) ENGINE = InnoDB;";

		
		
		
		try {
			// Create a PreparedStatement based on the query in query
			PreparedStatement pst = connection.prepareStatement(query);
			pst.execute();
			pst.close();

		} catch (Exception e) {
			e.printStackTrace();

		}
		
		

	}
	
	public void createJobsTable(String theTableName) throws Exception {

		//Logger.print("Creating table '" + theTableName + "'");
		System.out.println("Creating table '" + theTableName + "'");
		Connection connection = MySQLDriverManager.getConnection();
		String schemaName = PropertiesLoader.getProperties("db.scheme");

		
		String query = "CREATE TABLE `" + schemaName
				+ "`.`" +  theTableName + "` ("
				+ "`NAME` VARCHAR(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,"
				+ "`JOB_TEXT` VARCHAR(5000) CHARACTER SET utf8 COLLATE utf8_general_ci,"
				+ "`DAY_OF_WEEK` VARCHAR(45),"
				+ "`DAY_OF_MONTH` VARCHAR(45),"
				+ "`JOB_MONTH` VARCHAR(45),"
				+ "`JOB_HOUR` VARCHAR(45),"
				+ "`JOB_MINUTE` VARCHAR(45),"
				+ "`JOB_REPEAT` VARCHAR(45),"
				+ "`JOB_TYPE` VARCHAR(45) NOT NULL,"
				+ "`DETAILS` VARCHAR(200) CHARACTER SET utf8 COLLATE utf8_general_ci,"
				+ "`TYPE_ARG_1` VARCHAR(1000),"
				+ "`TYPE_ARG_2` VARCHAR(1000),"
				+ "`TYPE_ARG_3` VARCHAR(1000),"
				+ "`TYPE_ARG_4` VARCHAR(1000),"
				+ "`ACTIVE` VARCHAR(45) NOT NULL,"
				+ "PRIMARY KEY (`NAME`)"
				+ ") ENGINE = InnoDB;";

		
		
		
		try {
			// Create a PreparedStatement based on the query in query
			PreparedStatement pst = connection.prepareStatement(query);
			pst.execute();
			pst.close();

		} catch (Exception e) {
			e.printStackTrace();

		}
		
		

	}
		

	public void createUsersTable(String theTableName) throws Exception {
		createUserDetailsTable();
		Logger.print("Creating table '" + theTableName + "'");
		Connection connection = MySQLDriverManager.getConnection();
		String schemaName = PropertiesLoader.getProperties("db.scheme");

		String query = "CREATE TABLE `" + schemaName + "`.`" + theTableName + "` (`CELIAC_MEMBER_ID` VARCHAR(30) CHARACTER SET utf8 COLLATE utf8_general_ci,"
				+ "`USER_TZ` VARCHAR(30) CHARACTER SET utf8 COLLATE utf8_general_ci,"
				+ "`DID_BUY_THE_BOOK` VARCHAR(15) CHARACTER SET utf8 COLLATE utf8_general_ci,"	
				+ "`EXPIRATION_DATE` DATETIME ,"
				+ " PRIMARY KEY (`CELIAC_MEMBER_ID`,`USER_TZ`)) ENGINE = InnoDB;";

		try {
			// Create a PreparedStatement based on the query in query
			PreparedStatement pst = connection.prepareStatement(query);
			pst.execute();
			pst.close();

		} catch (Exception e) {
			e.printStackTrace();

		}

	}
	
	public void createUserDetailsTable() throws Exception {

		Logger.print("Creating table 'USER_DETAILS'");
		Connection connection = MySQLDriverManager.getConnection();
		String schemaName = PropertiesLoader.getProperties("db.scheme");

		String query = "CREATE TABLE `" + schemaName + "`.`USER_DETAILS` (`CELL_NUM` VARCHAR(30),"
				+ "`USER_ID` VARCHAR(30) CHARACTER SET utf8 COLLATE utf8_general_ci,"
				+ "`FIRST_NAME` VARCHAR(30) CHARACTER SET utf8 COLLATE utf8_general_ci ,"
				+ "`LAST_NAME` VARCHAR(30) CHARACTER SET utf8 COLLATE utf8_general_ci ,"	
				+ "`PASSWORD` VARCHAR(30) CHARACTER SET utf8 COLLATE utf8_general_ci ,"
				+ "`PERMISSIONS` VARCHAR(30) CHARACTER SET utf8 COLLATE utf8_general_ci ,"
				+ "`EFFECTIVE_DATE` DATETIME ," + "`EXPIRATION_DATE` DATETIME ,"
				+ "`LAST_LOGIN_DATE` DATETIME,"
				+ "`ACKNOWLEDGE_USE` VARCHAR(5) CHARACTER SET utf8 COLLATE utf8_general_ci,"		
				+ "`EMAIL` VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_general_ci,"
				+ "`CELIAC_MEMBER_ID` VARCHAR(30) CHARACTER SET utf8 COLLATE utf8_general_ci,"		
				+ " PRIMARY KEY (`USER_ID`,`CELIAC_MEMBER_ID`)) ENGINE = InnoDB;";

		try {
			// Create a PreparedStatement based on the query in query
			PreparedStatement pst = connection.prepareStatement(query);
			pst.execute();
			pst.close();

		} catch (Exception e) {
			e.printStackTrace();

		}

	}
	
	public void createCodeDescTable(String tableName) throws Exception {

		Logger.print("Creating table '"+ tableName + "'");
		Connection connection = MySQLDriverManager.getConnection();
		String schemaName = PropertiesLoader.getProperties("db.scheme");

		String query = "CREATE TABLE `" + schemaName + "`.`"+ tableName +"` (`CODE` VARCHAR(30),"
				+ "`DESCRIPTION` VARCHAR(200) CHARACTER SET utf8 COLLATE utf8_general_ci,"		
				+ " PRIMARY KEY (`CODE`)) ENGINE = InnoDB;";

		try {
			// Create a PreparedStatement based on the query in query
			PreparedStatement pst = connection.prepareStatement(query);
			pst.execute();
			pst.close();

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	public void insertProductIndexTable(Map<String, CompanyTable> db, String theTableName) throws Exception {

		Logger.print("Builing "  + theTableName + " Table:");
		Connection connection = MySQLDriverManager.getConnection();
		String schemaName = PropertiesLoader.getProperties("db.scheme");

		Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		connection.setAutoCommit(false);

		Iterator<String> it = db.keySet().iterator();
		String tableName = null;
		String tableNameWithoutDate = null;
		String companyDate = "";
		CompanyTable companyTable = null;
		String queryDates = null;
		Map<String,String> noDuplicatedCompany = new HashMap<String,String>();

		
		while (it.hasNext()) {
			tableName = it.next();
			companyDate = "";
			tableNameWithoutDate = tableName;
			if( tableName.indexOf("{") > -1  && tableName.indexOf("}") > -1){
				companyDate =  tableName.substring(tableName.indexOf("{") +1 , tableName.indexOf("}") );
				tableNameWithoutDate = tableName.substring(0,tableName.indexOf("{"));
			}
			if (noDuplicatedCompany.get(tableNameWithoutDate.trim()) == null){
				noDuplicatedCompany.put(tableNameWithoutDate.trim(), "");
			} else {
				String error = "Can't have the same company name in more then 1 tab. The company name '" + tableNameWithoutDate.trim() +"' exist in more then 1 tab in your excel.";
				Logger.error(error);
				throw new Exception(error);
			}
			
			
			
			queryDates = "INSERT INTO `" + schemaName + "`.`" + theTableName + "_DATES` (COMPANY,DATE) VALUES('"
			+ tableNameWithoutDate + "','" + companyDate  + "')";
			//Logger.print(queryDates + " Company is " + tableNameWithoutDate);
			stmt.addBatch(queryDates);
			
			Logger.print("Scanning table '" + tableNameWithoutDate + "'");
			companyTable = db.get(tableName);
			for (int j = 0; j < companyTable.getCATAGORY().length; j++) {
				if (companyTable.getCATAGORY()[j].trim().equals(""))
					continue;
				String query = "INSERT INTO `" + schemaName + "`.`" + theTableName + "` (PRODUCT,CATEGORY,COMPANY,GLUTEN_FREE) VALUES('"
						+ companyTable.getPRODUCT()[j] + "','" + companyTable.getCATAGORY()[j] + "','" + tableNameWithoutDate + "','"
						+ companyTable.getGLUTEN_FREE()[j] + "')";

				
				
				try {
					// Create a PreparedStatement based on the query in query
					stmt.addBatch(query);
					
					// Logger.print("Exceuting on table '" + tableName + "'");
					// stmt.executeBatch();
				} catch (Exception e) {
					Logger.print(e.getMessage());
					e.printStackTrace();
					throw e;
				}
			}
		}
		try {
			stmt.executeBatch();
		} catch (Exception e) {
			Logger.print("ERROR - Failed to update PRODUCT_INDEX table");
			e.printStackTrace();
			Logger.print(e.getMessage());
			throw e;
		}
		connection.commit();
		connection.setAutoCommit(true);
		Logger.print("Done builing " + theTableName + " Table.");

	}

	// In case table is null - drop all tables
	public void dropCompanyTable(String table) throws Exception {
		
		Connection connection = MySQLDriverManager.getConnection();
		String schemaName = PropertiesLoader.getProperties("db.scheme");
		
		if (table != null) {
			Logger.print("Dropping table '" + table + "'");
			String query = "DROP TABLE `" + schemaName + "`.`" + table;
			try {
				// Create a PreparedStatement based on the query in query
				PreparedStatement pst = connection.prepareStatement(query);
				pst.execute();
				pst.close();

			} catch (Exception e) {
				Logger.print("ERROR - Failed to dump table '" + table + "'");
				e.printStackTrace();

			}
			
		} else {

			DBReader dBReader = new DBReader();
			Set<String> tables = dBReader.getDBTables();
			Iterator<String> it = tables.iterator();

			while (it.hasNext()) {

				String tableName = (String) it.next();
				/*
				 * if (tableName.trim().equals("category_companies") ||
				 * tableName.trim().equals("product_index")) continue;
				 */
				Logger.print("Dropping table '" + tableName + "'");
				
				String query = "DROP TABLE `" + schemaName + "`.`" + tableName;

				try {
					// Create a PreparedStatement based on the query in query
					PreparedStatement pst = connection.prepareStatement(query);
					pst.execute();
					pst.close();

				} catch (Exception e) {
					Logger.print("ERROR - Failed to dump table '" + tableName + "'");
					e.printStackTrace();

				}
			}

		}
	}
}
