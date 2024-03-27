package io.haskins.staffmanagement.dao

import io.haskins.staffmanagement.dao.models.Employees
import io.haskins.staffmanagement.dao.models.ProjectEmployees
import io.haskins.staffmanagement.dao.models.Projects
import io.haskins.staffmanagement.enums.FilterType
import io.haskins.staffmanagement.models.Employee
import io.haskins.staffmanagement.models.ListItem
import io.haskins.staffmanagement.models.Project
import io.haskins.staffmanagement.models.ProjectEmployee
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

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

    fun project(id: Int): Project {

        var project = Project(0, "","")

        transaction {
            val tmp = Projects.selectAll().where { Projects.id eq id }
            for (t in tmp) {
                project = Project(
                    t[Projects.id],
                    t[Projects.name],
                    t[Projects.code]
                )
            }
        }

        return project
    }

    fun projectResources(id: Int): List<ProjectEmployee> {

        val employees = mutableListOf<ProjectEmployee>()

        transaction {

            val tmp = (ProjectEmployees innerJoin Employees)
                .select(Employees.id, Employees.name, ProjectEmployees.allocation, ProjectEmployees.cost)
                .where {
                    ProjectEmployees.projectId.eq(id)
                }.toList()

            for (t in tmp) {
                val employee = ProjectEmployee(
                    t[Employees.name],
                    t[ProjectEmployees.allocation],
                    t[ProjectEmployees.cost]
                )

                employees.add(employee)
            }
        }

        return employees
    }
}