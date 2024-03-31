package io.haskins.staffmanagement.ui.components.project

import androidx.compose.runtime.Composable
import io.haskins.staffmanagement.enums.ProjectPriority
import org.jetbrains.jewel.ui.component.Text

@Composable
fun ProjectPriorityView(priority: Int) {
    when(priority) {
        ProjectPriority.NotSet.id -> Text("Not Set")
        ProjectPriority.Now.id -> Text("Now")
        ProjectPriority.Next.id -> Text("Next")
        ProjectPriority.Future.id -> Text("Future")
    }
}