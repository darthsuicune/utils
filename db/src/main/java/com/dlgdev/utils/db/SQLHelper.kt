package com.dlgdev.utils.db

import com.dlgdev.utils.db.sqlite.Select
import javax.sql.DataSource

class SQLHelper(val dataSource: DataSource?) {

    fun query(): Select {
        return Select(dataSource)
    }
    fun query(vararg columns: String): Select {
        return Select(dataSource, columns)
    }
}