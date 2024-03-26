package io.haskins.staffmanagement.dao.models

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Departments : Table() {
    val id: Column<Int> = integer("department_id").autoIncrement()
    val name : Column<String> = varchar("name", length = 100)
}