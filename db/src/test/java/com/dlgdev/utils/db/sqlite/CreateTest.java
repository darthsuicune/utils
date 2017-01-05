package com.dlgdev.utils.db.sqlite;

import com.dlgdev.utils.db.exceptions.MalformedSqlException;
import org.junit.Test;

import java.sql.SQLException;
import java.util.*;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;

public class CreateTest extends SqlTest {
	String tableName = "a";
	Map<String, String> fields = new HashMap<>();
	String key = "b";
	List<Create.ForeignKey> foreignKeys = new ArrayList<>();
	List<String> indices = new ArrayList<>();

	@Test public void comprehensiveApiIsAvailable() {
		fields.put("a", "b");
		Create.in(source)
				.table(tableName)
				.fields(fields)
				.primaryKey(key)
				.foreignKeys(foreignKeys)
				.indices(indices).run();
	}

	@Test public void emptyTableNameThrowsException() {
		try {
			Create.in(source).table("");
		} catch (MalformedSqlException e) {
			assertNotNull(e);
		}
	}

	@Test public void emptyFieldsThrowsException() {
		try {
			Create.in(source).table("a").fields(fields).run();
		} catch (MalformedSqlException e) {
			assertNotNull(e);
		}
	}

	@Test public void createsAProperSqlSentence() throws SQLException {
		fields.put("b", "c");
		fields.put("d", "e");
		Create.in(source).table("a").fields(fields).run();
		verify(connection).prepareStatement("CREATE TABLE a (d e, b c)");
	}

	@Test public void withPrimaryKey() throws Exception {
		fields.put("a", "b");
		Create.in(source).table("a").fields(fields).primaryKey("a").run();
		verify(connection).prepareStatement("CREATE TABLE a (a b, PRIMARY KEY (a))");
	}

	@Test public void withCompositePrimaryKey() throws Exception {
		fields.put("a", "b");
		fields.put("c", "b");
		Create.in(source).table("a").fields(fields).primaryKey(Arrays.asList("a", "c")).run();
		verify(connection).prepareStatement("CREATE TABLE a (a b, c b, PRIMARY KEY (a,c))");
	}

	@Test public void withForeignKey() throws Exception {
		fields.put("a", "b");
		Create.in(source).table("a").fields(fields).foreignKey(new Create.ForeignKey("a", "h", "j")).run();
		verify(connection).prepareStatement("CREATE TABLE a (a b, " +
				"CONSTRAINT `a_a_h_j` FOREIGN KEY (a) REFERENCES h (j))");
	}
}