package com.dlgdev.utils.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.sql.DataSource;

public class Select {
	private final DataSource dataSource;
	private final StringBuilder sql;
	private String[] whereArgs;

	public Select(DataSource dataSource) {
		this.dataSource = dataSource;
		sql = new StringBuilder("SELECT *");
	}

	public Select(DataSource dataSource, String[] columns) {
		this.dataSource = dataSource;
		sql = new StringBuilder("SELECT " + Arrays.stream(columns).collect(
				Collectors.joining(",")));
	}

	public Select from(String tableName) {
		sql.append(" FROM " ).append(tableName);
		return this;
	}
	public Select where(String where, String[] whereArgs) {
		sql.append(" WHERE ").append(where);
		this.whereArgs = whereArgs;
		return this;
	}

	public void execute(Consumer<ResultSet> consumer) {
		try(Connection connection = dataSource.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(sql.toString());
			if(whereArgs != null) {
				for(int i = 1, len = whereArgs.length; i <= len; i++) {
					statement.setString(i, whereArgs[i - 1]);
				}
			}
			ResultSet set = statement.executeQuery();
			if(set != null) {
				consumer.accept(set);
			} else {
				throw new RuntimeException("Error while running the query: " + sql.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error while running the query: " + sql.toString(), e);
		}
	}
}
