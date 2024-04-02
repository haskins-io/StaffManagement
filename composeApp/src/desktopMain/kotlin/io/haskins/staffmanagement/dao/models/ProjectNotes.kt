package io.haskins.staffmanagement.dao.models

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object ProjectNotes: Table() {

    val id: Column<Int> = integer("pn_id").autoIncrement()

    val projectId: Column<Int> = integer("project_id") references Projects.id
    val noteId = reference("note_id", Notes)

    override val primaryKey = PrimaryKey(Employees.id, name="projectsnotes_pk")
}