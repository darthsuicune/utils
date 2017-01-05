package com.dlgdev.utils.db.sqlite;

import com.dlgdev.utils.db.exceptions.MalformedSqlException;

import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class SelectTest extends SqlTest {
	Object object = new Object();

	@Test public void testFromWithoutConditionsIsCalled() throws Exception {
		Object result = new Select(source).from("a").apply(this::verifySet);
		verify(connection).prepareStatement("SELECT * FROM a");
		verify(statement).executeQuery();
		assertEquals(result, object);
	}

	private Object verifySet(ResultSet set) {
		assertEquals(set, resultSet);
		return object;
	}

	@Test public void testFromWithColumnsHasAProperSelectCall() throws Exception {
		String[] columns = {"a", "b"};
		new Select(source, columns).from("a").apply(this::verifySet);
		verify(connection).prepareStatement("SELECT a,b FROM a");
		verify(statement).executeQuery();
	}

	@Test public void testWithWhereMakesARestrictedSearch() throws SQLException {
		String[] whereArgs = {"1"};
		new Select(source).from("a").where("a=?", whereArgs).apply(this::verifySet);
		verify(connection).prepareStatement("SELECT * FROM a WHERE a=?");
		verify(statement, times(whereArgs.length)).setString(anyInt(), anyString());
		verify(statement).executeQuery();
	}

	@Test public void testWithEmptyWhereThrowsException() {
		try {
			new Select(source).from("a").where("", null);
		} catch (MalformedSqlException e) {
			assertTrue(e.getMessage().contains("Idiot"));
		}
	}

	@Test public void testWithNullTextWhereThrowsException() {
		try {
			new Select(source).from("a").where(null, null);
		} catch (MalformedSqlException e) {
			assertTrue(e.getMessage().contains("Idiot"));
		}
	}

	@Test public void testWithMisalignedWhereAndArgsThrowsException() {
		try {
			new Select(source).from("a").where("asdf?", null);
		} catch (MalformedSqlException e) {
			assertTrue(e.getMessage().contains("m8"));
		}
	}

	@Test public void testWithInvertedMisalignedWhereAndArgsThrowsException() {
		try {
			new Select(source).from("a").where("a", new String[]{"a"});
		} catch (MalformedSqlException e) {
			assertTrue(e.getMessage().contains("m8"));
		}
	}

	@Test public void testWithNullTableName() {
		try {
			new Select(source).from(null);
		} catch (MalformedSqlException e) {
			assertTrue(e.getMessage().contains("Seriously?"));
		}
	}

	@Test public void testWhereAndWorks() throws SQLException {
		new Select(source).from("a").where("a=?", new String[]{"a"}).and("b=?", new String[]{"c"})
				.apply(this::verifySet);
		verify(connection).prepareStatement("SELECT * FROM a WHERE a=? AND b=?");
		verify(statement).setString(1, "a");
		verify(statement).setString(2, "c");
	}

	@Test public void testWhereOrWorks() throws SQLException {
		new Select(source).from("a").where("a=?", new String[]{"a"}).or("b=?", new String[]{"c"})
				.apply(this::verifySet);
		verify(connection).prepareStatement("SELECT * FROM a WHERE a=? OR b=?");
		verify(statement).setString(1, "a");
		verify(statement).setString(2, "c");
	}
}