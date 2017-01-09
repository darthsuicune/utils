package com.dlgdev.utils.db.mysql

import com.dlgdev.utils.db.From
import com.dlgdev.utils.db.Select
import javax.sql.DataSource

class Select(val dataSource: DataSource): Select {
    constructor(dataSource: DataSource, vararg columns: String) : this(dataSource) {
        this.columns = columns
    }

    var columns: Array<out String> = emptyArray()

    override fun from(tableName: String): From {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}