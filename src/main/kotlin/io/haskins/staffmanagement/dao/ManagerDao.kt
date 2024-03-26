package io.haskins.staffmanagement.dao

import io.haskins.staffmanagement.dao.models.Managers
import io.haskins.staffmanagement.enums.FilterType
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

    fun allManagers(): List<ListItem> {

        val managers = mutableListOf<ListItem>()

        transaction {
            for (manager in Managers.selectAll()) {

                val item = ListItem(
                    manager[Managers.id],
                    manager[Managers.name],
                    FilterType.Managers.id
                )

                managers.add(item)
            }
        }

        return managers
    }
}