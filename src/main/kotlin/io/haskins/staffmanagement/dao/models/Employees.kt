package io.haskins.staffmanagement.dao.models

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Employees: Table() {
    val id: Column<Int> = integer("employee_id").autoIncrement()
    val managerId: Column<Int> = integer("manager_id")
    val departmentId: Column<Int> = integer("department_id")
    val rateId: Column<Int> = integer("rate_id")
    val name : Column<String> = varchar("name", length = 100)

    override val primaryKey = PrimaryKey(Departments.id, name="employees_pk")
}