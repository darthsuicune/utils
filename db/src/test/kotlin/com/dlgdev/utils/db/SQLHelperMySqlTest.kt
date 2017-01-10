package com.dlgdev.utils.db

import com.dlgdev.utils.db.mysql.Select
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import javax.sql.DataSource

class SQLHelperMySqlTest {
    val dataSource: DataSource = Mockito.mock(DataSource::class.java)
    val sqlHelper = SQLHelper(dataSource, SQLHelper.Sources.MYSQL)

    @Test
    fun queryWithoutColumns() {
        Assert.assertTrue(sqlHelper.query() is Select)
    }

    @Test fun queryWithColumns() {
        Assert.assertTrue(sqlHelper.query("asdf") is Select)
    }

}