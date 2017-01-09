package com.dlgdev.utils.db

import com.dlgdev.utils.db.sqlite.Select
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mockito.mock
import javax.sql.DataSource


class SQLHelperTest {
    val dataSource: DataSource = mock(DataSource::class.java)
    val sqlHelper = SQLHelper(dataSource, SQLHelper.Sources.SQLITE)
    @Before
    fun setUp() {

    }

    @Test
    fun queryWithoutColumns() {
        assertTrue(sqlHelper.query() is Select)
    }

    @Test fun queryWithColumns() {
        assertTrue(sqlHelper.query("asdf") is Select)
    }

}