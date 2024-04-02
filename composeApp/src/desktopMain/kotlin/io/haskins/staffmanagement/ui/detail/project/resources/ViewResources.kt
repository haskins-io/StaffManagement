package io.haskins.staffmanagement.ui.detail.project.resources

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import io.github.windedge.table.DataTable
import io.haskins.staffmanagement.models.dao.ProjectResource
import io.haskins.staffmanagement.ui.components.TableHeader
import io.haskins.staffmanagement.ui.components.buttonrows.AddButtonRow
import io.haskins.staffmanagement.utils.FormatUtils
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
                column { TableHeader("Name") }
                column { TableHeader("Allocation") }
                column { TableHeader("Rate") }
                column { TableHeader("Cost") }
                column { TableHeader("") }
            }
        ) {

            resources.forEach { resource ->

                row(modifier = Modifier) {

                    cell { Text(text = resource.name) }

                    cell {
                        AllocationView(
                            allocation,
                            isEditing,
                            resource,
                            selectedResource
                        )
                    }

                    cell { Text(text = resource.rateName) }

                    cell { Text(text = FormatUtils.formatCurrency(resource.cost)) }

                    cell {
                        if (isEditing.value && resource.id == selectedResource.value) {
                            EditResourceButtons(
                                isEditing,
                                resource,
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

@Composable
private fun AllocationView(
    allocation: MutableState<String>,
    isEditing: MutableState<Boolean>,
    resource: ProjectResource,
    selectedResource: MutableState<Int>
) {

    val localState: MutableState<String> = remember { mutableStateOf(resource.allocation.toString()) }

    if (isEditing.value && resource.id == selectedResource.value) {
       TextField(localState.value, {
           localState.value = it
           allocation.value = it
       })
    } else {
        Text(text = localState.value)
    }
}