package io.haskins.staffmanagement

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.haskins.staffmanagement.ui.window
import org.jetbrains.exposed.sql.Database

// https://github.com/JetBrains/compose-multiplatform/tree/master/examples/todoapp-lite

fun main() = application {

    Database.connect("jdbc:postgresql://localhost:5432/postgres", driver="org.postgresql.Driver", user="postgres", password = "postgres")

    Window(onCloseRequest = ::exitApplication) {
        window()
    }
}
