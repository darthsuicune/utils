package com.dlgdev.utils.db

import com.dlgdev.utils.db.sqlite.Select
import javax.sql.DataSource

class SQLHelper(val dataSource: DataSource, val source: Sources) {

    fun query(): Select {
        when(source) {
            Sources.SQLITE -> return Select(dataSource)
            Sources.MYSQL -> return Select(dataSource)
        }

    }
    fun query(vararg columns: String): Select {
        when(source) {
            Sources.SQLITE -> return Select(dataSource, columns)
            Sources.MYSQL -> return Select(dataSource, columns)
        }
    }

    enum class Sources {
        MYSQL(), SQLITE();
    }
}