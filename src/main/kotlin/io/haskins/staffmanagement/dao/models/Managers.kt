package io.haskins.staffmanagement.dao.models

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Managers: Table() {
    val id: Column<Int> = integer("manager_id").autoIncrement()
    val departmentId: Column<Int> = integer("department_id")
    val name : Column<String> = varchar("name", length = 100)
}