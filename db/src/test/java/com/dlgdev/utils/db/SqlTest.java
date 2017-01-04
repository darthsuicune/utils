package com.dlgdev.utils.db;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class SqlTest {
	@Mock DataSource source;
	@Mock Connection connection;
	@Mock PreparedStatement statement;
	@Mock ResultSet resultSet;

	@Before public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		when(source.getConnection()).thenReturn(connection);
		when(connection.prepareStatement(anyString())).thenReturn(statement);
		doNothing().when(statement).setString(anyInt(), anyString());
		when(statement.executeQuery()).thenReturn(resultSet);
	}
}
