package io.haskins.staffmanagement.models.dao

class Employee(
    val id: Int,
    val name: String,
    val managerId: Int,
    val departmentId: Int,
    val rate: Int,
    var isManager: Boolean
)