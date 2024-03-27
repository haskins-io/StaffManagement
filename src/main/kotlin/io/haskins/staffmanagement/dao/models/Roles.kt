package io.haskins.staffmanagement.dao.models

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Roles: Table() {
    val id: Column<Int> = integer("role_id").autoIncrement()
    val name : Column<String> = varchar("name", length = 100)

    override val primaryKey = PrimaryKey(id, name="roles_pk")
}