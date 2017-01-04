package com.dlgdev.utils.db;

import com.dlgdev.utils.db.exceptions.MalformedSqlException;
import org.apache.commons.lang3.StringUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Create {
	private DataSource source;

	private Create(DataSource source) {
		this.source = source;
	}

	public static Create in(DataSource source) {
		return new Create(source);
	}

	public TableCreator table(String tableName) {
		if (StringUtils.isEmpty(tableName)) {
			throw new MalformedSqlException("You cannot create a table with an empty name");
		}
		return new TableCreator(source, tableName);
	}

	public static class TableCreator {
		private DataSource source;
		private String tableName;
		private Map<String, String> fields = new HashMap<>();
		private String primaryKey;
		private List<ForeignKey> foreignKeys = new ArrayList<>();
		private List<String> indices = new ArrayList<>();

		private TableCreator(DataSource source, String tableName) {
			this.source = source;
			this.tableName = tableName;
		}


		public TableCreator fields(Map<String, String> fields) {
			if(fields.isEmpty()) {
				throw new MalformedSqlException("You cannot create a table without fields.");
			}
			this.fields.putAll(fields);
			return this;
		}

		public TableCreator primaryKey(String key) {
			this.primaryKey = key;
			return this;
		}

		public TableCreator primaryKey(List<String> key) {
			this.primaryKey = key.stream().collect(Collectors.joining(","));
			return this;
		}

		public TableCreator foreignKey(ForeignKey foreignKey) {
			this.foreignKeys.add(foreignKey);
			return this;
		}

		public TableCreator foreignKeys(List<ForeignKey> foreignKeys) {
			this.foreignKeys.addAll(foreignKeys);
			return this;
		}

		public TableCreator indices(List<String> indices) {
			this.indices = indices;
			return this;
		}

		public void run() {
			String sql = String.format("CREATE TABLE %s (%s%s%s)", tableName, fieldsAsString(), primaryKeyAsString(),
					foreignKeysAsString());
			try (Connection connection = source.getConnection()) {
				connection.prepareStatement(sql).execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		private String primaryKeyAsString() {
			if(StringUtils.isNotEmpty(primaryKey)) {
				return String.format(", PRIMARY KEY (%s)", primaryKey);
			} else {
				return "";
			}
		}

		private String fieldsAsString() {
			if(fields.isEmpty()) {
				throw new MalformedSqlException("You cannot create a table without fields.");
			}
			StringBuilder builder = new StringBuilder();
			fields.forEach((key, value) -> builder.append(key).append(" ").append(value).append(", "));
			return builder.delete(builder.length() - 2, builder.length()).toString();
		}

		private String foreignKeysAsString() {
			if(foreignKeys.isEmpty()) {
				return "";
			}
			StringBuilder builder = new StringBuilder(", ");
			foreignKeys.forEach(
					(key) -> builder.append(String.format("CONSTRAINT %s FOREIGN KEY (%s) REFERENCES %s (%s)",
							key.constraintName(tableName), key.localField, key.referencedTable, key.referencedField)));
			return builder.toString();
		}
	}

	public static class ForeignKey {
		public String localField;
		public String referencedTable;
		public String referencedField;

		public ForeignKey(String localField, String referencedTable, String referencedField) {
			this.localField = localField;
			this.referencedTable = referencedTable;
			this.referencedField = referencedField;
		}

		public String constraintName(String tableName) {
			return String.format("`%s_%s_%s_%s`", tableName, localField, referencedTable, referencedField);
		}
	}
}
