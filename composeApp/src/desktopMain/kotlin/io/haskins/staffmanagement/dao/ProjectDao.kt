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

class ProjectDao private constructor() {

    companion object {

        @Volatile
        private var instance: ProjectDao? = null

        fun getInstance() = instance ?: synchronized(this) {
            instance ?: ProjectDao().also { instance = it}
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///// Projects
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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

        var project = Project(
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

    fun create(project: Project) {

        transaction {
            Projects.insert {
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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///// Resources
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    fun resources(id: Int): List<ProjectResource> {

        val resources = mutableListOf<ProjectResource>()

        transaction {

            val tmp = (ProjectResources innerJoin Employees innerJoin Rates)
                .selectAll()
                .where {
                    ProjectResources.projectId.eq(id)
                }
                .orderBy(Employees.name)
                .toList()

            for (t in tmp) {
                val employee = ProjectResource(
                    t[ProjectResources.id],
                    t[ProjectResources.projectId],
                    t[Employees.id],
                    t[Employees.name],
                    t[Employees.rateId],
                    t[Rates.name],
                    t[ProjectResources.allocation],
                    t[ProjectResources.cost],
                    DateUtils.epochToLocalDate(t[ProjectResources.start]),
                    DateUtils.epochToLocalDate(t[ProjectResources.end]),
                )

                resources.add(employee)
            }
        }

        return resources
    }

    fun allocateResource(projectId: Int, employeeId: Int, allocationPerc: Int) {

        transaction {

            val employeeCost = employeeCost(employeeId, allocationPerc)

            ProjectResources.insert {
                it[Employees.id] = employeeId
                it[Projects.id] = projectId
                it[allocation] = allocationPerc
                it[cost] = employeeCost
            }

            updateProjectCost(projectId)
        }
    }

    fun removeResource(allocationId: Int) {
        transaction {
            ProjectResources.deleteWhere { id eq allocationId }
        }
    }

    fun updateResource(resource: ProjectResource,  allocationPerc: Int) {

        transaction {

            // calculate employee cost based on allocation
            val employeeCost = employeeCost(resource.employeeId, allocationPerc)

            ProjectResources.update({ ProjectResources.id eq resource.id }) {
                it[allocation] = allocationPerc
                it[cost] = employeeCost
            }

            // now get all resources for project and sum up cost
            updateProjectCost(resource.projectId)
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///// Notes
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    fun notes(id: Int): List<Note> {

        val notes = mutableListOf<Note>()

        transaction {

            val tmp = (ProjectNotes innerJoin Notes)
                .selectAll()
                .where {
                    ProjectNotes.projectId.eq(id)
                }
                .orderBy(Notes.date, order = SortOrder.ASC)
                .toList()

            for (t in tmp) {
                val instant = Instant.ofEpochMilli(t[Notes.date].toLong())
                val zoneId = ZoneId.systemDefault()

                val employee = Note(
                    t[Notes.id].value,
                    t[Notes.title],
                    t[Notes.note],
                    instant.atZone(zoneId).toLocalDate(),
                )

                notes.add(employee)
            }
        }

        return notes
    }

    fun addNote(projectId: Int, title: String, note: String) {

        transaction {

            val noteId = Notes.insertAndGetId {
                it[Notes.title] = title
                it[Notes.note] = note
                DateUtils.localDateToEpoch(LocalDate.now())
            }

            ProjectNotes.insert {
                it[ProjectNotes.projectId] = projectId
                it[ProjectNotes.noteId] = noteId.value
            }
        }
    }

    private fun employeeCost(employeeId: Int, allocation: Int): Float {

        val employee = EmployeeDao.getInstance().employee(employeeId)
        val rate: Float = (employee.rate.toFloat()) / 100f
        return rate * allocation
    }

    private fun updateProjectCost(projectId: Int) {

        var projectCost = 0f
        val resources = resources(projectId)
        for (r in resources) {
            projectCost += r.cost
        }

        transaction {

            Projects.update( { Projects.id eq projectId } ) {
                it[cost] = projectCost.toInt()
            }
        }
    }
}