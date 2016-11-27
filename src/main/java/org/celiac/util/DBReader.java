package org.celiac.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class DBReader {

	public Set<String> getDBTables() throws Exception {
		Connection connection = MySQLDriverManager.getConnection();
		HashSet<String> output = new HashSet<String>();
		try {

			// Gets the metadata of the database
			DatabaseMetaData dbmd = connection.getMetaData();
			String[] types = { "TABLE" };
			ResultSet rs = dbmd.getTables(null, null, "%", types);

			while (rs.next()) {
				// String tableName = rs.getString(3);
				
				//System.out.println(rs.getRow() + " tableName " + tableName);
				output.add(rs.getString(3));

			}
		} catch (SQLException e) {
			e.printStackTrace();
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return output;

	}

	
	

	
}
