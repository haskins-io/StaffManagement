package io.haskins.staffmanagement.ui.detail.project

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.windedge.table.DataTable
import io.haskins.staffmanagement.dao.ProjectDao
import io.haskins.staffmanagement.enums.FilterType
import io.haskins.staffmanagement.models.ListItem
import io.haskins.staffmanagement.ui.components.AddButtonRow
import io.haskins.staffmanagement.ui.components.project.ProjectPriorityView
import io.haskins.staffmanagement.ui.components.project.ProjectStatusView
import org.jetbrains.jewel.ui.component.Text

@Composable
fun ProjectsOverview(currentDetail: MutableState<ListItem>) {

    val projects = ProjectDao.getInstance().projects()

    val addingNew: MutableState<Boolean> = remember { mutableStateOf(false) }

    val vScrollState = rememberScrollState()

    Column(modifier = Modifier.padding(10.dp)) {

        if (addingNew.value) {

        } else {

            AddButtonRow(
                "Add Note",
                addingNew
            )

            Row {

                Box(modifier = Modifier.verticalScroll(vScrollState)) {

                    DataTable(
                        columns = {
                            column { Text("Name") }
                            column { Text("Budget") }
                            column { Text("Cost") }
                            column { Text("Status") }
                            column { Text("Priority") }
                            column { Text("Progress") }
                            column { Text("Due Date") }
                        }
                    ) {

                        projects.forEach { project ->

                            row(
                                modifier = Modifier.clickable {
                                    currentDetail.value = ListItem(project.id, "", FilterType.Projects.id)
                                }
                            ) {
                                cell { Text(text = project.name) }
                                cell { Text(text = project.budget.toString()) }
                                cell { Text(text = project.cost.toString()) }
                                cell { ProjectStatusView(project.status) }
                                cell { ProjectPriorityView(project.priority) }
                                cell { Text(text = project.progress.toString()) }
                                cell { Text(text = project.due.toString()) }
                            }
                        }
                    }
                }
            }
        }
    }
}