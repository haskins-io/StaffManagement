package io.haskins.staffmanagement.dao

import io.haskins.staffmanagement.dao.models.Employees
import io.haskins.staffmanagement.dao.models.ProjectResources
import io.haskins.staffmanagement.dao.models.Projects
import io.haskins.staffmanagement.enums.FilterType
import io.haskins.staffmanagement.models.ListItem
import io.haskins.staffmanagement.models.ProjectResource
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

    fun projects(id: Int): List<ProjectResource> {

        val projects = mutableListOf<ProjectResource>()

        transaction {

            val tmp = (Projects innerJoin ProjectResources)
                .select(Projects.name, ProjectResources.allocation, ProjectResources.cost, ProjectResources.id)
                .where {
                    ProjectResources.employeeId.eq(id)
                }.toList()

            for (t in tmp) {
                val employee = ProjectResource(
                    t[ProjectResources.id],
                    t[Projects.name],
                    t[ProjectResources.allocation],
                    t[ProjectResources.cost]
                )

                projects.add(employee)
            }
        }

        return projects
    }

}