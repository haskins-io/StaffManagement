package io.haskins.staffmanagement.dao

import io.haskins.staffmanagement.dao.models.Employees
import io.haskins.staffmanagement.dao.models.ProjectEmployees
import io.haskins.staffmanagement.dao.models.Projects
import io.haskins.staffmanagement.enums.FilterType
import io.haskins.staffmanagement.models.Employee
import io.haskins.staffmanagement.models.ListItem
import io.haskins.staffmanagement.models.ProjectEmployee
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

    fun employees(): List<ListItem> {

        val employees = mutableListOf<ListItem>()

        transaction {
            for (employee in Employees.selectAll().orderBy(Employees.name)) {

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

    fun projects(id: Int): List<ProjectEmployee> {

        val projects = mutableListOf<ProjectEmployee>()

        transaction {

            val tmp = (Projects innerJoin ProjectEmployees)
                .select(Projects.name, ProjectEmployees.allocation, ProjectEmployees.cost, ProjectEmployees.id)
                .where {
                    ProjectEmployees.employeeId.eq(id)
                }.toList()

            for (t in tmp) {
                val employee = ProjectEmployee(
                    t[ProjectEmployees.id],
                    t[Projects.name],
                    t[ProjectEmployees.allocation],
                    t[ProjectEmployees.cost]
                )

                projects.add(employee)
            }
        }

        return projects
    }

}