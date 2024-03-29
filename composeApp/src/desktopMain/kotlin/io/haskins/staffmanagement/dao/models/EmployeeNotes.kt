package io.haskins.staffmanagement.dao.models

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object EmployeeNotes: Table() {
    val id: Column<Int> = integer("en_id").autoIncrement()

    val date: Column<Int> = integer("date")

    val employeeId: Column<Int> = (integer("employee_id") references Projects.id)
    val title: Column<String> = text("title")
    val note: Column<String> = text("note")

    override val primaryKey = PrimaryKey(Employees.id, name="employeenotes_pk")
}