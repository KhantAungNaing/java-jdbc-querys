package com.jdc.kan.connectionassit;

import java.sql.SQLException;

public class DatabaseInitializer {

	public static void truncateTable(String... tables) {

		try (var conn = ConnectionManager.getInstance().getConnection(); var stmt = conn.createStatement()) {

			
			for (String table : tables) {
				stmt.execute("truncate table %s".formatted(table));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
