package io.haskins.staffmanagement

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.window.application
import io.haskins.staffmanagement.ui.components.TitleBarView
import io.haskins.staffmanagement.ui.components.twoColumnLayout
import org.jetbrains.exposed.sql.Database
import org.jetbrains.jewel.foundation.theme.JewelTheme
import org.jetbrains.jewel.intui.standalone.Inter
import org.jetbrains.jewel.intui.standalone.theme.IntUiTheme
import org.jetbrains.jewel.intui.standalone.theme.createDefaultTextStyle
import org.jetbrains.jewel.intui.standalone.theme.lightThemeDefinition
import org.jetbrains.jewel.intui.window.decoratedWindow
import org.jetbrains.jewel.intui.window.styling.light
import org.jetbrains.jewel.ui.ComponentStyling
import org.jetbrains.jewel.window.DecoratedWindow
import org.jetbrains.jewel.window.styling.TitleBarStyle

fun main() = application {

    Database.connect("jdbc:postgresql://localhost:5432/postgres", driver = "org.postgresql.Driver", user = "postgres", password = "postgres")

    val textStyle = JewelTheme.createDefaultTextStyle(fontFamily = FontFamily.Inter)

    val themeDefinition = JewelTheme.lightThemeDefinition(defaultTextStyle = textStyle)

    IntUiTheme(
        themeDefinition,
        ComponentStyling.decoratedWindow(
            titleBarStyle = TitleBarStyle.light()
        )
    ) {
        DecoratedWindow(
            onCloseRequest = { exitApplication() },
            title = "Staff Management",
        ) {
//            TitleBarView()
            twoColumnLayout()
        }
    }
}
