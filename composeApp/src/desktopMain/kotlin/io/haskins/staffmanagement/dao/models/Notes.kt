package io.haskins.staffmanagement.dao.models

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

object Notes: IntIdTable() {

    val date: Column<Long> = long("date")
    val title: Column<String> = text("title")
    val note: Column<String> = text("note")
}