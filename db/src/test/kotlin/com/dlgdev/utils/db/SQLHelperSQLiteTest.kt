package com.dlgdev.utils.db

import com.dlgdev.utils.db.sqlite.Select
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import javax.sql.DataSource


class SQLHelperSQLiteTest {
    val dataSource: DataSource = mock(DataSource::class.java)
    val sqlHelper = SQLHelper(dataSource, SQLHelper.Sources.SQLITE)

    @Test
    fun queryWithoutColumns() {
        assertTrue(sqlHelper.query() is Select)
    }

    @Test fun queryWithColumns() {
        assertTrue(sqlHelper.query("asdf") is Select)
    }

}