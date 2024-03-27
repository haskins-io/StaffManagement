package io.haskins.staffmanagement.dao.models

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object ProjectEmployees: Table() {
    val id: Column<Int> = integer("pe_id").autoIncrement()

    val employeeId: Column<Int> = (integer("employee_id")  references Employees.id)
    val projectId: Column<Int> = (integer("project_id") references Projects.id)
    val allocation: Column<Int> = integer("allocation")

    val cost: Column<Float> = float("cost")

    override val primaryKey = PrimaryKey(Employees.id, name="project_employee_pk")
}