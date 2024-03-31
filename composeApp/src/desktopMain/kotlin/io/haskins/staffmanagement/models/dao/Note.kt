package io.haskins.staffmanagement.models.dao

import java.time.LocalDate

class Note(
    val id: Int,
    val title: String,
    val note: String,
    val created: LocalDate
)