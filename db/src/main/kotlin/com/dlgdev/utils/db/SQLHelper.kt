package com.dlgdev.utils.db

import javax.sql.DataSource

class SQLHelper(val dataSource: DataSource, val sourceType: Sources) {

    fun query(): Select {
        when (sourceType) {
            Sources.SQLITE -> return com.dlgdev.utils.db.sqlite.Select(dataSource)
            SQLHelper.Sources.MYSQL -> return com.dlgdev.utils.db.mysql.Select(dataSource)
        }

    }
    fun query(vararg columns: String): Select {
        when (sourceType) {
            Sources.SQLITE -> return com.dlgdev.utils.db.sqlite.Select(dataSource, columns)
            Sources.MYSQL -> return com.dlgdev.utils.db.mysql.Select(dataSource, *columns)
        }
    }

    enum class Sources {
        MYSQL(), SQLITE();
    }
}