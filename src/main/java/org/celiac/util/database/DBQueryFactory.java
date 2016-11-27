package org.celiac.util.database;

import org.celiac.util.PropertiesLoader;
public class DBQueryFactory {

	public static GFQuery getDBHandler(){
		
		String useOfExternalDB = "false";
		useOfExternalDB = PropertiesLoader.getProperties("use.external.db");
		
		if (useOfExternalDB.trim().equals("true")) return new ExternalDBQueryImpl();
		else return new MemoryDBQueryImpl();
		
	}
}
