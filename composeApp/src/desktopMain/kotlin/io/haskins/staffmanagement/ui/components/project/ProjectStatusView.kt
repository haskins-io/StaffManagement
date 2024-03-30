package io.haskins.staffmanagement.ui.components.project

import androidx.compose.runtime.Composable
import io.haskins.staffmanagement.enums.ProjectStatus
import org.jetbrains.jewel.ui.component.Text

@Composable
fun ProjectStatusView(status: Int) {
    when(status){
        ProjectStatus.Red.status -> Text("Red")
        ProjectStatus.Amber.status -> Text("Amber")
        ProjectStatus.Green.status -> Text("Green")
    }
}