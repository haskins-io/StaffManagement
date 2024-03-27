package io.haskins.staffmanagement.dao

import io.haskins.staffmanagement.dao.models.Employees
import io.haskins.staffmanagement.enums.FilterType
import io.haskins.staffmanagement.models.ListItem
import org.jetbrains.exposed.sql.and
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

    fun allManagers(): List<ListItem> {

        val managers = mutableListOf<ListItem>()

        transaction {

            val tmp = Employees
                .selectAll()
                .where {
                    Employees.departmentId.eq(Employees.managerId)
                }.toList()

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
}