package com.dlgdev.utils.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;

import javax.sql.DataSource;

public class QueryExecutor {
	private DataSource dataSource;
	private String[] whereArgs;

	public QueryExecutor(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public <T> T run(final String sql, Function<ResultSet, T> function) {
		try (Connection connection = dataSource.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(sql);
			if (whereArgs != null && whereArgs.length > 0) {
				for (int i = 0, len = whereArgs.length; i < len; i++) {
					statement.setString(i + 1, whereArgs[i]);
				}
			}
			ResultSet set = statement.executeQuery();
			if (set != null) {
				return function.apply(set);
			} else {
				throw new RuntimeException(
						"Error while running the query, resultSet is null: " + sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error while running the query: " + sql, e);
		}
	}

	public void setWhereArgs(String[] whereArgs) {
		this.whereArgs = whereArgs;
	}
}
