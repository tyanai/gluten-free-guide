package org.celiac.util.database;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.celiac.datatype.CompanyTable;
import org.celiac.datatype.GFUser;
import org.celiac.datatype.IndexRow;
import org.celiac.util.Logger;
import org.celiac.util.XLSReader;

public class DBInMemory {

	private static List<IndexRow> db = null;
	private static List<GFUser> usersDb = null;

	public static List<IndexRow> getDB() {

		if (db == null) {
			db = new ArrayList<IndexRow>();

			// load DB into
			try {
				XLSReader xLSReader = new XLSReader();
				Map<String, CompanyTable> intoDB = xLSReader.readWorkBook();

				if (intoDB.size() < 1) {
					Logger.print("Database (excel) file is empty. Exit program.");
					System.exit(1);
				}

				Iterator<String> it = intoDB.keySet().iterator();
				String tableName = null;
				CompanyTable companyTable = null;
				IndexRow indexRow = null;

				while (it.hasNext()) {
					tableName = (String) it.next();
					Logger.print("Scanning table '" + tableName + "'");
					companyTable = (CompanyTable) intoDB.get(tableName);
					for (int j = 0; j < companyTable.getCATAGORY().length; j++) {
						if (companyTable.getCATAGORY()[j].trim().equals(""))
							continue;

						indexRow = new IndexRow();
						indexRow.setProduct(companyTable.getPRODUCT()[j]);
						indexRow.setCategory(companyTable.getCATAGORY()[j]);
						indexRow.setCompany(tableName);
						indexRow.setGlutenFree(companyTable.getGLUTEN_FREE()[j]);

						db.add(indexRow);
					}
				}

			} catch (Exception e) {
				Logger.print("Fail to load database into memory. Exit program");
				e.printStackTrace();
				System.exit(1);
			}

		}

		return db;
	}

	public static List<GFUser> getUsersDB() {

		if (usersDb == null) {
			usersDb = new ArrayList<GFUser>();

			// load DB into
			try {
				XLSReader xLSReader = new XLSReader();
				GFUser[] usersTable = xLSReader.readUsersWorkBook();

				int howMany = usersTable.length;
				System.out.println("Num of user " + howMany);
				
				for (int i = 0; i < howMany; i++) {
					
					usersDb.add(usersTable[i]);
				}

			} catch (Exception e) {
				Logger.print("Fail to load database into memory. Exit program");
				e.printStackTrace();
				System.exit(1);
			}

		}

		return usersDb;
	}
}
