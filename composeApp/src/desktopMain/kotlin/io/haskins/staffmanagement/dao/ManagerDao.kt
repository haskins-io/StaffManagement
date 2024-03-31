package io.haskins.staffmanagement.dao

import io.haskins.staffmanagement.dao.models.Employees
import io.haskins.staffmanagement.enums.FilterType
import io.haskins.staffmanagement.models.dao.Employee
import io.haskins.staffmanagement.models.ListItem
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class ManagerDao private constructor(){

    companion object {

        @Volatile
        private var instance: ManagerDao? = null

        fun getInstance() = instance ?: synchronized(this) {
            instance ?: ManagerDao().also { instance = it}
        }
    }

    fun managers(): List<ListItem> {

        val managers = mutableListOf<ListItem>()

        transaction {

            val tmp = Employees
                .selectAll()
                .where {
                    Employees.isManager eq true
                }
                .orderBy(Employees.name)
                .toList()

            for (manager in tmp) {

                val item = ListItem(
                    manager[Employees.id],
                    manager[Employees.name],
                    FilterType.Managers.id
                )

                managers.add(item)
            }
        }

        return managers
    }

    fun managerReports(managerId: Int): List<Employee> {

        val employees = mutableListOf<Employee>()

        transaction {
            val tmp = Employees
                .selectAll()
                .where { Employees.managerId eq managerId }
                .orderBy(Employees.name)
                .toList()
            for (t in tmp) {
                val item = Employee(
                    t[Employees.id],
                    t[Employees.name],
                    t[Employees.managerId],
                    t[Employees.departmentId],
                    t[Employees.rateId],
                    t[Employees.isManager]
                )

                employees.add(item)
            }
        }

        return employees
    }
}