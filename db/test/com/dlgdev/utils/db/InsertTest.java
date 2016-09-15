package com.dlgdev.utils.db;

import com.dlgdev.utils.db.exceptions.MalformedSqlException;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;

public class InsertTest extends SqlTest{
	@Test public void emptyTableThrowsException() throws Exception {
		try {
			Insert.into(source, "");
		} catch (MalformedSqlException e) {
			assertNotNull(e);
		}
	}

	@Test public void insertSingleValueSet() throws Exception {
		Map<String, String> values = new HashMap<>(2);
		values.put("b", "d");
		values.put("c", "e");
		Insert.into(source, "a").values(values).run();
		verify(connection).prepareStatement("INSERT INTO a(b,c) VALUES (d,e)");
	}

	@Test public void insertValues() throws Exception {
		Collection<Map<String, String>> list = new ArrayList<>(2);
		Map<String, String> values = new HashMap<>(2);
		values.put("b", "d");
		values.put("c", "e");
		list.add(values);
		values = new HashMap<>(2);
		values.put("b", "f");
		values.put("c", "g");
		list.add(values);
		Insert.into(source, "a").values(list).run();
		verify(connection).prepareStatement("INSERT INTO a(b,c) VALUES (d,e),(f,g)");
	}

	@Test public void insertUnalignedValues() throws Exception {
		Collection<Map<String, String>> list = new ArrayList<>(2);
		Map<String, String> values = new HashMap<>(2);
		values.put("b", "d");
		values.put("c", "e");
		list.add(values);
		values = new HashMap<>(2);
		values.put("b", "f");
		values.put("c", "g");
		values.put("h", "i");
		list.add(values);
		Insert.into(source, "a").values(list).run();
		verify(connection).prepareStatement("INSERT INTO a(b,c,h) VALUES (d,e,null),(f,g,i)");
	}

	@Test public void anEmptyValuesThrowsAnException() {
		try {
			Insert.into(source, "a").values((Map<String,String>)null);
		} catch (MalformedSqlException e) {
			assertNotNull(e);
		}
	}
}