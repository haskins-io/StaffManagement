package io.haskins.staffmanagement.dao.models

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Projects: Table() {
    val id: Column<Int> = integer("project_id").autoIncrement()

    val name : Column<String> = varchar("name", length = 100)
    val description : Column<String> = varchar("description", length = 100)
    val code : Column<String> = varchar("code", length = 100)

    val budget: Column<Int> = integer("budget")
    val cost: Column<Int> = integer("cost")
    val status: Column<Int> = integer("status")
    val priority: Column<Int> = integer("priority")
    val due: Column<Long> = long("due_date")
    val progress: Column<Int> = integer("progress")

    override val primaryKey = PrimaryKey(id, name="projects_pk")
}