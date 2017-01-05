package com.dlgdev.utils.db

import com.dlgdev.utils.db.sqlite.Select
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import javax.sql.DataSource


class SQLHelperTest {
    val dataSource: DataSource? = null
    val sqlHelper = SQLHelper(dataSource)
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