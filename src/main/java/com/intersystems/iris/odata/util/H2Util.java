package com.intersystems.iris.odata.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class H2Util {

	private static H2Util instance = null;

	public Connection connection;
	
	private H2Util() {
		try {
			Class.forName("org.h2.Driver").newInstance();
			String url = "jdbc:h2:file:~/odatadb;DB_CLOSE_ON_EXIT=FALSE;IFEXISTS=FALSE;DB_CLOSE_DELAY=-1";
			String username = "sa";
			String password = "password";
			connection = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
			connection = null;
		}
	}

	public static H2Util getInstance() {

		if (instance == null) {
			instance = new H2Util();
		}

		return instance;

	}
	
}
