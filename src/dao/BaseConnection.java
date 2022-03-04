package dao;

import java.sql.Connection;
import java.sql.DriverManager;

import util.staticValue;

/**
 * 
 *������ݿ������
 */
public class BaseConnection {
	
	public static Connection getConnection(){
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(staticValue.url, staticValue.user, staticValue.password);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}

}