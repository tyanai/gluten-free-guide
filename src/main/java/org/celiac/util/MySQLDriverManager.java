package org.celiac.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySQLDriverManager {

	

	private static Connection connection = null;
	


	static public Connection getConnection() throws Exception {
		
		if (connection != null){
			if (!connection.isClosed()) return connection;
		}
		
		connection = connection();
		return connection;
	}
	
	static public Connection getConnectionAgain() throws Exception {

		connection = connection();
		return connection;
	}
	
	static private Connection connection() throws Exception {

				
		try {

			Class.forName(PropertiesLoader.getProperties("db.driver"));
			String url = "jdbc:mysql://"+ PropertiesLoader.getProperties("db.ip") +":" + PropertiesLoader.getProperties("db.port") +"/" + PropertiesLoader.getProperties("db.scheme");
			Properties prop = new Properties();

			String username = PropertiesLoader.getProperties("db.user");
			String password = PropertiesLoader.getProperties("db.password");

			prop.put("user", username);
			prop.put("password", password);
			prop.put("characterEncoding", "UTF-8");

			connection = DriverManager.getConnection(url, prop);
		} catch (SQLException e) {
			e.printStackTrace();
			if (connection != null && !connection.isClosed()) {
				connection.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return connection;
	}
}
