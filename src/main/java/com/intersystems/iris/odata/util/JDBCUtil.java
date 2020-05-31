package com.intersystems.iris.odata.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBCUtil {

	private static JDBCUtil instance = null;

	public Connection connection;

	private JDBCUtil() {
		try {
			Class.forName("com.intersystems.jdbc.IRISDriver").newInstance();
			String url = "jdbc:IRIS://192.168.56.1:9091/Contest";
			String username = "_SYSTEM";
			String password = "welcome1";
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
