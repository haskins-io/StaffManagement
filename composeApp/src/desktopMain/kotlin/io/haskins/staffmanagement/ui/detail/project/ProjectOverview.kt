package io.haskins.staffmanagement.ui.detail.project

import org.jetbrains.jewel.ui.component.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import io.github.windedge.table.DataTable
import io.haskins.staffmanagement.dao.ProjectDao
import io.haskins.staffmanagement.models.ListItem
import io.haskins.staffmanagement.ui.components.project.ProjectPriorityView
import io.haskins.staffmanagement.ui.components.project.ProjectStatusView
import io.haskins.staffmanagement.ui.detail.project.resources.EditResourceButtons
import io.haskins.staffmanagement.ui.detail.project.resources.ViewResourceButtons
import org.jetbrains.jewel.ui.component.TextField

@Composable
fun projectOverview(currentDetail: MutableState<ListItem>) {

    val project = ProjectDao.getInstance().project(currentDetail.value.id)

    DataTable(
        columns = {
            column { Text("") }
            column { Text("") }
        }
    ) {

        row(modifier = Modifier) {
            cell { Text(text = "Name") }
            cell { Text(text = project.name) }
        }

        row(modifier = Modifier) {
            cell { Text(text = "Description") }
            cell { Text(text = project.description) }
        }

        row(modifier = Modifier) {
            cell { Text(text = "Code") }
            cell { Text(text = project.code) }
        }

        row(modifier = Modifier) {
            cell { Text(text = "Budget") }
            cell { Text(text = project.budget.toString()) }
        }

        row(modifier = Modifier) {
            cell { Text(text = "Status") }
            cell { ProjectStatusView(project.status) }
        }

        row(modifier = Modifier) {
            cell { Text(text = "priority") }
            cell { ProjectPriorityView(project.priority) }
        }

        row(modifier = Modifier) {
            cell { Text(text = "Due") }
            cell { Text(text = project.due.toString()) }
        }

        row(modifier = Modifier) {
            cell { Text(text = "Progress") }
            cell { Text(text = project.progress.toString()) }
        }
    }
}