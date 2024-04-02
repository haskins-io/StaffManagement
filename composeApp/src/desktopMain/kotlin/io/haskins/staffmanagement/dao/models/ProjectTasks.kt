package io.haskins.staffmanagement.dao.models

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object ProjectTasks: Table() {

    val id: Column<Int> = integer("pt_id").autoIncrement()

    val projectId: Column<Int> = (integer("project_id") references Projects.id)
    val taskId = reference("task_id", Tasks)

    override val primaryKey = PrimaryKey(Employees.id, name="projecttasks_pk")
}