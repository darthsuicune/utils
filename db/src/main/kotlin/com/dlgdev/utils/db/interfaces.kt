package com.dlgdev.utils.db

import java.sql.ResultSet

interface Select {
    fun from(tableName: String): From
}

interface From {
    fun where(where: String, whereArgs: Array<String>): Where
    fun <T> apply(function: java.util.function.Function<ResultSet, T>): T
}

interface Where {
    fun and(where: String, whereArgs: Array<String>): com.dlgdev.utils.db.sqlite.Where
    fun or(where: String, whereArgs: Array<String>): com.dlgdev.utils.db.sqlite.Where
    fun <T> apply(function: java.util.function.Function<ResultSet, T>): T

}