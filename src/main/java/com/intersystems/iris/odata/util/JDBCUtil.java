package com.intersystems.iris.odata.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class JDBCUtil {

	private static JDBCUtil instance = null;

	public Connection connection;
	
	private JDBCUtil() {
		
		Connection h2Conn = H2Util.getInstance().connection;
		
		try {
			ResultSet rs = h2Conn.createStatement().executeQuery("SELECT * FROM param");
			rs.next();
			Class.forName("com.intersystems.jdbc.IRISDriver").newInstance();
			String url = "jdbc:IRIS://" + rs.getString("host") + ":" + rs.getString("port") + "/" + rs.getString("namespace");
			String username = rs.getString("username");
			String password = rs.getString("password");
			connection = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
			connection = null;
		}
	}

	public static JDBCUtil getInstance() {

		if (instance == null) {
			instance = new JDBCUtil();
		}

		return instance;

	}
	
}
