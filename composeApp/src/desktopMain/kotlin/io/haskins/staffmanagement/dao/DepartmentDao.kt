package io.haskins.staffmanagement.dao

import io.haskins.staffmanagement.dao.models.Departments
import io.haskins.staffmanagement.dao.models.Employees
import io.haskins.staffmanagement.enums.FilterType
import io.haskins.staffmanagement.models.Employee
import io.haskins.staffmanagement.models.ListItem
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

    fun departments(): List<ListItem> {

        val departments = mutableListOf<ListItem>()

        transaction {

            val tmp = Departments
                .selectAll()
                .orderBy(Departments.name)
                .toList()

            for (department in tmp) {

                val item = ListItem(
                    department[Departments.id],
                    department[Departments.name],
                    FilterType.Departments.id
                )

                departments.add(item)
            }
        }

        return departments
    }

    fun departmentHeads(): List<ListItem> {

        val departments = mutableListOf<ListItem>()

        transaction {

            val tmp = Departments
                .selectAll()
                .orderBy(Departments.name)
                .toList()

            for (department in tmp) {

                val item = ListItem(
                    department[Departments.head],
                    department[Departments.name],
                    FilterType.Departments.id
                )

                departments.add(item)
            }
        }

        return departments
    }

    fun departmentEmployees(departmentId: Int): List<Employee> {

        val employees = mutableListOf<Employee>()

        transaction {
            val tmp = Employees
                .selectAll()
                .where { Employees.departmentId eq departmentId }
                .orderBy(Employees.name)
                .toList()

            for (t in tmp) {
                val item = Employee(
                    t[Employees.id],
                    t[Employees.name],
                    t[Employees.managerId],
                    t[Employees.departmentId],
                    t[Employees.rateId],
                    t[Employees.isManager]
                )

                employees.add(item)
            }
        }

        return employees
    }
}