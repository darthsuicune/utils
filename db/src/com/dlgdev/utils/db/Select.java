package com.dlgdev.utils.db;

import java.sql.ResultSet;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.sql.DataSource;

public class Select {
	private final QueryExecutor queryExecutor;
	private final StringBuilder sql;

	public Select(DataSource dataSource) {
		this.queryExecutor = new QueryExecutor(dataSource);
		sql = new StringBuilder("SELECT *");
	}

	public Select(DataSource dataSource, String[] columns) {
		this.queryExecutor = new QueryExecutor(dataSource);
		sql = new StringBuilder("SELECT " + Arrays.stream(columns).collect(
				Collectors.joining(",")));
	}

	public From from(String tableName) {
		sql.append(" FROM ").append(tableName);
		return new From(queryExecutor, sql);
	}

	public class From {
		private final QueryExecutor queryExecutor;
		private StringBuilder sql;

		public From(QueryExecutor queryExecutor, StringBuilder sql) {
			this.queryExecutor = queryExecutor;
			this.sql = sql;
		}

		public Where where(String where, String[] whereArgs) {
			Where.checkPreconditionsOrThrow(where, whereArgs);
			sql.append(" WHERE ").append(where);
			return new Where(queryExecutor, sql, whereArgs);
		}

		public <T> T execute(Function<ResultSet, T> function) {
			return queryExecutor.run(sql.toString(), function);
		}
	}

}
