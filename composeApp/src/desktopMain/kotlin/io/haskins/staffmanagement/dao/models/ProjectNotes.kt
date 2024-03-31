package io.haskins.staffmanagement.dao.models

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object ProjectNotes: Table() {
    val id: Column<Int> = integer("pn_id").autoIncrement()

    val date: Column<Long> = long("date")

    val projectId: Column<Int> = (integer("project_id") references Projects.id)
    val title: Column<String> = text("title")
    val note: Column<String> = text("note")

    override val primaryKey = PrimaryKey(Employees.id, name="projectnotes_pk")
}