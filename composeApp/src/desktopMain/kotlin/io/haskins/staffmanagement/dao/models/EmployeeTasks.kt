package io.haskins.staffmanagement.dao.models

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object EmployeeTasks: Table() {

    val id: Column<Int> = integer("et_id").autoIncrement()

    val employeeId: Column<Int> = (integer("employee_id") references Employees.id)
    val taskId = reference("task_id", Tasks)

    override val primaryKey = PrimaryKey(Employees.id, name="employeetasks_pk")
}