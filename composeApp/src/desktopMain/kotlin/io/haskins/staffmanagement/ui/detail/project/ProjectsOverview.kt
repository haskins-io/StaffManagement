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
import io.haskins.staffmanagement.ui.components.TableHeader
import io.haskins.staffmanagement.ui.components.buttonrows.AddButtonRow
import io.haskins.staffmanagement.ui.components.project.ProjectPriorityView
import io.haskins.staffmanagement.ui.components.project.ProjectStatusView
import io.haskins.staffmanagement.utils.FormatUtils
import org.jetbrains.jewel.ui.component.Text

@Composable
fun ProjectsOverview(currentDetail: MutableState<ListItem>) {

    val projects = ProjectDao.getInstance().projects()

    val addingNew: MutableState<Boolean> = remember { mutableStateOf(false) }

    val vScrollState = rememberScrollState()

    Column(modifier = Modifier.padding(10.dp)) {

        if (addingNew.value) {
            AddProject(addingNew)
        } else {

            AddButtonRow(
                "Add Project",
                addingNew
            )

            Row {

                Box(modifier = Modifier.verticalScroll(vScrollState)) {

                    DataTable(
                        columns = {
                            column { TableHeader("Name") }
                            column { TableHeader("Budget") }
                            column { TableHeader("Cost") }
                            column { TableHeader("Status") }
                            column { TableHeader("Priority") }
                            column { TableHeader("Progress") }
                            column { TableHeader("Due Date") }
                        }
                    ) {

                        projects.forEach { project ->

                            row(
                                modifier = Modifier.clickable {
                                    currentDetail.value = ListItem(project.id, "", FilterType.Projects.id)
                                }
                            ) {
                                cell { Text(text = project.name) }
                                cell { Text(text = FormatUtils.formatCurrency(project.budget)) }
                                cell { Text(text = FormatUtils.formatCurrency(project.cost)) }
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