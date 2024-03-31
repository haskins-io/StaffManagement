package io.haskins.staffmanagement.models.dao

import java.time.LocalDate

class Holiday(
    var id: Int,
    var employeeId: Int,
    var start: LocalDate,
    var end: LocalDate)