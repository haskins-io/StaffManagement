package io.haskins.staffmanagement.dao.models

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

object Tasks: IntIdTable() {
    val name : Column<String> = varchar("name", length = 100)

    val assigned: Column<Int> = integer("assigned")

    val progress: Column<Int> = integer("progress")
    val status: Column<Int> = integer("status")
    val due: Column<Long> = long("due_date")
    val priority: Column<Int> = integer("priority")
}