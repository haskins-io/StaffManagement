package io.haskins.staffmanagement.models

import java.time.LocalDate

class Project(
    val id: Int,
    val name: String,
    val description: String,
    val code: String,
    val budget: Int,
    val status: Int,
    val priority: Int,
    val due: LocalDate,
    val progress: Int,
)