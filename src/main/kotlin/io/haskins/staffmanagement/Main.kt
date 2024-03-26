package io.haskins.staffmanagement

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.haskins.staffmanagement.dao.models.Departments
import io.haskins.staffmanagement.dao.models.Managers
import io.haskins.staffmanagement.enums.FilterType
import io.haskins.staffmanagement.models.ListItem
import io.haskins.staffmanagement.ui.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

// https://github.com/JetBrains/compose-multiplatform/blob/master/examples/issues/common/src/jvmAndAndroidMain/kotlin/androidx/ui/examples/jetissues/view/JetIssuesView.kt#L50

fun main() = application {

    Database.connect("jdbc:postgresql://localhost:5432/postgres", driver="org.postgresql.Driver", user="postgres", password = "postgres")

    transaction {

        val result = (Managers innerJoin Departments)
            .select(Managers.id, Managers.name, Departments.name)
            .where { Departments.id.eq(1) }

        for (manager in result) {
            println("${manager[Managers.id]}, ${manager[Managers.name]}, ${manager[Departments.name]}")
        }
    }

    Window(onCloseRequest = ::exitApplication) {
        window()
    }
}
