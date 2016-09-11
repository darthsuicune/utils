package com.dlgdev.utils.db;

import com.dlgdev.utils.db.exceptions.MalformedSqlException;

import org.apache.commons.lang3.StringUtils;

import java.sql.ResultSet;
import java.util.function.Function;

public class Where {
	private QueryExecutor executor;
	private StringBuilder sql;
	private String[] whereArgs;

	Where(QueryExecutor executor, StringBuilder sql, String[] whereArgs) {
		this.executor = executor;
		this.sql = sql;
		this.whereArgs = whereArgs;
	}

	static void checkPreconditionsOrThrow(String where, String[] whereArgs) {
		if (StringUtils.isEmpty(where)) {
			throw new MalformedSqlException("You passed a where without content. Idiot.");
		}
		int parameterCount = StringUtils.countMatches(where, "?");
		if ((whereArgs != null && parameterCount != whereArgs.length) ||
				(whereArgs == null && parameterCount > 0)) {
			throw new MalformedSqlException("Align your questions and answers m8...");
		}
	}

	public Where and(String where, String[] whereArgs) {
		checkPreconditionsOrThrow(where, whereArgs);
		return this;
	}

	public Where or(String where, String[] whereArgs) {
		checkPreconditionsOrThrow(where, whereArgs);
		return this;
	}

	public <T> T execute(Function<ResultSet, T> function) {
		executor.setWhereArgs(whereArgs);
		return executor.run(sql.toString(), function);
	}
}
