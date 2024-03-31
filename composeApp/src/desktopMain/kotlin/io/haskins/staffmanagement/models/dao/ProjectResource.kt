package io.haskins.staffmanagement.models.dao

import java.time.LocalDate

class ProjectResource(
    val id: Int,

    val projectId: Int,

    val employeeId: Int,
    val name: String,

    val rateId: Int,
    val rateName: String,

    val allocation: Int,
    val cost: Float,

    val start: LocalDate,
    val end: LocalDate,
)