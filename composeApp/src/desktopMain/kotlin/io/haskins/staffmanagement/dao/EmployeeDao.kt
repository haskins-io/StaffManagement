package io.haskins.staffmanagement.dao

import io.haskins.staffmanagement.dao.models.*
import io.haskins.staffmanagement.enums.FilterType
import io.haskins.staffmanagement.models.ListItem
import io.haskins.staffmanagement.models.dao.Employee
import io.haskins.staffmanagement.models.dao.Holiday
import io.haskins.staffmanagement.models.dao.Note
import io.haskins.staffmanagement.models.dao.ProjectResource
import io.haskins.staffmanagement.utils.DateUtils
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

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

    fun employee(employeeId: Int): Employee {

        var employee = Employee (
            0,
            "",
            0,
            0,
            0,
            false
        )

        transaction {

            val t = (Employees innerJoin Rates)
                .selectAll()
                .where { Employees.id.eq(employeeId) }
                .single()

            employee = Employee (
                t[Employees.id],
                t[Employees.name],
                t[Employees.managerId],
                t[Employees.departmentId],
                t[Rates.daily],
                t[Employees.isManager]
            )
        }

        return employee
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///// Projects
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    fun projects(employeeId: Int): List<ProjectResource> {

        val projects = mutableListOf<ProjectResource>()

        transaction {

            val tmp = (Projects innerJoin ProjectResources)
                .select(Projects.name, ProjectResources.allocation, ProjectResources.cost, ProjectResources.id)
                .where {
                    ProjectResources.employeeId.eq(employeeId)
                }.toList()

            for (t in tmp) {
                val employee = ProjectResource(
                    t[ProjectResources.id],
                    0,
                    0,
                    t[Projects.name],
                    0,
                    "",
                    t[ProjectResources.allocation],
                    t[ProjectResources.cost],
                    DateUtils.epochToLocalDate(0),
                    DateUtils.epochToLocalDate(0)
                )

                projects.add(employee)
            }
        }

        return projects
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///// Notes
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    fun notes(id: Int): List<Note> {

        val notes = mutableListOf<Note>()

        transaction {

            val tmp = (EmployeeNotes innerJoin Notes)
                .selectAll()
                .where {
                    EmployeeNotes.employeeId.eq(id)
                }
                .orderBy(Notes.date, order = SortOrder.ASC)
                .toList()

            for (t in tmp) {

                val note = Note(
                    t[Notes.id].value,
                    t[Notes.title],
                    t[Notes.note],
                    DateUtils.epochToLocalDate(t[Notes.date]),
                )

                notes.add(note)
            }
        }

        return notes
    }

    fun addNote(employeeId: Int, title: String, note: String) {

        transaction {

            val noteId = Notes.insertAndGetId {
                it[Notes.title] = title
                it[Notes.note] = note
                DateUtils.localDateToEpoch(LocalDate.now())
            }

            EmployeeNotes.insert {
                it[EmployeeNotes.employeeId] = employeeId
                it[EmployeeNotes.noteId] = noteId.value
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///// Holidays
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    fun holidays(employeeId: Int): List<Holiday> {

        val holidays = mutableListOf<Holiday>()

        transaction {

            val tmp = EmployeeHolidays
                .selectAll()
                .where {
                    EmployeeHolidays.employeeId.eq(employeeId)
                }
                .orderBy(EmployeeHolidays.start)
                .toList()

            for (t in tmp) {

                val holiday = Holiday(
                    t[EmployeeHolidays.id],
                    t[EmployeeHolidays.employeeId],
                    DateUtils.epochToLocalDate(t[EmployeeHolidays.start]),
                    DateUtils.epochToLocalDate(t[EmployeeHolidays.end]),
                )

                holidays.add(holiday)
            }
        }

        return holidays
    }

    fun addHoliday(employeeId: Int, start: Long, end: Long) {

        transaction {

            EmployeeHolidays.insert {
                it[EmployeeHolidays.employeeId] = employeeId
                it[EmployeeHolidays.start] = start
                it[EmployeeHolidays.end] = end
            }
        }
    }
}