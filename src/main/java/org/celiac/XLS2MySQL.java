package org.celiac;

//import java.util.Iterator;
import java.util.Map;

import org.celiac.datatype.CodeDesc;
import org.celiac.datatype.CompanyTable;
import org.celiac.datatype.GFUser;
import org.celiac.util.DBWriter;
import org.celiac.util.XLSReader;

public class XLS2MySQL {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {

		String type = args[0];
		DBWriter dBWriter = new DBWriter();
		XLSReader xLSReader = new XLSReader();

		if (type.equals("product_data")) {

			Map<String, CompanyTable> intoDB = xLSReader.readWorkBook();
			CodeDesc[] codeDescs = xLSReader.readCodeDescWorkBook();

			// Iterator<String> it = intoDB.keySet().iterator();
			// String tableName = null;
			// CompanyTable companyTable = null;
			try {
				dBWriter.dropCompanyTable("PRODUCT_INDEX");
				dBWriter.dropCompanyTable("CODE_DESC");
			} catch (Exception e) {
				// do nothing
			}
			/*
			 * while (it.hasNext()){ tableName = it.next(); companyTable =
			 * intoDB.get(tableName);
			 * dBWriter.createCompanyTable(tableName,companyTable); }
			 */
			dBWriter.createProductIndexTable("PRODUCT_INDEX");
			dBWriter.insertProductIndexTable(intoDB, "PRODUCT_INDEX");
			dBWriter.createCodeDescTable("CODE_DESC");
			dBWriter.insertCodeDescTable(codeDescs, "CODE_DESC");

		} else if (type.equals("user_data")) {

			// Insert the users
			try {
				dBWriter.dropCompanyTable("USERS");
			} catch (Exception e) {
				// do nothing
			}
			dBWriter.createUsersTable("USERS");
			GFUser[] usersTable = xLSReader.readUsersWorkBook();
			dBWriter.insertUsersTable(usersTable, "USERS");
		} else if (type.equals("statistics")) {

			// Insert the users
			try {
				dBWriter.dropCompanyTable("STATISTICS");
			} catch (Exception e) {
				// do nothing
			}
			dBWriter.createStatisticsTable();
		} else if (type.equals("images")) {

			// Insert the users
			try {
				dBWriter.dropCompanyTable("IMAGES");
			} catch (Exception e) {
				// do nothing
			}
			dBWriter.createProductImageTable("IMAGES");
		} else if (type.equals("jobs")) {

			// Insert the users
			try {
				dBWriter.dropCompanyTable("JOBS");
			} catch (Exception e) {
				// do nothing
			}
			dBWriter.createJobsTable("JOBS");
		} else if (type.equals("productDiff")) {

			// Insert the users
			try {
				dBWriter.dropCompanyTable("PRODUCT_DIFF");
			} catch (Exception e) {
				// do nothing
			}
			dBWriter.createDiffTable("PRODUCT_DIFF");
		} else if (type.equals("companyDiff")) {

			// Insert the users
			try {
				dBWriter.dropCompanyTable("COMPANY_DIFF");
			} catch (Exception e) {
				// do nothing
			}
			dBWriter.createDiffTable("COMPANY_DIFF");
		}
	}

}
