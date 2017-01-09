package com.dlgdev.utils.db.mysql

import com.dlgdev.utils.db.Select
import javax.sql.DataSource

class Select(val dataSource: DataSource): Select {
    var columns: Array<out String> = emptyArray()
    constructor(dataSource: DataSource, columns: Array<out String>) : this(dataSource) {
        this.columns = columns
    }

    fun from(table: String) {

    }
}