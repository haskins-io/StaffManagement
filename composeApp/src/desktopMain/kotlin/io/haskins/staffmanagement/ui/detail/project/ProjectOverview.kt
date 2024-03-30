package io.haskins.staffmanagement.ui.detail.project

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import io.github.windedge.table.DataTable
import io.haskins.staffmanagement.ui.components.EditButtonRow
import io.haskins.staffmanagement.dao.ProjectDao
import io.haskins.staffmanagement.models.ListItem
import io.haskins.staffmanagement.ui.components.project.ProjectPriorityView
import io.haskins.staffmanagement.ui.components.project.ProjectStatusView
import org.jetbrains.jewel.ui.component.Text

@Composable
fun projectOverview(currentDetail: MutableState<ListItem>) {

    val project = ProjectDao.getInstance().project(currentDetail.value.id)

    val editing: MutableState<Boolean> = remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding()) {

        if (editing.value) {

        } else {

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
    }
}