package io.haskins.staffmanagement.dao.models

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Rates: Table() {
    val id: Column<Int> = integer("rate_id").autoIncrement()
    val name : Column<String> = varchar("name", length = 100)
    val hourly: Column<Int> = integer("hour_rate")
    val daily: Column<Int> = integer("day_rate")
    val weekly: Column<Int> = integer("week_rate")
    val monthly: Column<Int> = integer("month_rate")
    val annually: Column<Int> = integer("year_rate")

    override val primaryKey = PrimaryKey(id, name="rates_pk")
}