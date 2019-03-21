package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseInfo {
	
	//JDBC Driver name and Database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://cse.unl.edu:3306/psuchato";
	
	//Database credentials
	static final String USER = "psuchato";
	static final String PASS = "6_8Je6";
	
	public DatabaseInfo() {
		super();
	}
	
	//Initiate connection
	public static Connection getConnection() {
		try{
			Class.forName(JDBC_DRIVER);			
		
			Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
			
			return conn;
		} catch (SQLException e){
			e.printStackTrace();
		}catch(ClassNotFoundException e){
			System.err.println(e);
			System.exit (-1);
		}
		return null;
		
	}
	

	public static void closeConnection(Connection conn){
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
