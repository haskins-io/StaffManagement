package io.haskins.staffmanagement.dao

import io.haskins.staffmanagement.dao.models.Departments
import io.haskins.staffmanagement.dao.models.Employees
import io.haskins.staffmanagement.enums.FilterType
import io.haskins.staffmanagement.models.dao.Employee
import io.haskins.staffmanagement.models.ListItem
import io.haskins.staffmanagement.models.dao.Department
import org.jetbrains.exposed.sql.insert
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


    fun departments(): List<Department> {

        val departments = mutableListOf<Department>()

        transaction {

            val tmp = (Departments innerJoin Employees)
                .selectAll()
                .orderBy(Departments.name)
                .toList()

            for (department in tmp) {

                val item = Department(
                    department[Departments.id],
                    department[Departments.name],
                    department[Employees.id],
                    department[Employees.name],
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

    fun create(name: String, head: Int) {
        transaction {
            Departments.insert {
                it[Departments.name] = name
                it[Departments.head] = head
            }
        }
    }
}