package io.haskins.staffmanagement.dao

import io.haskins.staffmanagement.dao.models.Employees
import io.haskins.staffmanagement.enums.FilterType
import io.haskins.staffmanagement.models.Employee
import io.haskins.staffmanagement.models.ListItem
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class EmployeeDao private constructor() {

    companion object {

        @Volatile
        private var instance: EmployeeDao? = null

        fun getInstance() = instance ?: synchronized(this) {
            instance ?: EmployeeDao().also { instance = it}
        }
    }

    fun allEmployees(): List<ListItem> {

        val employees = mutableListOf<ListItem>()

        transaction {
            for (employee in Employees.selectAll()) {

                val item = ListItem(
                    employee[Employees.id],
                    employee[Employees.name],
                    FilterType.Employees.id
                )

                employees.add(item)
            }
        }

        return employees
    }

    fun employeesForDepartment(departmentId: Int): List<Employee> {

        var employees = mutableListOf<Employee>()

        transaction {
            val tmp = Employees.selectAll().where { Employees.departmentId eq departmentId }.toList()
            for (t in tmp) {
                val item = Employee(
                    t[Employees.id],
                    t[Employees.name],
                    t[Employees.managerId],
                    t[Employees.departmentId],
                    t[Employees.rateId],
                )

                employees.add(item)
            }
        }

        return employees
    }

    fun employeesForManager(managerId: Int): List<Employee> {

        var employees = mutableListOf<Employee>()

        transaction {
            val tmp = Employees.selectAll().where { Employees.managerId eq managerId }.toList()
            for (t in tmp) {
                val item = Employee(
                    t[Employees.id],
                    t[Employees.name],
                    t[Employees.managerId],
                    t[Employees.departmentId],
                    t[Employees.rateId],
                )

                employees.add(item)
            }
        }

        return employees
    }
}