package io.haskins.staffmanagement.dao.models

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Employees: Table() {
    val id: Column<Int> = integer("employee_id").autoIncrement()

    val name : Column<String> = varchar("name", length = 100)
    val managerId: Column<Int> = integer("manager_id")
    val departmentId: Column<Int> = integer("department_id")
    val rateId: Column<Int> = (integer("rate_id") references Rates.id)

    val isManager: Column<Boolean> = bool("is_manager")

    override val primaryKey = PrimaryKey(id, name="employees_pk")
}