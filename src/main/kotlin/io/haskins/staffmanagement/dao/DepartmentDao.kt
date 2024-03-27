package io.haskins.staffmanagement.dao

import io.haskins.staffmanagement.dao.models.Employees
import io.haskins.staffmanagement.enums.FilterType
import io.haskins.staffmanagement.models.ListItem
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class DepartmentDao private constructor() {

    companion object {

        @Volatile
        private var instance: DepartmentDao? = null

        fun getInstance() = instance ?: synchronized(this) {
            instance ?: DepartmentDao().also { instance = it}
        }
    }

    fun allDepartments(): List<ListItem> {

        val departments = mutableListOf<ListItem>()

        transaction {

            val tmp = Employees
                .selectAll()
                .where {
                    Employees.departmentId.eq(0) and Employees.managerId.eq(0)
            }.toList()
            for (department in tmp) {

                val item = ListItem(
                    department[Employees.id],
                    department[Employees.name],
                    FilterType.Departments.id
                )

                departments.add(item)
            }
        }

        return departments
    }
}