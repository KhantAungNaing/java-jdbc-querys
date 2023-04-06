package com.jdc.kan.connectionassit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public interface ConnectionManager {

	String URL = "jdbc:mysql://localhost:3306/messagetest_db";
	String USR = "root";
	String PWD = "Admin";
	
	Connection getConnection() throws SQLException;
	
	public static ConnectionManager getInstance() {
		return ()->DriverManager.getConnection(URL,USR,PWD);
	}
	
}
