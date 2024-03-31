package io.haskins.staffmanagement.dao.models

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object EmployeeHolidays: Table() {
    val id: Column<Int> = integer("eh_id").autoIncrement()

    val employeeId: Column<Int> = (integer("employee_id") references Projects.id)

    val start: Column<Long> = long("start")
    val end: Column<Long> = long("end")

    override val primaryKey = PrimaryKey(Employees.id, name="employeenotes_pk")
}