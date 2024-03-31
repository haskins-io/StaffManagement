package io.haskins.staffmanagement.ui.detail.project.overview

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import io.github.windedge.table.DataTable
import io.haskins.staffmanagement.models.dao.Project
import io.haskins.staffmanagement.ui.components.buttonrows.EditButtonRow
import io.haskins.staffmanagement.ui.components.project.ProjectPriorityView
import io.haskins.staffmanagement.ui.components.project.ProjectStatusView
import io.haskins.staffmanagement.utils.FormatUtils
import org.jetbrains.jewel.ui.component.Text

@Composable
fun ColumnScope.ViewProjectOverview(project: Project, editing: MutableState<Boolean>) {

    EditButtonRow("Edit Project", editing)

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
            cell { Text(text = FormatUtils.formatCurrency(project.budget)) }
        }

        row(modifier = Modifier) {
            cell { Text(text = "Cost") }
            cell { Text(text = FormatUtils.formatCurrency(project.cost)) }
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