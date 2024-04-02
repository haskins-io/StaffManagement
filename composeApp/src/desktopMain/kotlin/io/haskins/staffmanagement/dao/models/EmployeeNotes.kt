package io.haskins.staffmanagement.dao.models

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object EmployeeNotes: Table() {

    val id: Column<Int> = integer("en_id").autoIncrement()

    val employeeId: Column<Int> = (integer("employee_id") references Employees.id)
    val noteId = reference("note_id", Notes)

    override val primaryKey = PrimaryKey(Employees.id, name="employeenotes_pk")
}