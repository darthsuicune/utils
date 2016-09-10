package com.dlgdev.utils.db;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SelectTest {
	@Mock DataSource source;
	@Mock Connection connection;
	@Mock PreparedStatement statement;
	@Mock ResultSet resultSet;
	Object object = new Object();

	@Before public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		when(source.getConnection()).thenReturn(connection);
		when(connection.prepareStatement(anyString())).thenReturn(statement);
		doNothing().when(statement).setString(anyInt(), anyString());
		when(statement.executeQuery()).thenReturn(resultSet);
	}

	@Test public void testFromWithoutConditionsIsCalled() throws Exception {
		Object result = new Select(source).from("a").execute(this::verifySet);
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
		new Select(source, columns).from("a").execute(this::verifySet);
		verify(connection).prepareStatement("SELECT a,b FROM a");
		verify(statement).executeQuery();
	}

	@Test public void testWithWhereMakesARestrictedSearch() throws SQLException {
		String[] whereArgs = {"1"};
		new Select(source).from("a").where("a=?", whereArgs).execute(this::verifySet);
		verify(connection).prepareStatement("SELECT * FROM a WHERE a=?");
		verify(statement, times(whereArgs.length)).setString(anyInt(), anyString());
		verify(statement).executeQuery();
	}

	@Test public void testWithEmptyWhereThrowsException() {
		try {
			new Select(source).from("a").where("", null).execute(this::verifySet);
		} catch (RuntimeException e) {
			assertTrue(e.getMessage().contains("Idiot"));
		}
	}

	@Test public void testWithNullTextWhereThrowsException() {
		try {
			new Select(source).from("a").where(null, null).execute(this::verifySet);
		} catch (RuntimeException e) {
			assertTrue(e.getMessage().contains("Idiot"));
		}
	}
}