package com.dlgdev.utils.db.mysql

import com.dlgdev.utils.db.Select
import com.dlgdev.utils.db.Where
import java.sql.ResultSet
import java.util.function.Function
import javax.sql.DataSource

class Select(val dataSource: DataSource): Select {
    val sql = StringBuilder("SELECT ")
    var columns: Array<out String> = emptyArray()

    constructor(dataSource: DataSource, vararg columns: String) : this(dataSource) {
        this.columns = columns
    }

    override fun from(tableName: String): com.dlgdev.utils.db.From {
        if(columns.isEmpty()) {
            sql.append("*")
        } else {
            sql.append(columns.joinToString(","))
        }
        sql.append(" FROM $tableName")
        return From(sql)
    }

    class From(val sql: StringBuilder) : com.dlgdev.utils.db.From {
        override fun where(where: String, whereArgs: Array<String>): Where {
            throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
        override fun <T> apply(function: Function<ResultSet, T>): T {
            throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }
}