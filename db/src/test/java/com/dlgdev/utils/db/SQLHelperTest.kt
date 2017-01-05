package com.dlgdev.utils.db

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
    fun query() {
        fail()
    }

}