package dev.reese.project1.util;

import java.sql.Connection;
import java.sql.DriverManager;

//import util.JDBCConnection;


public class JDBCConnection {
	
	
private static Connection conn = null;
	
	public static Connection getConnection() {
		
		try {
			
			if(conn == null) {
				//Make a new connection
				
				/*
				 * Oracle added a *hotfix* to ensure that
				 * ojdbc drives would correctly load
				 * at the beginning of you app starting.
				 */
				Class.forName("oracle.jdbc.driver.OracleDriver");
				
				
				//To establish a Connection we need 3 credentials
				//url (endpoint), username, password
				String endpoint = "reesepocherevaturedbinstance1.ceqyrensrzot.us-east-1.rds.amazonaws.com";
				String url = "jdbc:oracle:thin:@" + endpoint + ":1521:ORCL";
				String username = "reese";
				String password = "devasdfghjkl";
				
//				Properties props = new Properties();
//				FileInputStream input = new FileInputStream(JDBCConnection.class.getClassLoader().getResource("connection.properties").getFile());
//				props.load(input);				
//				
//				String url = props.getProperty("url");
//				String username = props.getProperty("username");
//				String password = props.getProperty("password");
				
				//Establish our connection.
				conn = DriverManager.getConnection(url, username, password);
				return conn;
				
			} else {
				//Return the connection that already exists.
				return conn;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return null;
	}

}
