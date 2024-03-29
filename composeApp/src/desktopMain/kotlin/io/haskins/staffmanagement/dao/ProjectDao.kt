package io.haskins.staffmanagement.dao

import io.haskins.staffmanagement.dao.models.*
import io.haskins.staffmanagement.enums.FilterType
import io.haskins.staffmanagement.models.ListItem
import io.haskins.staffmanagement.models.Note
import io.haskins.staffmanagement.models.ProjectResource
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
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

    fun resources(id: Int): List<ProjectResource> {

        val resources = mutableListOf<ProjectResource>()

        transaction {

            val tmp = (ProjectResources innerJoin Employees)
                .select(Employees.id, Employees.name, ProjectResources.allocation, ProjectResources.cost, ProjectResources.id)
                .where {
                    ProjectResources.projectId.eq(id)
                }.toList()

            for (t in tmp) {
                val employee = ProjectResource(
                    t[ProjectResources.id],
                    t[Employees.name],
                    t[ProjectResources.allocation],
                    t[ProjectResources.cost]
                )

                resources.add(employee)
            }
        }

        return resources
    }

    fun notes(id: Int): List<Note> {

        val notes = mutableListOf<Note>()

        transaction {

            val tmp = ProjectNotes
                .selectAll()
                .where {
                    ProjectNotes.projectId.eq(id)
                }
                .orderBy(ProjectNotes.date, order = SortOrder.ASC)
                .toList()

            for (t in tmp) {
                val employee = Note(
                    t[ProjectNotes.id],
                    t[ProjectNotes.title],
                    t[ProjectNotes.note]
                )

                notes.add(employee)
            }
        }

        return notes
    }

    fun allocateResource(projectId: Int, employeeId: Int, allocationPerc: Int) {

        transaction {

            ProjectResources.insert {
                it[Employees.id] = employeeId
                it[Projects.id] = projectId
                it[allocation] = allocationPerc
                it[cost] = 0f
            }
        }
    }

    fun addNote(projectId: Int, title: String, note: String) {
        transaction {

            ProjectNotes.insert {
                it[ProjectNotes.projectId] = projectId
                it[ProjectNotes.title] = title
                it[ProjectNotes.note] = note
            }
        }
    }

    fun removeResource(allocationId: Int) {
        transaction {
            ProjectResources.deleteWhere { id eq allocationId }
        }
    }

    fun updateResource(allocationId: Int,  allocationPerc: Int) {
        transaction {
            ProjectResources.update({ ProjectResources.id eq allocationId }) {
                it[allocation] = allocationPerc
            }
        }
    }
}