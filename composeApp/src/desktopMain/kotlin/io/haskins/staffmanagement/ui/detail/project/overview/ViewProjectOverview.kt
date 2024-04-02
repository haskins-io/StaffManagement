package io.haskins.staffmanagement.ui.detail.project.overview

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import io.github.windedge.table.DataTable
import io.haskins.staffmanagement.models.dao.Project
import io.haskins.staffmanagement.ui.components.TableHeader
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
            cell { TableHeader("Name") }
            cell { Text(text = project.name) }
        }

        row(modifier = Modifier) {
            cell { TableHeader( "Description") }
            cell { Text(text = project.description) }
        }

        row(modifier = Modifier) {
            cell { TableHeader( "Code") }
            cell { Text(text = project.code) }
        }

        row(modifier = Modifier) {
            cell { TableHeader( "Budget") }
            cell { Text(text = FormatUtils.formatCurrency(project.budget)) }
        }

        row(modifier = Modifier) {
            cell { TableHeader( "Cost") }
            cell { Text(text = FormatUtils.formatCurrency(project.cost)) }
        }

        row(modifier = Modifier) {
            cell { TableHeader( "Status") }
            cell { ProjectStatusView(project.status) }
        }

        row(modifier = Modifier) {
            cell { TableHeader( "priority") }
            cell { ProjectPriorityView(project.priority) }
        }

        row(modifier = Modifier) {
            cell { TableHeader( "Due") }
            cell { Text(text = project.due.toString()) }
        }

        row(modifier = Modifier) {
            cell { TableHeader( "Progress") }
            cell { Text(text = project.progress.toString()) }
        }
    }
}