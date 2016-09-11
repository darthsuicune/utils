package com.dlgdev.utils.db;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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
		return new Insert(source, tableName);
	}

	public Insert values(Map<String, String> values) {
		this.values.add(values);
		return this;
	}

	public Insert values(Collection<Map<String, String>> values) {
		this.values.addAll(values);
		return this;
	}
}
