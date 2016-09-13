package com.dlgdev.utils.db;

import com.dlgdev.utils.db.exceptions.MalformedSqlException;

import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.sql.DataSource;

public class Insert {
	private final DataSource source;
	private final String tableName;
	private List<Map<String, String>> values = new ArrayList<>();

	private Insert(DataSource source, String tableName) {
		this.source = source;
		this.tableName = tableName;
	}

	public static Insert into(DataSource source, String tableName) {
		if (StringUtils.isEmpty(tableName)) {
			throw new MalformedSqlException("You have to give the table name");
		}
		return new Insert(source, tableName);
	}

	public Insert values(Map<String, String> values) {
		if (values.isEmpty()) {
			throw new MalformedSqlException("You have to give the table name");
		}
		this.values.add(values);
		return this;
	}

	public Insert values(Collection<Map<String, String>> values) {
		this.values.addAll(values);
		return this;
	}

	public void run() {
		String sql = buildSqlQueryString();
		runSqlQuery(sql);
	}

	private String buildSqlQueryString() {
		Set<String> columns = new LinkedHashSet<>();
		List<String> items = new ArrayList<>(values.size());
		values.forEach(item -> {
			item.forEach((key, value) -> {
				columns.add(key);
			});
		});
		values.forEach(item -> {
			items.add(buildItemString(columns, item));
		});
		String columnsAsString = columns.stream().collect(Collectors.joining(","));
		String valuesAsString = items.stream().collect(Collectors.joining(","));
		return String.format("INSERT INTO %s(%s) VALUES %s", tableName, columnsAsString,
				valuesAsString);
	}

	private String buildItemString(Set<String> columns, Map<String, String> item) {
		StringBuilder builder = new StringBuilder("(");
		columns.forEach(column -> {
			if(item.containsKey(column)) {
				builder.append(item.get(column));
			} else {
				builder.append("null");
			}
			builder.append(",");
		});
		return builder.deleteCharAt(builder.length() - 1).append(")").toString();
	}

	private void runSqlQuery(String sql) {
		try (Connection connection = source.getConnection()) {
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("", e);
		}
	}
}
