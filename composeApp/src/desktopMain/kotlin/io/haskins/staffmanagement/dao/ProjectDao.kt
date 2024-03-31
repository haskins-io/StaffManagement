package io.haskins.staffmanagement.dao

import io.haskins.staffmanagement.dao.models.*
import io.haskins.staffmanagement.enums.FilterType
import io.haskins.staffmanagement.models.ListItem
import io.haskins.staffmanagement.models.dao.Note
import io.haskins.staffmanagement.models.dao.Project
import io.haskins.staffmanagement.models.dao.ProjectResource
import io.haskins.staffmanagement.utils.DateUtils
import java.time.LocalDate
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset

class ProjectDao private constructor() {

    companion object {

        @Volatile
        private var instance: ProjectDao? = null

        fun getInstance() = instance ?: synchronized(this) {
            instance ?: ProjectDao().also { instance = it}
        }
    }

    fun projectsList(): List<ListItem> {

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

    fun projects(): List<Project> {

        val projects = mutableListOf<Project>()

        transaction {

            for (t in Projects.selectAll().orderBy(Projects.name)) {

                val instant = Instant.ofEpochMilli(t[Projects.due].toLong())
                val zoneId = ZoneId.systemDefault()

                val project = Project(
                    t[Projects.id],
                    t[Projects.name],
                    t[Projects.description],
                    t[Projects.code],
                    t[Projects.budget],
                    t[Projects.cost],
                    t[Projects.status],
                    t[Projects.priority],
                    instant.atZone(zoneId).toLocalDate(),
                    t[Projects.progress],
                )

                projects.add(project)
            }
        }

        return projects
    }

    fun project(id: Int): Project {

        var project= Project(
            0,
            "",
            "",
            "",
            0,
            0,
            0,
            0,
            LocalDate.now(),
            0
        )

        transaction {
            val t = Projects.selectAll().where { Projects.id.eq(id) }.single()

            project = Project(
                t[Projects.id],
                t[Projects.name],
                t[Projects.description],
                t[Projects.code],
                t[Projects.budget],
                t[Projects.cost],
                t[Projects.status],
                t[Projects.priority],
                DateUtils.epochToLocalDate(t[Projects.due]),
                t[Projects.progress],
            )
        }

        return project
    }

    fun update(project: Project) {

        transaction {

            Projects.update( { Projects.id eq project.id } ) {
                it[name] = project.name
                it[description] = project.description
                it[code] = project.code
                it[budget] = project.budget
                it[status] = project.status
                it[priority] = project.priority
                it[due] = DateUtils.localDateToEpoch(project.due)
                it[progress] = project.progress
            }
        }
    }

    fun resources(id: Int): List<ProjectResource> {

        val resources = mutableListOf<ProjectResource>()

        transaction {

            val tmp = (ProjectResources innerJoin Employees)
                .select(Employees.id, Employees.name, ProjectResources.allocation, ProjectResources.cost, ProjectResources.id)
                .where {
                    ProjectResources.projectId.eq(id)
                }
                .orderBy(Employees.name)
                .toList()

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
                val instant = Instant.ofEpochMilli(t[ProjectNotes.date].toLong())
                val zoneId = ZoneId.systemDefault()

                val employee = Note(
                    t[ProjectNotes.id],
                    t[ProjectNotes.title],
                    t[ProjectNotes.note],
                    instant.atZone(zoneId).toLocalDate(),
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