package io.haskins.staffmanagement.models.dao

import java.time.LocalDate

class Project(
    val id: Int,
    val name: String,
    val description: String,
    val code: String,
    val budget: Int,
    val cost: Int,
    val status: Int,
    val priority: Int,
    val due: LocalDate,
    val progress: Int,
)