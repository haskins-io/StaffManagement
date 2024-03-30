package io.haskins.staffmanagement.ui.detail.project.resources

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import io.github.windedge.table.DataTable
import io.haskins.staffmanagement.models.ProjectResource
import io.haskins.staffmanagement.ui.components.AddButtonRow
import org.jetbrains.jewel.ui.component.*

@Composable
fun ColumnScope.ViewResources(
    addingNew: MutableState<Boolean>,
    deleteDialog: MutableState<Boolean>,
    selectedResource: MutableState<Int>,
    resources: List<ProjectResource>
) {

    val allocation: MutableState<String> = remember { mutableStateOf("0") }

    val isEditing: MutableState<Boolean> = remember { mutableStateOf(false) }

    AddButtonRow(
        "Allocation Resource",
        addingNew
    )

    Row {
        DataTable(
            columns = {
                column { Text("Name") }
                column { Text("Allocation") }
                column { Text("Cost") }
                column { Text("") }
            }
        ) {

            resources.forEach { resource ->

                row(modifier = Modifier) {
                    cell { Text(text = resource.name) }

                    if (isEditing.value && resource.id == selectedResource.value) {
                        cell { TextField(allocation.value, { allocation.value = it }) }
                    } else {
                        cell { Text(text = resource.allocation.toString()) }
                    }

                    cell { Text(text = resource.cost.toString()) }
                    cell {
                        if (isEditing.value && resource.id == selectedResource.value) {
                            EditResourceButtons(
                                isEditing,
                                selectedResource,
                                allocation
                            )
                        } else {
                            ViewResourceButtons(
                                resource,
                                deleteDialog,
                                isEditing,
                                selectedResource
                            )
                        }
                    }
                }
            }
        }
    }
}