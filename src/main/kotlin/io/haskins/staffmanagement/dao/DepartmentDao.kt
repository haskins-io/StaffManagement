package io.haskins.staffmanagement.dao

import io.haskins.staffmanagement.dao.models.Departments
import io.haskins.staffmanagement.enums.FilterType
import io.haskins.staffmanagement.models.ListItem
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

    fun allDepartments(): List<ListItem> {

        val departments = mutableListOf<ListItem>()

        transaction {

            for (department in Departments.selectAll()) {

                val item = ListItem(
                    department[Departments.id],
                    department[Departments.name],
                    FilterType.Departments.id
                )

                departments.add(item)
            }
        }

        return departments
    }
}