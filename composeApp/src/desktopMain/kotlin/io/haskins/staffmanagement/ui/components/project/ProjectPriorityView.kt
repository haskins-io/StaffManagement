package io.haskins.staffmanagement.ui.components.project

import androidx.compose.runtime.Composable
import io.haskins.staffmanagement.enums.ProjectPriority
import org.jetbrains.jewel.ui.component.Text

@Composable
fun ProjectPriorityView(priority: Int) {
    when(priority){
        ProjectPriority.Now.priority -> Text("Now")
        ProjectPriority.Next.priority -> Text("Next")
        ProjectPriority.Future.priority -> Text("Future")
    }
}