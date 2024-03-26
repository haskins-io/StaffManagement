package io.haskins.staffmanagement.dao.models

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Projects: Table() {
    val id: Column<Int> = integer("project_id").autoIncrement()
    val name : Column<String> = varchar("name", length = 100)
    val code : Column<String> = varchar("code", length = 100)

    override val primaryKey = PrimaryKey(Departments.id, name="projects_pk")
}