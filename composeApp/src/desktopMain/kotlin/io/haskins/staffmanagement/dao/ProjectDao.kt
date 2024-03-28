package io.haskins.staffmanagement.dao

import io.haskins.staffmanagement.dao.models.Employees
import io.haskins.staffmanagement.dao.models.ProjectEmployees
import io.haskins.staffmanagement.dao.models.Projects
import io.haskins.staffmanagement.enums.FilterType
import io.haskins.staffmanagement.models.ListItem
import io.haskins.staffmanagement.models.ProjectEmployee
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class ProjectDao private constructor() {

    companion object {

        @Volatile
        private var instance: ProjectDao? = null

        fun getInstance() = instance ?: synchronized(this) {
            instance ?: ProjectDao().also { instance = it}
        }
    }

    fun projects(): List<ListItem> {

        val projects = mutableListOf<ListItem>()

        transaction {
            for (project in Projects.selectAll().orderBy(Projects.name)) {

                val item = ListItem(
                    project[Projects.id],
                    project[Projects.name],
                    FilterType.Projects.id
                )

                projects.add(item)
            }
        }

        return projects
    }

    fun projectResources(id: Int): List<ProjectEmployee> {

        val employees = mutableListOf<ProjectEmployee>()

        transaction {

            val tmp = (ProjectEmployees innerJoin Employees)
                .select(Employees.id, Employees.name, ProjectEmployees.allocation, ProjectEmployees.cost, ProjectEmployees.id)
                .where {
                    ProjectEmployees.projectId.eq(id)
                }.toList()

            for (t in tmp) {
                val employee = ProjectEmployee(
                    t[ProjectEmployees.id],
                    t[Employees.name],
                    t[ProjectEmployees.allocation],
                    t[ProjectEmployees.cost]
                )

                employees.add(employee)
            }
        }

        return employees
    }

    fun allocateResource(projectId: Int, employeeId: Int, allocationPerc: Int) {

        transaction {

            ProjectEmployees.insert {
                it[Employees.id] = employeeId
                it[Projects.id] = projectId
                it[allocation] = allocationPerc
                it[cost] = 0f
            }
        }
    }

    fun removeResource(allocationId: Int) {
        transaction {
            ProjectEmployees.deleteWhere { id eq allocationId }
        }
    }

    fun updateResource(allocationId: Int,  allocationPerc: Int) {
        transaction {
            ProjectEmployees.update({ ProjectEmployees.id eq allocationId }) {
                it[allocation] = allocationPerc
            }
        }
    }
}