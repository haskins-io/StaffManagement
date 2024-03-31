package io.haskins.staffmanagement.ui.components.project

import androidx.compose.runtime.Composable
import io.haskins.staffmanagement.enums.ProjectStatus
import org.jetbrains.jewel.ui.component.Text

@Composable
fun ProjectStatusView(status: Int) {
    when(status){
        ProjectStatus.NotSet.id -> Text("Not Set")
        ProjectStatus.Red.id -> Text("Red")
        ProjectStatus.Amber.id -> Text("Amber")
        ProjectStatus.Green.id -> Text("Green")
    }
}