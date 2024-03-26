package io.haskins.staffmanagement.models

class Employee(
    eId: Int,
    eName: String,
    eManagerId: Int,
    eDepartmentId: Int,
    eRateId: Int,
) {
    val id = eId;
    val name = eName;
    val managerId = eManagerId;
    val departmentId = eDepartmentId;
    val rateId = eRateId;
}