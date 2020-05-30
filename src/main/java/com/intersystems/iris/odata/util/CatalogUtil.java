package com.intersystems.iris.odata.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.intersystems.iris.odata.model.SQLElement;

public class CatalogUtil {
	
	public static ArrayList<SQLElement> getSQLColumns(String SQLTable) {
		
		Connection conn = JDBCUtil.getInstance().connection;
		
		ArrayList<SQLElement> result = new ArrayList<SQLElement>();
		
		try {
			ResultSet rs = conn
					.createStatement()
					.executeQuery("SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = '" + SQLTable + "'");
			while(rs.next()) {
				SQLElement row = new SQLElement(rs.getString("COLUMN_NAME"), rs.getString("DATA_TYPE"));
				result.add(row);
			}
			
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static ArrayList<SQLElement> getSQLTables(String Schema) {
		
		Connection conn = JDBCUtil.getInstance().connection;
		
		ArrayList<SQLElement> result = new ArrayList<SQLElement>();
		
		try {
			ResultSet rs = conn
					.createStatement()
					.executeQuery("SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'BASE TABLE' AND TABLE_SCHEMA = '" + Schema + "'");
			while(rs.next()) {
				SQLElement row = new SQLElement(rs.getString("TABLE_NAME"), rs.getString("TABLE_TYPE"));
				result.add(row);
			}
			
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
